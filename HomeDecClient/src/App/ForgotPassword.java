package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import HelperPack.Center;
import HelperPack.ImgPack;
import Network.CI;

public class ForgotPassword extends JFrame{
	Toolkit kit=Toolkit.getDefaultToolkit();
	JButton b1;
	JTextField t1;
	private boolean bool_p=false;
	private boolean bool_cp=false;
	private boolean bool_otp=false;
	protected boolean bool_o=false;
	public ForgotPassword(CI ob) {
		setTitle("Reset Password");
		setLayout(null);
		setSize(450,400);
		setLocation(Center.getCenter(kit.getScreenSize(), getSize()));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(kit.createImage("E:\\eclipse\\ProjectForm\\src\\img\\icon2.png"));
		setResizable(false);
		Container panel=getContentPane();
		panel.setLayout(null);
		Font f=new Font(Font.SANS_SERIF,Font.BOLD,15);
		
		JLabel title=new JLabel("Reset Password",SwingConstants.CENTER);
		title.setSize(200,40);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		title.setLocation(125,20);
		ActionListener trans = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((Component)e.getSource()).transferFocus();
			}
		};
		JLabel l1=new JLabel("E-mail");
		l1.setLocation(50,80);
		l1.setSize(100,20);
		JLabel etick=new JLabel();
		etick.setSize(20,20);
		etick.setLocation(375,80);
		t1=new JTextField(20);
		t1.setLocation(170,80);
		t1.setSize(200,20);
		t1.addActionListener(trans);
		
		JButton so=new JButton("Send OTP");
		so.setEnabled(false);
		so.setSize(100,25);
		so.setLocation(170,115);
		so.addActionListener((event)->{
			new Thread(()->{
				try {
					bool_otp=ob.requestResetPassword(t1.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();			
		});
		
		JLabel l2=new JLabel("Create Password");
		l2.setLocation(50,160);
		l2.setSize(100,20);
		JPasswordField t2=new JPasswordField(20);
		t2.setEchoChar('*');
		t2.setToolTipText("<html><p width=\"200\">"
							+"password should<br>"
							+"&emsp*have atleast 8 charcters<br>"
							+"&emsp*contain a uppercase letter<br>"
							+"&emsp*contain a special character<br>"
							+"&emsp*contain a number"
							+"</p></html>");
		t2.setLocation(170,160);
		t2.setSize(200,20);
		t2.setForeground(Color.RED);
		t2.getDocument().addDocumentListener(new Register.TextListener() {

			@Override
			public void update(DocumentEvent e) {
				int passcount=Register.isPassword(t2.getText());
				Graphics g=panel.getGraphics();
				g.setColor(Color.GRAY);
				int start=171;
				g.fillRoundRect(start+0, 182, 45, 4, 2, 2);
				g.fillRoundRect(start+50, 182, 45, 4, 2, 2);
				g.fillRoundRect(start+100, 182, 45, 4, 2, 2);
				g.fillRoundRect(start+150, 182, 45, 4, 2, 2);
				
				g.setColor(new Color(0,255,127));
				for(int i=0;i<passcount;i++) {
					g.fillRoundRect(start+50*i, 182, 45, 4, 2, 2);
				}
				
				bool_p=(passcount==4)? true:false;
				if(bool_p) t2.setForeground(Color.black);
				else t2.setForeground(Color.RED);
				b1.setEnabled(bool_p && bool_cp && bool_otp);
			}
		});
		t2.addActionListener(trans);
		JLabel l3=new JLabel("Confirm Password");
		l3.setLocation(50,200);
		l3.setSize(120,20);
		JPasswordField t3=new JPasswordField(20);
		t3.setLocation(170,200);
		t3.setSize(200,20);
		t3.setEchoChar('*');
		t3.getDocument().addDocumentListener(new Register.TextListener() {
			@Override
			public void update(DocumentEvent e) {
				int passcount=Register.isPassword(t3.getText());
				Graphics g=panel.getGraphics();
				g.setColor(Color.GRAY);
				int start=171;
				g.fillRoundRect(start+0, 222, 45, 4, 2, 2);
				g.fillRoundRect(start+50, 222, 45, 4, 2, 2);
				g.fillRoundRect(start+100, 222, 45, 4, 2, 2);
				g.fillRoundRect(start+150, 222, 45, 4, 2, 2);
				
				g.setColor(new Color(0,255,127));
				for(int i=0;i<passcount;i++) {
					g.fillRoundRect(start+50*i, 222, 45, 4, 2, 2);
				}
				bool_cp=t2.getText().equals(t3.getText());
				if(bool_cp) t3.setForeground(Color.black);
				else t3.setForeground(Color.RED);
				b1.setEnabled(bool_p && bool_cp && bool_otp);
			}
		});
		t3.addActionListener(trans);
		
		t1.getDocument().addDocumentListener(new Register.TextListener() {
			@Override
			public void update(DocumentEvent e) {
				String s=t1.getText();
				if(Register.isEmail(s)) {
					etick.setIcon(new ImageIcon(new ImgPack("E:\\eclipse\\ProjectForm\\src\\img\\tick.gif").getImage(16,16)));
					so.setEnabled(true);
				}
				else {
					etick.setIcon(null);
					so.setEnabled(false);
				}
			}
		});
		
		JLabel otp=new JLabel("Enter OTP");
		otp.setLocation(50,240);
		otp.setSize(100,20);
		JTextField t4 = new JTextField(20);
		t4.setLocation(170,240);
		t4.setSize(200,20);
		t4.addActionListener(trans);
		t4.getDocument().addDocumentListener(new Register.TextListener() {
			@Override
			public void update(DocumentEvent e) {
				String s=t4.getText();
				bool_o= isOTP(s);
				b1.setEnabled(bool_p && bool_cp && bool_otp && bool_o);
			}
		});
		
		b1=new JButton("Reset");
		b1.setEnabled(false);
		b1.setSize(100,30);
		b1.setLocation(170,290);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if( ob.changePassword(t1.getText(), t4.getText(), t3.getText())) {
						dispose();
					}
					else JOptionPane.showMessageDialog(b1, "Enter Correct OTP");
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(b1, "Enter Correct OTP");
					e1.printStackTrace();
				}
			}
		});
		
		panel.add(title);
		
		panel.add(l1);
		panel.add(t1);
		panel.add(etick);
		panel.add(so);
		
		panel.add(l2);
		panel.add(t2);
		panel.add(l3);
		panel.add(t3);
		panel.add(otp);
		panel.add(t4);
		
		panel.add(b1);
		
		setVisible(true);
	}
	protected boolean isOTP(String s) {
		Pattern mn=Pattern.compile("[0-9]{4}");
		return mn.matcher(s).matches();
	}
//	public static void main(String[] args) {
//		new ForgotPassword(null);
//	}
}