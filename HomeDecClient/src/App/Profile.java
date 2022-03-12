package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.*;
import HelperPack.*;
import Network.CI;

public class Profile extends JFrame{
	Toolkit kit=Toolkit.getDefaultToolkit();
	JButton b1=null;
	public static final int bool_f=0,bool_m=1,bool_e=2,bool_d=3,bool_s=4,bool_c=5,bool_pin=6;
	boolean[] bool={false,false,false,false,false,false,false};
	String[] data=null;
	Application parent;
	public Profile(Application j) {
		parent=j;
		b1=new JButton("save");
		b1.setEnabled(false);
		setTitle("Profile Page");
		setLayout(null);
		setSize(490,450);
		setLocation(Center.getCenter(kit.getScreenSize(), getSize()));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(kit.createImage("E:\\eclipse\\ProjectForm\\src\\img\\icon2.png"));
		setResizable(false);
		Container panel=getContentPane();
		panel.setLayout(null);
		
		JLabel title=new JLabel("Your Profile",SwingConstants.CENTER);
		title.setSize(160,40);
		title.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		title.setLocation(160,20);
		JLabel l1=new JLabel("Name");
		l1.setLocation(80,80);
		l1.setSize(100,20);
		JTextField t1=new JTextField(20);
		t1.setLocation(200,80);
		t1.setSize(200,20);
		ActionListener trans = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((Component)e.getSource()).transferFocus();
			}
		};
		t1.addActionListener(trans);
		t1.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				if(t1.getText().length()<1) bool[bool_f]=false;
				else bool[bool_f]=true;
				b1Render();
			}
		});
		
		JLabel l2=new JLabel("Mobile Number");
		l2.setLocation(80,160);
		l2.setSize(100,20);
		JTextField t2=new JTextField(20);
		t2.setLocation(200,160);
		t2.setSize(200,20);
		t2.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				bool[bool_m]=isMobileNumber(t2.getText());
				b1Render();
				if(bool[bool_m]) t2.setForeground(Color.GREEN);
				else t2.setForeground(Color.RED);
			}
		});
		t2.addActionListener(trans);
		
		JLabel l3=new JLabel("E-mail");
		l3.setLocation(80,120);
		l3.setSize(100,20);
		JTextField t3=new JTextField(20);
		t3.setLocation(200,120);
		t3.setSize(200,20);
		t3.addActionListener(trans);
		t3.setForeground(Color.RED);
		t3.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				bool[bool_e]=isEmail(t3.getText());
				if(bool[bool_e]) {
					t3.setForeground(Color.GREEN);
				}
				else t3.setForeground(Color.RED);
				b1Render();
			}
		});
		
		JLabel l6=new JLabel("Door no");
		l6.setLocation(80,200);
		l6.setSize(100,20);
		
		JTextField t6=new JTextField();
		t6.setLocation(200,200);
		t6.setSize(35,20);
		t6.addActionListener(trans);
		t6.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				if(t6.getText().length()<1) bool[bool_d]=false;
				else bool[bool_d]=true;
				b1Render();
			}
		});
		
		JLabel l7=new JLabel("Street");
		l7.setLocation(80,240);
		l7.setSize(100,20);
		
		JTextField t7=new JTextField();
		t7.setLocation(200,240);
		t7.setSize(200,20);
		t7.addActionListener(trans);
		t7.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				if(t7.getText().length()<1) bool[bool_s]=false;
				else bool[bool_s]=true;
				b1Render();
			}
		});
		
		JLabel l8=new JLabel("City");
		l8.setLocation(80,280);
		l8.setSize(100,20);
		
		JTextField t8=new JTextField(20);
		t8.setLocation(200,280);
		t8.setSize(200,20);
		t8.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				if(t8.getText().length()<1) bool[bool_c]=false;
				else bool[bool_c]=true;
				b1Render();
			}
		});
		t8.addActionListener(trans);
		
		JLabel l9=new JLabel("Pincode");
		l9.setLocation(80,320);
		l9.setSize(100,20);
		
		JTextField t9=new JTextField();
		t9.setLocation(200,320);
		t9.setSize(100,20);
		t9.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				bool[bool_pin]=isPincode(t9.getText());
				b1Render();
				if(bool[bool_pin]) t9.setForeground(Color.GREEN);
				else t9.setForeground(Color.RED);
			}
		});
		t9.addActionListener(trans);
		
		try {
			data=parent.ob.getProfileDetails(parent.EMAIL_ID,parent.PASSWORD);
		} catch (RemoteException e1) {e1.printStackTrace();}
		
		//{userid,name,email,mobile_number,address}
		t1.setText(data[1]);
		t2.setText(data[3]);
		t3.setText(data[2]);
		String address[]=data[4].split("[,][\n]");
		System.out.println(address.length);
		t6.setText(address[0]);
		t7.setText(address[1]);
		t8.setText(address[2]);
		t9.setText(address[3]);
		
		b1.setSize(100, 25);
		b1.setLocation(200,370);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String address=  t6.getText()+",\n"
								+t7.getText()+",\n"
								+t8.getText()+",\n"
								+t9.getText();
				String[] arr= {t1.getText(),t3.getText(),t2.getText(),address};
				try {
					CI ss = (CI)Naming.lookup("rmi://localhost:1099/Client");
					if(ss.updateProfile(arr, parent.EMAIL_ID, parent.PASSWORD)) {
						JOptionPane.showMessageDialog(panel, "Successfully Saved", "Status", JOptionPane.DEFAULT_OPTION );
					}
					else {
						throw new Exception("error in upload");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(panel, "Try Again Later", "Server Down", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				dispose();
			}
		});
		
		panel.add(title);
		panel.add(l1);
		panel.add(t1);
		panel.add(l2);
		panel.add(t2);
		panel.add(l3);
		panel.add(t3);
		
		panel.add(l6);
		panel.add(t6);
		panel.add(l7);
		panel.add(t7);
		panel.add(l8);
		panel.add(t8);
		panel.add(l9);
		panel.add(t9);
		
		panel.add(b1);
		
		setVisible(true);
	}
	protected static boolean isMobileNumber(String text) {
		Pattern mn=Pattern.compile("[0-9]{10}");
		return mn.matcher(text).matches();
	}
	protected static boolean isPincode(String text) {
		Pattern mn=Pattern.compile("[0-9]{6}");
		return mn.matcher(text).matches();
	}
	protected static int isPassword(String text) {
		boolean[] p= {(text.length()>7)? true:false,Pattern.compile("[!@#$%^&*_]").matcher(text).find(),Pattern.compile("[A-Z]").matcher(text).find(),Pattern.compile("[0-9]").matcher(text).find()};
		int count=0;
		for(boolean i:p) if(i) count++;
		return count;
	}
	private void b1Render(){
//		System.out.println(Arrays.to);
		boolean t=true;
		for(boolean i: bool) { 
			if(!i) {
				t=false;
				break;
			}
		}
		b1.setEnabled(t);
	}
	protected static boolean isEmail(String text) {
		Pattern mn=Pattern.compile("[0-9a-zA-Z]+[@][a-zA-Z]+[.].+");
		return mn.matcher(text).matches();
	}
	public static interface TextListener extends DocumentListener {
	    void update(DocumentEvent e);
	    @Override
	    default void insertUpdate(DocumentEvent e) {
	        update(e);
	    }
	    @Override
	    default void removeUpdate(DocumentEvent e) {
	        update(e);
	    }
	    @Override
	    default void changedUpdate(DocumentEvent e) {
	        update(e);
	    }
	}
//	public static void main(String[] args) {
////		new Profile();
//	}
}