package App;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import HelperPack.Center;
import HelperPack.Holder;



public class ManufacPage extends JFrame {
	
Toolkit kit=Toolkit.getDefaultToolkit();
	
	public String EMAIL_ID=null;
	public boolean LOGIN_SUCCESS=false;
	
	
	public ManufacPage() {
		
		setTitle("Login Page");
		setSize(400,450);
		setLocation(Center.getCenter(kit.getScreenSize(), getSize()));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(kit.createImage("E:\\Bootcamp exercise\\ChatApp\\img\\ppcir.png"));
		setResizable(false);
		Container panel=getContentPane();
		panel.setLayout(new GridLayout(3,1));
		
		
		
		JLabel emailLabel=new JLabel("E-mail");
		emailLabel.setSize(100,20);
		emailLabel.setLocation(50,20);
		JLabel etick=new JLabel();
		etick.setSize(20,20);
		etick.setLocation(340,20);
		JTextField emailInput=new JTextField(20);
		emailInput.setSize(200,20);
		emailInput.setLocation(135,20);
		emailInput.setSelectionColor(Color.RED);
		
		JPanel emailPanel=new Holder(null).add(emailLabel,emailInput,etick);

		JLabel passwordLabel=new JLabel("password");
		passwordLabel.setSize(100,20);
		passwordLabel.setLocation(50,20);
		JPasswordField passwordInput=new JPasswordField(20);
		passwordInput.setSize(200,20);
		passwordInput.setLocation(135,20);
		passwordInput.setEchoChar('*');
		passwordInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email=emailInput.getText();
				
				if(email=="homedec21comp@gmail.com") {
					String password=emailInput.getText();
					if(password=="Homedec@12") {
						EMAIL_ID=email;
						LOGIN_SUCCESS=true;
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(passwordInput, "Incorrect Password", "Error in Login", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(emailInput, "Email ID not Found", "Error in Login", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JPanel passwordPanel=new Holder(null).add(passwordLabel,passwordInput);
		panel.add(new Holder(new GridLayout(2,1)).add(emailPanel,passwordPanel));
		
		
		JButton login=new JButton("Login");
		login.setSize(100,30);
		login.setCursor(new Cursor(Cursor.HAND_CURSOR));
		login.setLocation(50,0);
		JButton register=new JButton("Register");
		register.setSize(100,30);
		register.setLocation(230,0);
		register.setCursor(new Cursor(Cursor.HAND_CURSOR));
		JPanel buttonPanel= new Holder(null).add(login,register);
		
		login.addActionListener(passwordInput.getActionListeners()[0]);
		
		
		
		
		setVisible(true);
		
	}
	public static void main(String[] args) {
		ManufacPage l=new ManufacPage();
		l.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				JOptionPane.showMessageDialog(l, "Login Successful");
			}
		});
	}
	
}
