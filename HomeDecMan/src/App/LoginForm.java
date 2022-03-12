package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

import Network.LoginConstants;
import Network.MI;
public class LoginForm extends JPanel {
	static Dimension scsize=Toolkit.getDefaultToolkit().getScreenSize();
	public LoginForm(Application jp) {
		Application parent=jp;
		
		Font f1=new Font("Times New Roman", Font.BOLD, 18);
		Font f2=new Font("Times New Roman", Font.PLAIN, 16);
		
		setBackground(Color.WHITE);
		setLayout(null);
		JLabel lp=new JLabel("Manufacturer Login");
		lp.setFont(f1);
		lp.setBounds(300,50, 250,30);
		lp.setHorizontalAlignment(SwingConstants.CENTER);
		lp.setVerticalAlignment(SwingConstants.CENTER);
		
		JLabel em=new JLabel("E-mail ID :");
		em.setBounds(200, 130,80,30);
		em.setFont(f2);
		JTextField emt=new JTextField();
		emt.setBounds(280,130,300, 30);
		
		JLabel pwd=new JLabel("Password :");
		pwd.setBounds(200, 190,80,30);
		pwd.setFont(f2);
		JTextField pwdt=new JTextField();
		pwdt.setBounds(280,190,300, 30);
		
		//Componentlistener center
		
		JButton b1=new JButton("LOGIN");
		b1.setSize(100, 30);
		b1.setLocation(200,270);
		
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.EMAIL_ID=emt.getText();
				parent.PASSWORD=pwdt.getText();
				
				try {
					int status = parent.ob.authenticate(parent.EMAIL_ID, parent.PASSWORD);
//					status=LoginConstants.AUTHENTICATION_SUCCESS;
					if(status==LoginConstants.AUTHENTICATION_SUCCESS) {
						jp.setContentPane(new TablePage(jp));
						jp.setVisible(true);
					}
					else if(status==LoginConstants.PASSWORD_INCORRECT) {
							JOptionPane.showMessageDialog(pwdt, "Incorrect Password", "Error in Login", JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(emt, "Email ID not Found", "Error in Login", JOptionPane.ERROR_MESSAGE);
					}
				} 
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		 add(lp);
		 add(em); add(emt);
		 add(pwd); add(pwdt);
		 add(b1);
	}
}
