package encryption;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.crypto.NoSuchPaddingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GUI2 extends JDialog{

	static Container cp;
	static JButton b1,b2,b3;
	static JTextField tf1,keyfield;
	static JLabel l1,l2,background,l3,keylabel;
	static ImageIcon ic,ic3;
	static JFileChooser ch1,ch2,ch3;
	static File file,keyfile;
	static JPasswordField ps;
	static JButton okbutt;
	//static JCheckBox cb1;
	static FileNameExtensionFilter filter;
	GUI gg=new GUI();



	
	GUI2(JFrame fr )
	{
		super ( fr, "Select Files or Folder", true ) ; 
		gg.getContentPane().setEnabled(false);
		
	

		cp=getContentPane(); 
		
   // setSize (480, 290) ;
		setBounds(500, 250, 480, 290);
    cp.setLayout(null);
    
	ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/file.jpg")));
	//ic2=new ImageIcon("folder.jpg");
	ic3=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/key.jpg")));
	
	ps=new JPasswordField(8);
	ps.setVisible(true);
	ps.setBounds(60,190,90,20);
	
	background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
	 background.setBounds(00, 00, 470, 290);
	 cp.add(background);

	
	b1=new JButton();
	b1.setIcon(ic);
    b1.setVisible(true);
	b1.setBounds(100, 50, ic.getIconWidth(), ic.getIconHeight());
	
	b3=new JButton();
	b3.setIcon(ic3);
    b3.setVisible(true);
	b3.setBounds(280, 50, ic3.getIconWidth(), ic3.getIconHeight());

	 okbutt=new JButton();
	    okbutt.setText("Done");
	    okbutt.setVisible(true);
	    okbutt.setBounds(200, 220, 60, 25);
	    okbutt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					file=new File(tf1.getText());
					if(!(tf1.getText().equals("") || ps.getText().equals("") || keyfield.equals(""))||(!FileEncryption.hasKeyfile(file)&&keyfield.equals("")))
					{
					
					String pass=null;
					FileEncryption dec=new FileEncryption(file);
					try {
						String algo=FileEncryption.getAlgoFromFile(file);
						System.out.println(algo);
						FileEncryption.setAlgo(algo);
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					try {
						if (FileEncryption.checkPassword(new String(ps.getPassword()))) 
						{
							pass=new String(ps.getPassword());
							keyfile=new File(keyfield.getText());
							if(keyfield.isEnabled())
							 dec.decrypt(keyfile,null);
							else
							{
								dec.decrypt(keyfile, pass);
								MyLogger.logged("Decrypted "+file.getAbsolutePath());
								JOptionPane.showMessageDialog(getParent(), "Decrypted!");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(getParent(), "Password is wrong");
							ps.setText("");
						}
					} catch (NoSuchAlgorithmException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(getParent(), "Decrypted");
					
}
					else
					{
						
						
						JOptionPane.showMessageDialog(getParent(), "Please Enter all the Fields");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
   /* cb1=new JCheckBox();
    cb1.setSelected(false);
    cb1.setBounds(270, 190, 20, 20);*/
 
  
    
    
    
	l1=new JLabel("PATH");
	l1.setVisible(true);
	l1.setBounds(10, 155, 40, 20);
	keylabel=new JLabel("Key");
	keylabel.setVisible(true);
	keylabel.setBounds(200, 190, 40, 20);
	l2=new JLabel("Password");
	l2.setVisible(true);
	l2.setBounds(10, 190, 80, 20);
	
	l3=new JLabel("<html> <font color='blue'>Choose the File  for  Decryption</font></html>");
	l3.setVisible(true);
	l3.setBounds(10, 10, 480, 20);
	
	tf1=new JTextField(190);
	tf1.setVisible(true);
	tf1.setBounds(60, 155, 250, 20);

    keyfield=new JTextField(190);
    keyfield.setVisible(true);
    keyfield.setBounds(225, 190, 230, 20);
    background.add(b1);
    background.add(okbutt);
    //background.add(b2);
    background.add(b3);
   // background.add(cb1);
    background.add(l1);
    background.add(l2);
    background.add(l3);
    background.add(tf1);
    background.add(keylabel);
    background.add(keyfield);
    
   // background.add(cb1);
    background.add(ps);

 


    ch1=new JFileChooser(".");
    ch1.setFileSelectionMode(JFileChooser.FILES_ONLY);
	ch1.setFileFilter(new FileNameExtensionFilter("Encrypted", "enc"));
	b1.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e)
        {
        	int returnVal = ch1.showOpenDialog(GUI2.this);
        	
        	 if (returnVal == JFileChooser.APPROVE_OPTION) {
                 //file = ch1.getSelectedFile();
        		 boolean haskeyfile=true;
				try {
					haskeyfile = FileEncryption.hasKeyfile(ch1.getSelectedFile());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					b3.setEnabled(haskeyfile);
					keyfield.setEnabled(haskeyfile);
              	tf1.setText(ch1.getSelectedFile().getAbsolutePath());
        	 }
        }});
	
	
	
	
		
		ch3=new JFileChooser(".");
	    ch3.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    
         ch3.setFileFilter(new FileNameExtensionFilter("Key", "key"));

		b3.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e)
	        {
	        	int returnVal = ch3.showOpenDialog(GUI2.this);
	        	
	        	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                // file = ch3.getSelectedFile();
	              	keyfield.setText(ch3.getSelectedFile().getAbsolutePath());
	        	 }
	        }});
	
		
		
		
	
	
		
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
