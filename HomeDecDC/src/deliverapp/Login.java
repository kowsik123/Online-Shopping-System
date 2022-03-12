package deliverapp;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Network.CI;
import Network.DI;
import Network.LoginConstants;
import Network.PaymentConstants;

public class Login extends KeyAdapter implements ActionListener{
	static JFrame fr_login;
	JLabel img_back;
	JLabel lb_Login;
	JLabel lb_email;	
	JLabel lb_pwd;

	JTextField txtbox_email;
	JPasswordField 	pwdbox_pwd;
	public DI objdi=null;
	JButton but_login;
	public String EMAIL="";
	public String PASSWORD="";
	public Login() {
		try {
			objdi=(DI)Naming.lookup("rmi://localhost:1099/DeliveryCenter");
		} catch (Exception e) {
			e.printStackTrace();
		}
		fr_login=new JFrame("Delivery Center");
		Image img=new ImgPack("E://img//DELIVERYCENTER.jpg").getImage(800, 600);
		img_back=new JLabel(new ImageIcon(img));
		fr_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lb_Login=new JLabel("LOGIN");
		lb_Login.setBounds(340,80,150,30);
		lb_Login.setFont(new Font("LOGIN", 1, 28));
		lb_Login.setForeground(Color.BLUE);

		lb_email=new JLabel("Email");
		lb_email.setBounds(180,200,150,30);
		lb_email.setFont(new Font(null, 1, 22));
		lb_email.setForeground(Color.cyan);

		txtbox_email=new JTextField();
		txtbox_email.setBounds(440, 200, 150, 30);
		txtbox_email.addKeyListener(this);

		lb_pwd=new JLabel("Password");
		lb_pwd.setBounds(180, 250, 150, 30);
		lb_pwd.setFont(new Font(null, 1, 22));
		lb_pwd.setForeground(Color.cyan);
		
		pwdbox_pwd=new JPasswordField();
		pwdbox_pwd.setBounds(440, 250, 150, 30);
		pwdbox_pwd.addKeyListener(this);

		but_login=new JButton(new ImageIcon(new ImgPack("E:\\img\\images.jpeg").getImage(90, 40)));
		but_login.setBounds(350, 380, 80, 30);
		but_login.addActionListener(this);

		fr_login.setContentPane(img_back);
		fr_login.add(lb_Login);
		fr_login.add(lb_email);
		fr_login.add(txtbox_email);
		fr_login.add(lb_pwd);
		fr_login.add(pwdbox_pwd);
		fr_login.add(but_login);
		fr_login.pack();

		fr_login.setBounds(20, 20, 800, 600);
		fr_login.setLayout(null);
		fr_login.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EMAIL=txtbox_email.getText();
			PASSWORD= pwdbox_pwd.getText();
			int status=objdi.authenticate(EMAIL,PASSWORD);
			switch(status) {
				case LoginConstants.AUTHENTICATION_SUCCESS:
					 fr_login.setContentPane(new ApplicationPanel(this).panel_app);
					 fr_login.setVisible(true);
				     break;
				case LoginConstants.INVALID_EMAIL_ID:
					JOptionPane.showMessageDialog(fr_login, "Invalid Email Id");
				    break;
				case LoginConstants.PASSWORD_INCORRECT:
					JOptionPane.showMessageDialog(fr_login, "Password Incorrect");
					break;
				default:
					break;
			}			
		}catch (Exception ex) {
			 ex.printStackTrace();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		EMAIL=txtbox_email.getText();
		PASSWORD= pwdbox_pwd.getText();
		if(e.getKeyCode()==KeyEvent.VK_ENTER&&e.getSource()==txtbox_email) pwdbox_pwd.grabFocus();
		if(e.getKeyCode()==KeyEvent.VK_ENTER&&e.getSource()==pwdbox_pwd) {
			try {
				int	status=objdi.authenticate(EMAIL, PASSWORD);
				switch(status) {
					case LoginConstants.AUTHENTICATION_SUCCESS:
						fr_login.setContentPane(new ApplicationPanel(this).panel_app);
					     break;
					case LoginConstants.INVALID_EMAIL_ID:
						JOptionPane.showMessageDialog(fr_login, "Invalid Email Id");
					    break;
					case LoginConstants.PASSWORD_INCORRECT:
						JOptionPane.showMessageDialog(fr_login, "Password Incorrect");
						break;
					default:
						break;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void reset() {
		SwingUtilities.updateComponentTreeUI(fr_login);
		fr_login.invalidate();
		fr_login.validate();
		fr_login.repaint();
	}

	public static void main(String[] args) {
		Login login=new Login();
	}
}