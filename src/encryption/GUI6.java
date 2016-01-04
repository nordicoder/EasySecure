package encryption;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class GUI6 extends JDialog{
	static ImageIcon ic,ic2,ic3;
	static JLabel l1,l2,l3,l4;
	static JButton b1,b2,b3;
	static JTextField tf1;
	static JPasswordField tf2;
	static JFileChooser ch1,ch2;
	static File file,folder;
	static JLabel background;
	static GUI4 g4=null;
	static GUI7 g7=null;

	static JFrame frr;
	
	String result,response;
	HttpURLConnection httpCon;
	byte[] bytes;
	byte[] bytes1;
	URL serverUrl;
	
	int flag=1;

	static Container cp;
	static ArrayList<File> filess=new ArrayList<File>();
	StringBuilder stringBuilder;
	static String ori,ext=null;
	GUI6(JFrame fr )
	{
	
		super(fr,"Sign Up or Login",true);
		cp=getContentPane(); 
		frr=fr;
	//setSize (370, 300) ;
		setBounds(500, 250, 370, 300);
	cp.setLayout(null);

	 background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
	background.setBounds(00, 00, 370, 300);
	
	ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/sign.jpg")));
	ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/login.jpg")));
	ic3=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/next.jpg")));


	cp.add(background);

	l2=new JLabel("UserID");
	l2.setBounds(270, 125, 100, 20);

	l3=new JLabel("Password");
	l3.setBounds(270, 165, 100, 20);

	b1=new JButton();
	b1.setIcon(ic);
    b1.setVisible(true);
	b1.setBounds(10, 20, ic.getIconWidth(), ic.getIconHeight());

    

 
  
    
    b2=new JButton();
	b2.setIcon(ic2);
    b2.setVisible(true);
	b2.setBounds(170, 20, ic2.getIconWidth(), ic2.getIconHeight());
	
	b3=new JButton();
	b3.setIcon(ic3);
    b3.setVisible(true);
	b3.setBounds(300, 210, ic3.getIconWidth(), ic3.getIconHeight());


	ic=new ImageIcon("sign.jpg");
	ic2=new ImageIcon("login.jpg");

	l1=new JLabel(ic);
	l1.setBounds(140,20,80,80);
	l1.setVisible(true);

	l4=new JLabel();
	l4.setBounds(170, 225, 100, 20);
	l4.setText("<html> <font color='blue'>Login or SignUp</font></html>");

	
	tf1=new JTextField(10);
	tf1.setVisible(true);

	tf2=new JPasswordField(10);
	tf2.setVisible(true);

	tf1.setBounds(10, 125, 250, 20);
	tf2.setBounds(10, 165, 250, 20);

	background.add(b1);
	background.add(b2);
	background.add(b3);

	background.add(l2);
	background.add(l3);
	background.add(l4);

	background.add(tf1);
	background.add(tf2);



	b1.addActionListener(new ActionListener() {
			 
	    public void actionPerformed(ActionEvent e)
	    {
	   	 	flag=2;
		   	 l4.setText("<html> <font color='blue'>Signing In</font></html>");

	    	
	    }});



	b2.addActionListener(new ActionListener() {
		 
	    public void actionPerformed(ActionEvent e)
	    {
		 flag=3;
	    	
	   	 l4.setText("<html> <font color='blue'>Logging In</font></html>");

	    }
	});
	
	b3.addActionListener(new ActionListener() {
		 
	    public void actionPerformed(ActionEvent e)
	    {
	   	 	
	   	 	if(tf1.getText().toString()!=null && tf2.getText().toString()!=null)
	   	 	{
	   	 		if(flag==2)
	   	 		{
	   	 			
	   	 			String t=commun("***"+tf1.getText().toLowerCase()+"???"+tf2.getText().toLowerCase());
	   	 			
	   	 			if(t.contains("new"))
	   	 			{
	   	 			g4=new GUI4(frr,tf1.getText().toLowerCase());
	             	g4.setVisible(true);
	   	 				
	   	 			}
	   	 			
	   	 			
	   	 		}
	   	 		else if(flag==3)
	   	 		{
	   	 			
	   	 		String t=commun(":::"+tf1.getText().toLowerCase()+"???"+tf2.getText().toLowerCase());
	   	 	if(t.contains("::"))
	 			{
	 		
	   	 	 JOptionPane.showMessageDialog(getParent(), "Password or username not correct");
	 			}
	   	 	
	   	 	else if(t.contains("bad"))
	   	 	{
	   	 		
	      	 	 JOptionPane.showMessageDialog(getParent(), "Password or username not correct");
	   	 	}
	   	 	else
	   	 	{
	   	 	g7=new GUI7(frr,tf1.getText().toLowerCase());
         	g7.setVisible(true);
	   	 		
	   	 	}
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
