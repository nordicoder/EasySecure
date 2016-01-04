package encryption;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryption 
{
	static String algo;
	static File file;
	public FileEncryption(File file) {
		
		FileEncryption.file = file;
	}
	public static void setAlgo(String algo) {
		FileEncryption.algo = algo;
	}
	
	public static File createZip(File dir) throws IOException
	{
		File tmpfile=File.createTempFile(dir.getName(), ".zip");
		try {
			BufferedInputStream origin=null;
			FileOutputStream dest=new FileOutputStream(tmpfile);
			ZipOutputStream out=new ZipOutputStream(new BufferedOutputStream(dest));
			byte [] data=new byte [4096];
			String [] files=dir.list();
			for(int i=0;i<files.length;i++)
			{
				System.out.println("adding: "+files[i]);
				File tmp=new File(dir.getAbsolutePath()+"\\"+files[i]);
				if(tmp.isDirectory())
					tmp=createZip(tmp);
				FileInputStream fi=new FileInputStream(tmp);
				origin=new BufferedInputStream(fi,4096);
				ZipEntry entry=new ZipEntry(tmp.getName());
				out.putNextEntry(entry);
				int cnt;
				while((cnt=origin.read(data,0,4096))!=-1)
				{
					out.write(data,0,cnt);
				}
				origin.close();
			}
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
			return tmpfile;
	}
	public static String getAlgoFromFile(File f) throws IOException
	{
		UserDefinedFileAttributeView view=Files.getFileAttributeView(f.toPath(), UserDefinedFileAttributeView.class);
		ByteBuffer b=ByteBuffer.allocate(view.size("algo"));
		view.read("algo", b);
		b.flip();
		return Charset.defaultCharset().decode(b).toString();
	}
	public static boolean checkPassword(String pass) throws IOException, NoSuchAlgorithmException
	{
		UserDefinedFileAttributeView view=Files.getFileAttributeView(file.toPath(),UserDefinedFileAttributeView.class);
		ByteBuffer buff=ByteBuffer.allocate(view.size("zzz"));
		view.read("zzz", buff);
		buff.flip();
		byte [] hash=buff.array();
		MessageDigest m = MessageDigest.getInstance("MD5");
		 m.reset();
		 m.update(pass.getBytes());
		 byte[] digest = m.digest();
		String result="";
		String result2="";
		 for (int i=0; i < hash.length; i++) {
	            result += Integer.toString( ( hash[i] & 0xff ) + 0x100, 16).substring( 1 );
	            result2 += Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	        }
		 
		 System.out.println(result);
		if(result2.equals(result))
			return true;
		return false;
	}
	public static SecretKey generateKey(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		if(pass!=null)
		{
			SecretKeyFactory factory=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(pass.toCharArray(), SecureRandom.getSeed(16), 65536, 128);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), algo);
			return secret;
		}
		KeyGenerator keygen=KeyGenerator.getInstance(algo);
		keygen.init(128);
		return keygen.generateKey();
	}
	public  void encrypt(SecretKey key,String pass,boolean usepass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException
	{
		File encrytedfile=new File("encrypted_"+file.getName().split("\\.")[0]+".enc");
		encrytedfile.setWritable(false,true);
		if(!usepass)
		{
			File keyfile=new File(file.getName().split("\\.")[0]+".key");	
			FileOutputStream fos=new FileOutputStream(keyfile);
			fos.write(key.getEncoded());
			fos.close();
			keyfile.setWritable(false,true);
		}
		
		FileOutputStream fileOutputStream=new FileOutputStream(encrytedfile);
		FileInputStream fis=new FileInputStream(file);
		System.out.println(algo);
		Cipher encCipher=Cipher.getInstance(algo);
		encCipher.init(Cipher.ENCRYPT_MODE,key );
		CipherOutputStream cipherOutputStream=new CipherOutputStream(fileOutputStream, encCipher);
		 byte[] buf = new byte[1024];
		 int read;

		 while((read=fis.read(buf))!=-1) 
		 cipherOutputStream.write(buf,0,read); 
		 //closing streams
		 fis.close();
		 cipherOutputStream.flush();
		 cipherOutputStream.close();
		 fis.close();
		 fileOutputStream.close();
		 MessageDigest m = MessageDigest.getInstance("MD5");
		 m.reset();
		 m.update(pass.getBytes());
		 byte[] digest = m.digest();
		 UserDefinedFileAttributeView view=Files.getFileAttributeView(encrytedfile.toPath(),UserDefinedFileAttributeView.class );
		 view.write("name", Charset.defaultCharset().encode(file.getName()));
		 view.write("zzz", ByteBuffer.wrap(digest));
		 view.write("checksum", ByteBuffer.wrap(generateChecksum(file)));
		 view.write("algo", Charset.defaultCharset().encode(algo));
		 if(usepass)
		 view.write("yyy",ByteBuffer.wrap(key.getEncoded()) );
		 
	}
	public static boolean hasKeyfile(File file) throws IOException
	{
		UserDefinedFileAttributeView view=Files.getFileAttributeView(file.toPath(),UserDefinedFileAttributeView.class);
		List<String> lists=view.list();
		if(lists.contains("yyy"))
			return false;
		return true;
	}
	public void decrypt(File keyfile,String pass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException
	{
		
		FileInputStream fis =new FileInputStream(file);
		UserDefinedFileAttributeView view=Files.getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);
		ByteBuffer buff=ByteBuffer.allocate(view.size("name"));
		view.read("name", buff);
		buff.flip();
		File decryptedfile=new File("decrypted_"+Charset.defaultCharset().decode(buff).toString());
		FileOutputStream fos =new FileOutputStream(decryptedfile);
		byte [] keybyte;
		if(pass==null)
		{
			FileInputStream fis2=new FileInputStream(keyfile);
			 keybyte=new byte[16];
			fis2.read(keybyte);
			fis2.close();
		}
		else
		{
			ByteBuffer keybuff=ByteBuffer.allocate(view.size("yyy"));
			view.read("yyy", keybuff);
			keybuff.flip();
			keybyte=keybuff.array();
		}
		SecretKeySpec key=new SecretKeySpec(keybyte, algo);
		Cipher decrypt = Cipher.getInstance(algo);
		decrypt.init(Cipher.DECRYPT_MODE, key);
		CipherInputStream cin=new CipherInputStream(fis, decrypt);
		ByteBuffer oldhash=ByteBuffer.allocate(view.size("checksum"));
		view.read("checksum", oldhash);
		//fis.read(oldhash);
		byte[] buf = new byte[1024];
		int read=0;

		while((read=cin.read(buf))!=-1) //reading encrypted data
		{
		fos.write(buf,0,read); //writing decrypted data
		}

		//closing streams
		cin.close();
		fos.flush();
		fos.close();
		
		
		
		FileInputStream fis1 = new FileInputStream(decryptedfile);
        MessageDigest md = MessageDigest.getInstance("MD5");
      
        //Using MessageDigest update() method to provide input
        byte [] buff1=new byte[4096];
        DigestInputStream dis=new DigestInputStream(fis1, md);
        int read1;
        while((read1=dis.read(buff1))!=-1)
        	md.update(buff1, 0, read1);
        byte[] hash = md.digest();
        dis.close();
        String result = "";
        String result2="";
        byte [] oldhasharray=oldhash.array();
        
        for (int i=0; i < hash.length; i++) {
            result += Integer.toString( ( hash[i] & 0xff ) + 0x100, 16).substring( 1 );
            result2 += Integer.toString( ( oldhasharray[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        
        System.out.println(result);
        System.out.println(result2);
        if(result.equals(result2))
        	System.out.println("file is untampered");
        else
        	System.out.println("file is tampered");
	}
	
	public static byte[] generateChecksum(File original) throws NoSuchAlgorithmException, IOException
	{
		
		FileInputStream fis = new FileInputStream(original);
        MessageDigest md = MessageDigest.getInstance("MD5");
      
        //Using MessageDigest update() method to provide input
        byte [] buff=new byte[4096];
        DigestInputStream dis=new DigestInputStream(fis, md);
        int read;
        while((read=dis.read(buff))!=-1)
        	md.update(buff, 0, read);
        byte[] hash = md.digest();
        String result="";
        for (int i=0; i < hash.length; i++) {
            result += Integer.toString( ( hash[i] & 0xff ) + 0x100, 16).substring( 1 );
            
        }
       
        System.out.println(result);
        dis.close();
        
        System.out.println(Charset.defaultCharset().decode(ByteBuffer.wrap(hash)));
        return hash;
	}
	
	public static String [] checkSum(File f1,File f2) throws IOException, NoSuchAlgorithmException
	{
		String [] array = new String[2];
		String result="";
		String result2="";
		byte [] oldhash=generateChecksum(f1);
		byte [] newhash=generateChecksum(f2);
        
        for (int i=0; i < oldhash.length; i++) {
            result += Integer.toString( ( oldhash[i] & 0xff ) + 0x100, 16).substring( 1 );
            result2 += Integer.toString( ( newhash[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        array[0]=result;
        array[1]=result2;
        System.out.println(result);
        System.out.println(result2);
        
        if(result.equals(result2))
        	System.out.println("file is untampered");
        else
        	System.out.println("file is tampered");
        return array;
	}
	/*public static void main(String[] args) throws InvalidKeySpecException {
		try {
			Scanner s=new Scanner(System.in);
			System.out.println("enter pass");
			String pass=s.next();
			//FileEncryption fileEncryption=new FileEncryption(createZip(new File("C:\\Users\\student\\Desktop\\tp0")));
	        FileEncryption fileEncryption=new FileEncryption(new File("encrypted_tp07490801656507378547"));
			FileEncryption.setAlgo("AES");
			//fileEncryption.encrypt(generateKey(pass),pass,false);
			if(checkPassword(pass))
			 fileEncryption.decrypt(new File("mykey.key"),null);
			else
				System.out.println("wrong pass");
			s.close();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
