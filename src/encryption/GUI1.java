package encryption;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.crypto.NoSuchPaddingException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class GUI1 extends JDialog{

	static Container cp;
	static JButton b1,b2;
	static JTextField tf1;
	static JLabel l1,l2,background,l3;
	static ImageIcon ic,ic2;
	static JFileChooser ch1,ch2;
	static File file;
	static JPasswordField ps;
	static JCheckBox cb1;
	static JRadioButton blow,aes;
	static JRadioButton[] buttarray;
	static ButtonGroup bgrp;
	static JButton okbutt;
	String algo;

	
	GUI1(JFrame fr )
	{
		super ( fr, "Encrypt a File", true ) ; 
		cp=getContentPane(); 
		
    //setSize (370, 300) ;
		setBounds(500, 250, 500, 300);
    cp.setLayout(null);
    
	ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/file.jpg")));
	ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/folder.jpg")));
	
	ps=new JPasswordField(8);
	ps.setVisible(true);
	ps.setBounds(60,190,90,20);
	
	 background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
	 background.setBounds(00, 00, 500, 290);
	 cp.add(background);
	b1=new JButton();
	b1.setIcon(ic);
    b1.setVisible(true);
	b1.setBounds(10, 50, ic.getIconWidth(), ic.getIconHeight());

    
    cb1=new JCheckBox("Use password as key");
    //cb1.setSelected(false);
    cb1.setBackground(Color.WHITE);
    cb1.setBounds(200, 190, 140, 20);
    
    bgrp=new ButtonGroup();
    buttarray=new JRadioButton[2];
    aes=new JRadioButton("AES",true);
    aes.setBounds(375, 185, 50, 30);
    aes.setBackground(Color.WHITE);
    
    blow=new JRadioButton("Blowfish");
    blow.setBounds(375,210,140,20);
    blow.setBackground(Color.WHITE);
    buttarray[0]=aes;
    buttarray[1]=blow;
    bgrp.add(aes);
    bgrp.add(blow);
  
    b2=new JButton();
	b2.setIcon(ic2);
    b2.setVisible(true);
	b2.setBounds(170, 50, ic2.getIconWidth(), ic2.getIconHeight());

    okbutt=new JButton();
    okbutt.setText("Done");
    okbutt.setVisible(true);
    okbutt.setBounds(150, 220, 60, 25);;
    okbutt.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			file=new File(tf1.getText());
			if(!(tf1.getText().equals("") ||ps.getText().equals("")))
			{
			if(file.isDirectory())
				try {
					file=FileEncryption.createZip(file);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			String pass=new String(ps.getPassword());
			try {
				FileEncryption enc=new FileEncryption(file);
				for(JRadioButton j:buttarray)
				{   
					if(j.isSelected())
						FileEncryption.setAlgo(j.getText());
				}
				enc.encrypt(FileEncryption.generateKey(pass), pass,cb1.isSelected());
				MyLogger.logged("Encrpyted "+file.getAbsolutePath());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			finally
			{
				JOptionPane.showMessageDialog(getParent(), "Encrypted!");
			}
			}
		}
	});
    background.add(okbutt);
	l1=new JLabel("PATH");
	l1.setVisible(true);
	l1.setBounds(10, 155, 40, 20);
	
	l2=new JLabel("Password");
	l2.setVisible(true);
	l2.setBounds(10, 190, 80, 20);
	
	l3=new JLabel("<html> <font color='blue'>Choose either the File or the Folder to be Encrypted</font></html>");
	l3.setVisible(true);
	l3.setBounds(10, 10, 280, 20);
	
	tf1=new JTextField(190);
	tf1.setVisible(true);
	tf1.setBounds(60, 155, 250, 20);
	
	background.add(blow);
    background.add(aes);
    background.add(b1);
    background.add(b2);
    background.add(cb1);
    background.add(l1);
    background.add(l2);
    background.add(l3);

    background.add(tf1);
    
    background.add(cb1);
    background.add(ps);

 


    ch1=new JFileChooser(".");
    ch1.setFileSelectionMode(JFileChooser.FILES_ONLY);
	
	b1.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e)
        {
        	int returnVal = ch1.showOpenDialog(GUI1.this);
        	
        	 if (returnVal == JFileChooser.APPROVE_OPTION) {
                // file = ch1.getSelectedFile();
              	tf1.setText(ch1.getSelectedFile().getAbsolutePath());
        	 }
        }});
	
	
	 ch2=new JFileChooser(".");
	    ch2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		b2.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e)
	        {
	        	int returnVal = ch2.showOpenDialog(GUI1.this);
	        	
	        	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                 //file = ch2.getSelectedFile();
	              	tf1.setText(ch2.getSelectedFile().getAbsolutePath());
	        	 }
	        }});
	
	
	if(cb1.isSelected())
	{
		ps.setVisible(true);
		
		
		
	}
		
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
	
	
}
