package encryption;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GUI4 extends JDialog{
	
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	

	byte[] bytes;
	byte[] bytes1;
	URL serverUrl;
	String username,result,response;
	String pathToOurFile=null ;
	String urlServer = "http://omkya.besaba.com/gcm2.php";
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";
	HttpURLConnection httpCon;
	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1*2048*1024;

	
	static ImageIcon ic,ic2;
	static JLabel l1,background;
	static JButton b1;
	static JTextField tf1;
	static JFileChooser ch1;
	static File file;
	static Container cp;
	
	
	GUI4(JFrame fr,String url)
	{
		super(fr,"Select File or Folder to be Uploaded",true);
		cp=getContentPane();
		//setSize (370, 300) ;
		setBounds(500, 250, 390, 300);
		cp.setLayout(null);
		username=url;
		 background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
		background.setBounds(00, 00, 370, 300);

		cp.add(background);

		b1=new JButton("Browse File");
		b1.setBounds(270, 125, 100, 20);

		ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/ajax-loader.gif")));
		ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/shredded.jpg")));

		l1=new JLabel(ic);
		l1.setBounds(140,20,80,80);
		l1.setVisible(true);

		tf1=new JTextField(10);
		tf1.setVisible(true);
		tf1.setBounds(10, 125, 250, 20);
		
		background.add(b1);
		background.add(l1);
		background.add(tf1);
		
		ch1=new JFileChooser();
		ch1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		b1.addActionListener(new ActionListener() {
			 
		    public void actionPerformed(ActionEvent e)
		    {
		    	int returnVal = ch1.showOpenDialog(GUI4.this);
			   	 if (returnVal == JFileChooser.APPROVE_OPTION) {
			   		 
			   		 file = ch1.getSelectedFile();
			   		 System.out.println("dfdf   "+username);
			   		// file.renameTo(new File(file.getName()+"+++"+username));
			   		 tf1.setText(file.getAbsolutePath());	
			   		 
			   		 pathToOurFile=file.getAbsolutePath();
	            	 byte[] buf = new byte[4096];
	 		        ByteArrayOutputStream os = new ByteArrayOutputStream();
 		      
 		                       
 		                    	
 		                    	
	 		       try
    		        {
    		        	InputStream is;
    		        	System.out.println("user : "+username);
    		        	Map<String, String> paramsMap = new HashMap<String, String>();
    		   		paramsMap.put("username", username);
    		   	StringBuilder postBody = new StringBuilder();
   			Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
   					.iterator();

   			while (iterator.hasNext()) {
   				Entry<String, String> param = iterator.next();
   				postBody.append(param.getKey()).append('=')
   						.append(param.getValue());
   				System.out.println(param.getValue());
   				if (iterator.hasNext()) {
   					postBody.append('&');
   				}
   			}
    		        	//System.setProperty("http.keepAlive", "false");
    		            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );
    		         
    		            URL url = new URL(urlServer+"?username="+username);
    		            connection = (HttpURLConnection) url.openConnection();
    		            System.out.println("ff"+"http url fine");
    		         
    		            // Allow Inputs &amp; Outputs.
    		            connection.setDoInput(true);
    		            connection.setDoOutput(true);
    		            connection.setUseCaches(false);
    		            
    		            //connection.setConnectTimeout (30000) ; 
    		         
    		            // Set HTTP method to POST.
    		            connection.setRequestMethod("POST");
    		         
    		           connection.setRequestProperty("Connection", "Keep-Alive");
   		            
   		            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
    		            
    		            //is=connection.getInputStream();
    		            int ret = 0;
    		           // Log.d("ff","connections fine");

    		            /*if(is.read(buf)==0)
    		            {
    		            	flag=1;
    		            }*/
    		            
    		          /*  while ((ret = is.read(buf)) > 0) 
    		            {
    		            	os.write(buf, 0, ret);  
    		            	Log.d("as",buf.toString());
    		            }*/
    		            //is.close();
 		        		            
 		        		         

     		            OutputStream of= connection.getOutputStream() ;
     		            
     		           
     		           
    		            outputStream = new DataOutputStream(of);
     		            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
     		            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
     		            outputStream.writeBytes(lineEnd);
     		            
     		           System.out.println("ff"+"outputstream fine");
     		         
     		            bytesAvailable = fileInputStream.available();
     		            bufferSize = Math.min(bytesAvailable, maxBufferSize);
     		            buffer = new byte[bufferSize];
     		         
     		            // Read file
     		            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
     		           System.out.println("ff"+"bytes read fine");
     		         
     		            while (bytesRead > 0)
     		            {
     		                outputStream.write(buffer, 0, bufferSize);
     		                bytesAvailable = fileInputStream.available();
     		                bufferSize = Math.min(bytesAvailable, maxBufferSize);
     		                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
     		            }
     		         
     		            outputStream.writeBytes(lineEnd);
     		            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
     		            
     		         
     		            // Responses from the server (code and message)
     		           System.out.println("asd"+"uhoh");
     		            int serverResponseCode = connection.getResponseCode();
     		            String serverResponseMessage = connection.getResponseMessage();
     		           System.out.println("asd"+"hushsh");
     		            //Toast.makeText(getApplicationContext(), "hushhh", Toast.LENGTH_LONG).show();
     		            
     		          System.out.println("dd"+/*serverResponseCode*/"--"+serverResponseMessage);
     		         
     		            fileInputStream.close();
     		            outputStream.flush();
     		            outputStream.close();
     		            connection.disconnect();
     		            
     		            //flag=0;
     		           
     		           System.out.println("ff"+"closed and done");
     		          l1.setIcon(ic2);
     		    		l1.setVisible(true);
     		    		l1.repaint();
     		    		
     		    			MyLogger.logged("Uploaded "+file.getAbsolutePath());
     		    		
     		            //Toast.makeText(getApplicationContext(), "Upload done", Toast.LENGTH_LONG).show();
     		        }
     		        catch (Exception ex)
     		        {
     		            //Exception handling
     		        	ex.printStackTrace();
     		       
     		        }
     		        
                 	
                 	
                 	
                 	
                 	
                 
                 }
		 
	 }});
}
	public String commun(String t)
    {
 
    {		Map<String, String> paramsMap = new HashMap<String, String>();
	paramsMap.put("username", t);

	
	
	try {
		serverUrl = null;
		try {
			serverUrl = new URL("http://omkya.besaba.com/ok.php?");
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
            /**/httpCon.setConnectTimeout (30000) ; 
            
			
            System.out.println("ddd"+"OutputStream Before");

			OutputStream out = httpCon.getOutputStream();
			
			bytes1=t.getBytes();
			String gg=new String(bytes1);
			   System.out.println("asd  "+gg);
			out.write(bytes);
			out.close();
			
			InputStream in=httpCon.getInputStream();
            in.read(bytes1);
             response =new String(bytes1);
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
	}



		
		
		
	

