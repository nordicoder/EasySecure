package encryption;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class GUI7 extends JDialog{

	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	 static String rett="";
	String result,response;
	HttpURLConnection httpCon;
	byte[] bytes;
	byte[] bytes1;
	URL serverUrl;

	static int flag=0;
	static String user;
	
	static ImageIcon ic,ic2;
	static JLabel l1;
	static JButton b1,b2,b3;
	static JTextField tf1,tf2;
	static JFileChooser ch1,ch2;
	static File file,folder;
	static JLabel background;
	
	static JComboBox combo_down; 
   


	static Container cp;
	static ArrayList<File> filess=new ArrayList<File>();
	StringBuilder stringBuilder;
	static String ori,ext=null;
	GUI7(JFrame fr,final String username)
	{
		super(fr,"Select File to download",true);
		cp=getContentPane(); 
		user=username;
	//setSize (370, 300) ;
		setBounds(500, 250, 370, 300);
	cp.setLayout(null);

	 background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
	background.setBounds(00, 00, 370, 300);

	cp.add(background);

	b1=new JButton("Get File");
	b1.setBounds(270, 125, 100, 20);
	b1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String finalfile="";
			finalfile= "/"+combo_down.getItemAt
                    (combo_down.getSelectedIndex()); 
			if(finalfile.contains("/.."))
			{
				 JOptionPane.showMessageDialog(null, "Please Choose a Valid File");

				
			}
			else
			{
			String t="http://omkya.besaba.com/"+username+finalfile;
			Path currentpath = Paths.get("");
			String dir = currentpath.toAbsolutePath().toString();
			//String dir="C:\\Users\\GHANSHYAM\\Downloads";
			try {
				downloadFile(t,dir,2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		}
		}
	);




	ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/ajax-loader.gif")));
	ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/shredded.jpg")));

	l1=new JLabel();
	l1.setBounds(140,140,ic2.getIconWidth(),ic2.getIconHeight());
	l1.setVisible(true);
	l1.setIcon(ic);

	tf1=new JTextField(10);
	tf1.setVisible(true);



	tf1.setBounds(10, 125, 250, 20);
	//tf2.setBounds(10, 165, 250, 20);

	background.add(b1);
	
	//background.add(b2);
	//background.add(b3);

	background.add(l1);
	//background.add(tf1);
	//background.add(tf2);
	
	String t=commun(username);
	String reess="No Files";
	String tttt="http://omkya.besaba.com/GCMRegIdTE2.txt";
	Path currentpath = Paths.get("");
	String dir = currentpath.toAbsolutePath().toString();
	//String dir="C:\\Users\\GHANSHYAM\\Downloads";
	try {
		reess=downloadFile(tttt,dir,1);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	final DefaultComboBoxModel downlist = new DefaultComboBoxModel();
	
	System.out.println("yo "+reess);

	//l1.setText(reess);
String []down_list=reess.split("ggg");
for(int i=0;i<down_list.length;i++)
{
	
	if(i>1)
	{
		
		downlist.addElement(down_list[i]);
		System.out.println("asd "+down_list[i]);
		
	}

}
combo_down=new JComboBox(downlist);
combo_down.setBounds(30, 20, 300, 30);
background.add(combo_down);
//combo_down.setSelectedIndex(0);



}
	
	public String commun(String username)
    {
 
    {		Map<String, String> paramsMap = new HashMap<String, String>();
	paramsMap.put("username", username);

	
	
	try {
		serverUrl = null;
		try {
			serverUrl = new URL("http://omkya.besaba.com/list.php?username="+username);
		} catch (MalformedURLException e) {
		
			
		}

		StringBuilder postBody = new StringBuilder();
		Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
				.iterator();

		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			postBody.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				postBody.append('&');
			}
		}
		System.out.println("ddd"+"http before");

		String body = postBody.toString();
		 bytes = body.getBytes();
		InputStream is;
        byte[] buf = new byte[4096];
        int flag=0;
        byte[] buff1 = new byte[16384];
        
        
	httpCon = null;
		
		 
		
		try {
			httpCon = (HttpURLConnection) serverUrl.openConnection();
            httpCon.setDoInput(true);

			httpCon.setDoOutput(true);
			httpCon.setUseCaches(false);
			httpCon.setFixedLengthStreamingMode(bytes.length);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
            /**/httpCon.setRequestProperty("Connection", "Keep-Alive");
            //httpCon.setConnectTimeout (30000) ; 
            
			
            System.out.println("ddd"+"OutputStream Before");

			OutputStream out = httpCon.getOutputStream();
			
			bytes1=username.getBytes();
			String gg=new String(bytes1);
			   System.out.println("asd  "+gg);
			out.write(bytes);
			out.close();
			
			InputStream in=httpCon.getInputStream();
            int ttt=in.read(buff1);
             response =new String(buff1);
             System.out.println(response+"    "+ttt);
            in.close();
			
			System.out.println("ddd"+"Getting response uhoh   "+response);
			
			System.out.println("ddd"+"phew");
			

			int status = httpCon.getResponseCode();
			if (status == 200) {
				result = "RegId shared with Application Server. RegId: ";
					
				System.out.println("dd"+"thers hope:)");
			} else {
				result = "Post Failure." + " Status: " + status;
				System.out.println("dd"+"sorry not shared");
				httpCon.disconnect();
			}
		} 
		finally {
			if (httpCon != null) {
				System.out.println("dd"+"http con is not null:)");

				httpCon.disconnect();
			}
		}
        

        
	
	
	
	

	                }
	            catch (Exception e) {
	    			result = "Post Failure. Error in sharing with App Server.";
	    			//Log.d("dd","sorry not shared again");
	    			//Log.e("AppUtil", "Error in sharing with App Server: " + e);
	    			
	            }
	            
	
	
}

return response;

}


	 public static String downloadFile(String fileURL, String saveDir,int flag)
	            throws IOException {
		 try
		 {
	        URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	                System.out.println(fileName);
	            }
	 
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	            System.out.println(saveDir);
	            String saveFilePath="";
	            saveFilePath = saveDir + "\\" + fileName;
	            if(flag==2)
	            {
	             saveFilePath = saveDir + "\\" + fileName;
	            }
	            // opens an output stream to save into file
	            File dowloadedfile=new File(saveFilePath);
	            System.out.println(saveFilePath);

	            FileOutputStream outputStream = new FileOutputStream(dowloadedfile);
	 
	            int bytesRead = -1;
	            byte[] buffer = new byte[4096];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	                
	            }
	 
	            outputStream.close();
	            inputStream.close();
	 
	            System.out.println("File downloaded");
	            if(flag==2)
	            {
	            l1.setIcon(ic2);
	            JOptionPane.showMessageDialog(null, "Saved To :- "+saveDir);
	            }
	    			MyLogger.logged("Downloaded  "+fileURL);
	    			try(BufferedReader br = new BufferedReader(new FileReader(saveFilePath))) {
	    		        StringBuilder sb = new StringBuilder();
	    		        String line = br.readLine();

	    		        while (line != null) {
	    		            sb.append(line);
	    		            sb.append(System.lineSeparator());
	    		            line = br.readLine();
	    		        }
	    		        String everything = sb.toString();
	    		       System.out.println(everything);
	    		        rett=everything;
	    		    }
	            
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
		 }catch(Exception e)
		 {
			 e.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Error, Choose appropriate file and refresh");
		 }
		 return rett;
	    }
		 
	    
	   



	
	
}
