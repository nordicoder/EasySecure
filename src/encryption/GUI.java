package encryption;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;




public class GUI extends JFrame
{

	 
	 private static final int BUFFER_SIZE = 4096;
	
	static GUI1 g=null;
	static GUI2 g2=null;
	static GUI3 g3=null;
	static GUI4 g4=null;
	static GUI5 g5=null;
	static GUI6 g6=null;

	static GUIinter gi=null;

	static JLabel l1,l2,l3,l4;
	static JMenuBar menuBar;
	static Image img;

	static JMenu menu,menu2,menu3;
	static JMenuItem mi1,mi2,m3;
	static JMenuItem mm;
	static JButton b1,b2,b3,b4,b5,b6,button_help;
	static ImageIcon ic,ic2,ic3,ic4,ic5,ic6,ic7,ic8,icon_help;
	static JFileChooser ch1,ch2;
	static JPasswordField ps;
	static JTextField tf1;
    static JFrame fr ; 
	static Container cp;
	static File file,key,file2;
	static FileNameExtensionFilter filter;
	GUI()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new FlowLayout());
	
		setBounds(350, 250, 740, 330);
		setResizable(false);
		setTitle("EasySecure");

		//add(background);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		cp=getContentPane(); 
		cp.setLayout(null);

		
		JLabel background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
		background.setBounds(00, 00, 740, 330);
		
		cp.add(background);
		
		
		 fr = new JFrame(); 
		
		ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/encrypt1.png")));
		ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/decrypt1.png")));
		ic3=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/file1.jpg")));
		ic4=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/md5.jpg")));
		ic5=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/shred.jpg")));
		ic6=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/folder1.jpg")));
		ic7=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/upload.jpg")));
	
		ic8=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/down.jpeg")));
		icon_help=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/help.jpg")));



		
		/*l1=new JLabel("ENTER PASSOWRD");
		l1.setVisible(true);
		l1.setBounds(10, 120,100,90);
		
		l2=new JLabel("PATH");
		l2.setVisible(true);
		l2.setBounds(10, 90, 40, 90);
		*/
		
		
		/*tf1=new JTextField(10);
		tf1.setVisible(true);
		tf1.setBounds(60,125,190,20);*/
		
		/*ps=new JPasswordField(10);
		ps.setVisible(false);
		ps.setBounds(120, 150,100,20);
		
		l1.setVisible(true);
    	ps.setVisible(true);
		*/
		
    	
		b1=new JButton();
		b1.setIcon(ic);
        b1.setVisible(true);
		b1.setBounds(10, 40, ic.getIconWidth(), ic.getIconHeight());

     
      
        
        b2=new JButton();
		b2.setIcon(ic2);
        b2.setVisible(true);
		b2.setBounds(120, 40, ic2.getIconWidth(), ic2.getIconHeight());

		 b3=new JButton();
			b3.setIcon(ic5);
	        b3.setVisible(true);
			b3.setBounds(250, 50, ic5.getIconWidth(), ic5.getIconHeight());
			

	        b4=new JButton();
			b4.setIcon(ic4);
	        b4.setVisible(true);
			b4.setBounds(370, 44, ic4.getIconWidth(), ic4.getIconHeight());
			
			b5=new JButton();
			b5.setIcon(ic7);
	        b5.setVisible(true);
			b5.setBounds(485, 40, ic7.getIconWidth(), ic7.getIconHeight());
			
			b6=new JButton();
			b6.setIcon(ic8);
	        b6.setVisible(true);
			b6.setBounds(620, 50, ic8.getIconWidth(), ic8.getIconHeight());
			
			button_help=new JButton();
			button_help.setIcon(icon_help);
	        button_help.setVisible(true);
			button_help.setBounds(700, 00, icon_help.getIconWidth(), icon_help.getIconHeight());

				l4=new JLabel();
				l4.setBounds(500,265,260,40);
				l4.setText("<html> <font color='blue'>Developed By Ghanshyam Lele and Omkar Patil</font></html>");


        
        menuBar=new JMenuBar();
        
       
        
        menu=new JMenu("Encrypt");
        menu2=new JMenu("Decrypt");
        menu3=new JMenu("Help");
       // menu3.setIcon(ic8);
        menu3.setBounds(500, 05, 10, 10);
       
        
      mi1=new JMenuItem("Add file");
      mi1.setIcon(ic3);



      mi2=new JMenuItem("Add folder");
      mi2.setIcon(ic6);

      
      menu.add(mi1);
      menu.add(mi2);

        
       
       /* menuBar.add(menu);
        menuBar.add(menu2);
        menuBar.add(menu3);*/


       

		
		background.add(b1);
		background.add(b2);
		background.add(b3);
		background.add(b4);
		background.add(b5);
		background.add(b6);
		background.add(button_help);

		background.add(l4);



		//cp.add(l1);
		//cp.add(ps);
		//cp.add(l2);
		//cp.add(tf1);
		
		setJMenuBar(menuBar);
		ch1=new JFileChooser();
		
		b1.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	//int returnVal = ch1.showOpenDialog(GUI.this);
                //Execute when button is pressed
            	// parent, isModal
            	
             	g=new GUI1(fr);
             	g.setVisible(true);
             	
            	/* if (returnVal == JFileChooser.APPROVE_OPTION) {
                     File file = ch1.getSelectedFile();
                  	tf1.setText(file.getAbsolutePath());
                 

                 

                   
            	 }*/
            }
		
			
			
			
		});
		
		/*String[]t={"enc"};
		ch2=new JFileChooser();
		 filter = new FileNameExtensionFilter("ENC",t);*/
		b2.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {//ch2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            //ch2.setFileFilter(filter);
            	//int returnVal = ch2.showOpenDialog(GUI.this);
                //Execute when button is pressed
            	// parent, isModal
            
            	g2=new GUI2(fr);
             	g2.setVisible(true);
            	
            	


            	
            	
            	/* if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = ch2.getSelectedFile();
                 	tf1.setText(file.getAbsolutePath());

            	 }*/
            }
            
           
			
			
			
		});
		
		
		 b3.addActionListener(new ActionListener() {
   			 
             public void actionPerformed(ActionEvent e)
             {
            	 
         		ch1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                		 g3=new GUI3(fr);
                      	g3.setVisible(true);

            	 }
            	 
             	
             });
		 
			
		 b4.addActionListener(new ActionListener() {
   			 
             public void actionPerformed(ActionEvent e)
             {
        		 
                	 
            	 g5=new GUI5(fr);
               	g5.setVisible(true);

            		
            		 
            		 
            	 }
            	 
             	
             });
		 
 b6.addActionListener(new ActionListener() {
   			 
             public void actionPerformed(ActionEvent e)
             {
        		 
            	 g6=new GUI6(fr);
             	g6.setVisible(true);
          	 

            		
            		 
            		 
            	 }
            	 
             	
             });

		 
		 
		 
		 ch1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			
		 b5.addActionListener(new ActionListener() {
   			 
             public void actionPerformed(ActionEvent e)
             {
            	// g4=new GUI4(fr,);
               	//g4.setVisible(true);
            	 gi=new GUIinter(fr);
                 gi.setVisible(true);
            	
             }
             });
		 
 		            
button_help.addActionListener(new ActionListener() {
   			 
             public void actionPerformed(ActionEvent e)
             {
        		 
            	// String htmlFilePath = ; 
            		Path currentpath = Paths.get("");
            	 File htmlFile = new File(currentpath.toAbsolutePath().toString()+"/help.html");

            	
            	 try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


            		
            		 
            		 
            	 }
            	 
             	
             });
		
		
		
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

	    logger.info("Hi How r u?");  
		
		
		
	}
	
	public static void main(String args[])
	{
		
		new GUI().setVisible(true);
		
		
	}
    public static void downloadFile(String fileURL, String saveDir)
            throws IOException {
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
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
	 
	
}
