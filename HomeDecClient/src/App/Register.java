package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.*;
import HelperPack.*;
import Network.CI;

public class Register extends JFrame{
	Toolkit kit=Toolkit.getDefaultToolkit();
	JButton b1=null;
	public static final int bool_f=0,bool_m=1,bool_e=2,bool_p=3,bool_cp=4,bool_d=5,bool_s=6,bool_c=7,bool_pin=8;
	boolean[] bool={false,false,false,false,false,false,false,false,false};
	public Register() {
		b1=new JButton("Register");
		b1.setEnabled(false);
		setTitle(" Registation Form");
		setLayout(null);
		setSize(490,580);
		setLocation(Center.getCenter(kit.getScreenSize(), getSize()));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(kit.createImage("E:\\eclipse\\ProjectForm\\src\\img\\icon2.png"));
		setResizable(false);
		Container panel=getContentPane();
		panel.setBackground(Color.white);
		panel.setLayout(null);
		
		JLabel title=new JLabel("Registration Form",SwingConstants.CENTER);
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
		l2.setLocation(80,120);
		l2.setSize(100,20);
		JTextField t2=new JTextField(20);
		t2.setLocation(200,120);
		t2.setSize(200,20);
		t2.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				bool[bool_m]=isMobileNumber(t2.getText());
				b1Render();
				if(bool[bool_m]) t2.setForeground(Color.BLACK);
				else t2.setForeground(Color.RED);
			}
		});
		t2.addActionListener(trans);
		JLabel l3=new JLabel("E-mail");
		l3.setLocation(80,160);
		l3.setSize(100,20);
		JTextField t3=new JTextField(20);
		t3.setLocation(200,160);
		t3.setSize(200,20);
		t3.addActionListener(trans);
		t3.setForeground(Color.RED);
		t3.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				bool[bool_e]=isEmail(t3.getText());
				if(bool[bool_e]) {
					t3.setForeground(Color.black);
				}
				else t3.setForeground(Color.RED);
				b1Render();
			}
		});
		JLabel l4=new JLabel("Create Password");
		l4.setLocation(80,200);
		l4.setSize(100,20);
		JPasswordField t4=new JPasswordField(20);
		t4.setEchoChar('*');
		t4.setToolTipText("<html><p width=\"200\">"
							+"password should<br>"
							+"&emsp*have atleast 8 charcters<br>"
							+"&emsp*contain a uppercase letter<br>"
							+"&emsp*contain a special character<br>"
							+"&emsp*contain a number"
							+"</p></html>");
		t4.setLocation(200,200);
		t4.setSize(200,20);
		t4.setForeground(Color.RED);
		t4.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				int passcount=isPassword(t4.getText());
				Graphics g=panel.getGraphics();
				g.setColor(Color.GRAY);
				int start=201;
				g.fillRoundRect(start+0, 222, 45, 4, 2, 2);
				g.fillRoundRect(start+50, 222, 45, 4, 2, 2);
				g.fillRoundRect(start+100, 222, 45, 4, 2, 2);
				g.fillRoundRect(start+150, 222, 45, 4, 2, 2);
				
				g.setColor(new Color(0,255,127));
				for(int i=0;i<passcount;i++) {
					g.fillRoundRect(start+50*i, 222, 45, 4, 2, 2);
				}
				
				bool[bool_p]=(passcount==4)? true:false;
				if(bool[bool_p]) t4.setForeground(Color.black);
				else t4.setForeground(Color.RED);
				b1Render();
			}
		});
		t4.addActionListener(trans);
		JLabel l5=new JLabel("Confirm Password");
		l5.setLocation(80,240);
		l5.setSize(120,20);
		JPasswordField t5=new JPasswordField(20);
		t5.setLocation(200,240);
		t5.setSize(200,20);
		t5.setEchoChar('*');
		t5.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				int passcount=isPassword(t5.getText());
				Graphics g=panel.getGraphics();
				g.setColor(Color.GRAY);
				int start=201;
				g.fillRoundRect(start+0, 262, 45, 4, 2, 2);
				g.fillRoundRect(start+50, 262, 45, 4, 2, 2);
				g.fillRoundRect(start+100, 262, 45, 4, 2, 2);
				g.fillRoundRect(start+150, 262, 45, 4, 2, 2);
				
				g.setColor(new Color(0,255,127));
				for(int i=0;i<passcount;i++) {
					g.fillRoundRect(start+50*i, 262, 45, 4, 2, 2);
				}
				bool[bool_cp]=t4.getText().equals(t5.getText());
				if(bool[bool_cp]) t5.setForeground(Color.black);
				else t5.setForeground(Color.RED);
				b1Render();
			}
		});
		t5.addActionListener(trans);
		JLabel l6=new JLabel("Door no");
		l6.setLocation(80,280);
		l6.setSize(100,20);
		
		JTextField t6=new JTextField();
		t6.setLocation(200,280);
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
		l7.setLocation(80,320);
		l7.setSize(100,20);
		
		JTextField t7=new JTextField();
		t7.setLocation(200,320);
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
		l8.setLocation(80,360);
		l8.setSize(100,20);
		
		JTextField t8=new JTextField(20);
		t8.setLocation(200,360);
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
		l9.setLocation(80,400);
		l9.setSize(100,20);
		
		JTextField t9=new JTextField();
		t9.setLocation(200,400);
		t9.setSize(100,20);
		t9.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				if(t9.getText().length()<1) bool[bool_pin]=false;
				else bool[bool_pin]=true;
				b1Render();
			}
		});
		t9.addActionListener(trans);
		
		b1.setSize(100, 25);
		b1.setLocation(200,460);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String address=  t6.getText()+",\n"
								+t7.getText()+",\n"
								+t8.getText()+",\n"
								+t9.getText();
				String[] arr= {t1.getText(),t3.getText(),t5.getText(),t2.getText(),address};
				try {
					CI ss = (CI)Naming.lookup("rmi://localhost:1099/Client");
					if(ss.register(arr)) {
						JOptionPane.showMessageDialog(panel, "Registation Success", "Login status", JOptionPane.DEFAULT_OPTION );
					}
					else {
						throw new Exception();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(panel, "Try Again Later", "Server Down", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				System.exit(DISPOSE_ON_CLOSE);
			}
		});
		
		panel.add(title);
		panel.add(l1);
		panel.add(t1);
		panel.add(l2);
		panel.add(t2);
		panel.add(l3);
		panel.add(t3);
		panel.add(l4);
		panel.add(t4);
		panel.add(l5);
		panel.add(t5);
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
	protected static int isPassword(String text) {
		boolean[] p= {(text.length()>7)? true:false,Pattern.compile("[!@#$%^&*_]").matcher(text).find(),Pattern.compile("[A-Z]").matcher(text).find(),Pattern.compile("[0-9]").matcher(text).find()};
		int count=0;
		for(boolean i:p) if(i) count++;
		return count;
	}
	private void b1Render(){
		System.out.println();
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
	public static void main(String[] args) {
		new Register();
	}
}