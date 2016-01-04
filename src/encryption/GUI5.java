package encryption;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI5 extends JDialog{
	static ImageIcon ic,ic2,ic3;
	static JLabel l1,l3;
	static JButton b1,b2,b3;
	static JTextField tf1,tf2;
	static JFileChooser ch1,ch2;
	static File file,folder;
	static JLabel background;

	static Container cp;
	GUI5(JFrame fr)
	{
		super(fr,"Select File and Folder",true);
		cp=getContentPane(); 
		
	//setSize (390, 300) ;
		setBounds(500, 250, 390, 300);
	cp.setLayout(null);

	 background=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/whit1.jpg"))));
	background.setBounds(00, 00, 370, 300);

	cp.add(background);

	b1=new JButton("Browse File");
	b1.setBounds(270, 125, 100, 20);

	b2=new JButton("Browse Folder");
	b2.setBounds(270, 165, 100, 20);

	b3=new JButton("Check!");
	b3.setBounds(125, 215, 100, 20);
	b3.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(file!=null && folder!=null)
			{
				try {
					String [] array=FileEncryption.checkSum(file, folder);
					if(array[0].equals(array[1]))
						l1.setIcon(ic2);
					else
						l1.setIcon(ic3);
					JOptionPane.showMessageDialog(getParent(), "File1: "+array[0]+"\nFile2: "+array[1]);
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	});

	ic=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/ajax-loader.gif")));
	ic2=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/shredded.jpg")));
	ic3=new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/error.jpg")));
	l1=new JLabel(ic);
	l1.setBounds(140,20,80,80);
	l1.setVisible(true);

	l3=new JLabel("<html> <font color='blue'>Select the file for comparing MD5 values</font></html>");
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

	ch1=new JFileChooser(".");
	ch1.setFileSelectionMode(JFileChooser.FILES_ONLY);

	b1.addActionListener(new ActionListener() {
			 
	    public void actionPerformed(ActionEvent e)
	    {
	   	 int returnVal = ch1.showOpenDialog(GUI5.this);
	   	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	   		 
	   		 file = ch1.getSelectedFile();
	   		 tf1.setText(file.getAbsolutePath());	 
	   		 
	   	 }
	   	 
	    	
	    }});


	ch2=new JFileChooser(".");

	ch2.setFileSelectionMode(JFileChooser.FILES_ONLY);
	b2.addActionListener(new ActionListener() {
		 
	    public void actionPerformed(ActionEvent e)
	    {
		 int returnVal1 = ch2.showOpenDialog(GUI5.this);
		 if (returnVal1 == JFileChooser.APPROVE_OPTION) {
			 folder=ch2.getSelectedFile();
	   		 tf2.setText(folder.getAbsolutePath());

			 
		 }
	    }
	});

		
		
		
		
		
		
	}
}
