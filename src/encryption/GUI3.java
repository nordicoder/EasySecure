package encryption;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GUI3 extends JDialog{
static ImageIcon ic,ic2;
static JLabel l1,l3;
static JButton b1,b2,b3;
static JTextField tf1,tf2;
static JFileChooser ch1,ch2;
static File file,folder;
static JLabel background;

static Container cp;
static ArrayList<File> filess=new ArrayList<File>();
StringBuilder stringBuilder;
static String ori,ext=null;
GUI3(JFrame fr )
{
	super(fr,"Select File and Folder",true);
	cp=getContentPane(); 
	
//setSize (370, 300) ;
	setBounds(500, 250, 390, 300);
cp.setLayout(null);

 background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
background.setBounds(00, 00, 370, 300);

cp.add(background);

b1=new JButton("Browse File");
b1.setBounds(270, 125, 100, 20);

b2=new JButton("Browse Folder");
b2.setBounds(270, 165, 100, 20);

b3=new JButton("Shred!");
b3.setBounds(10, 215, 100, 20);

ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/ajax-loader.gif")));
ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/shredded.jpg")));

l1=new JLabel(ic);
l1.setBounds(140,20,80,80);
l1.setVisible(true);

l3=new JLabel("<html> <font color='blue'>Choose the File  for  Shredding</font></html>");
l3.setVisible(true);
l3.setBounds(10, 10, 480, 20);

tf1=new JTextField(10);
tf1.setVisible(true);

tf2=new JTextField(10);
tf2.setVisible(true);

tf1.setBounds(10, 125, 250, 20);
tf2.setBounds(10, 165, 250, 20);

background.add(b1);
background.add(b2);
background.add(b3);

background.add(l1);
background.add(l3);
background.add(tf1);
background.add(tf2);

ch1=new JFileChooser();
ch1.setFileSelectionMode(JFileChooser.FILES_ONLY);

b1.addActionListener(new ActionListener() {
		 
    public void actionPerformed(ActionEvent e)
    {
   	 int returnVal = ch1.showOpenDialog(GUI3.this);
   	 if (returnVal == JFileChooser.APPROVE_OPTION) {
   		 
   		 file = ch1.getSelectedFile();
   		 tf1.setText(file.getAbsolutePath());	 
   		 
   	 }
   	 
    	
    }});


ch2=new JFileChooser();

ch2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
b2.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e)
    {
	 int returnVal1 = ch2.showOpenDialog(GUI3.this);
	 if (returnVal1 == JFileChooser.APPROVE_OPTION) {
		 folder=ch2.getSelectedFile();
   		 tf2.setText(folder.getAbsolutePath());

		 
	 }
    }
});




	

	b3.addActionListener(new ActionListener() {
		 
	    public void actionPerformed(ActionEvent e)
	    {
	    	
	    	if(!(tf1.getText().equals("") || tf2.getText().equals("")))
	    	{
	    	l1.repaint();
	    	l1.revalidate();
	    	background.repaint();
	    	background.revalidate();
	    	cp.revalidate();
	    	
	    	cp.repaint();

	    	System.out.println("yo  "+file.getAbsolutePath());
	    	System.out.println("yo  "+folder.getAbsolutePath());
	    	System.out.println("yo   "+folder.isDirectory());
	    
	    	
	    	try {
	    		ori = readFile(file.getAbsolutePath());
	    	 ext = getFileExtension(file);
	    	} catch (IOException e1) {
	    		// TODO Auto-generated catch block
	    		e1.printStackTrace();
	    	}
	    	listf(folder.getAbsolutePath(),filess);
	    	System.out.println("yo   "+ori);

	    	System.out.println("yo   "+ext);
	    	
	    	try {
	    		for(int i=0;i<filess.size();i++)
	    		{
	    		
	    	if(ext.equals(getFileExtension(filess.get(i)))&& file.getName().equals(filess.get(i).getName()))
	    			{		if (file.getCanonicalPath().equals(filess.get(i).getCanonicalPath())||ori.equals(readFile(filess.get(i).getAbsolutePath())))
	    				{
	    					wipe(filess.get(i));
	    					filess.get(i).delete();
	    					System.out.print("yo  shreded"+filess.get(i).getAbsolutePath());
	    				}
	    			
	    			}	
	    		}
	    		
	    		System.out.println("yo  over");
		    	//l1.setVisible(false);
	    		l1.setIcon(ic2);
	    		l1.setVisible(true);
	    		l1.repaint();
	    		


	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}
	    	
	    }
	    }
	}
	);
	
	
	
	
	
	
	
	
	
}



private void wipe(File file) {     
    try {     
        RandomAccessFile rwFile = new RandomAccessFile(file, "rw");  
        try {  
            FileChannel rwChannel = rwFile.getChannel();     
            int numBytes = (int)rwChannel.size();     
            MappedByteBuffer buffer = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, numBytes);     
            buffer.clear();     
            byte[] randomBytes = new byte[numBytes];     
            new Random().nextBytes(randomBytes);     
            buffer.put(randomBytes);     
            buffer.force();  
            // will already write to the disk  
        } finally {  
            rwFile.close();  
        }    
    } catch(Exception e) {     
         e.printStackTrace();
    }     
}


public void listf(String directoryName, ArrayList<File> files) {
    File directory = new File(directoryName);

    // get all the files from a directory
    File[] fList = directory.listFiles();
    if(fList!=null)
    {
    for (File file : fList) 
    {
    	if(files!=null && file!=null)
    	{
    		
    	
    		
        if (file.isFile()) {
            files.add(file);
        } else if (file.isDirectory()) {
            listf(file.getAbsolutePath(), files);
        }
    	}
    }
    }
}

private String readFile( String file ) throws IOException {
	try
	{
		RandomAccessFile f=new RandomAccessFile(file,"rw");
		FileChannel fch=f.getChannel();
    BufferedReader reader = new BufferedReader( new FileReader (file));
    String         line = null;
     stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");
    FileLock lock = fch.tryLock();  
    if (lock != null) {  
        try {  
        	  while( ( line = reader.readLine() ) != null ) {
        	        stringBuilder.append( line );
        	        stringBuilder.append( ls );
        	    }
            // read the file  
        } finally {  
            lock.release();  
        }  
    } else {  
        // some other process has locked the file for some reason  
    }  

  
	}
	catch(Exception e)
	{e.printStackTrace();}
    return stringBuilder.toString();

}
private static String getFileExtension(File file) {
    String fileName = file.getName();
    if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
    return fileName.substring(fileName.lastIndexOf(".")+1);
    else return "";
}

public void logged(String s)
{
	Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;  

    try {  

        // This block configure the logger with handler and formatter  
        fh = new FileHandler("C:/temp/test/MyLogFile.log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

        // the following statement is used to log any messages  
        logger.info(s);  

    } catch (SecurityException e) {  
        e.printStackTrace();  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  

     
	
	
	
}
}
