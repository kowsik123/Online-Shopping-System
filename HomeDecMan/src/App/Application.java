package App;

import javax.swing.*;

import Network.MI;

import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Application extends JFrame {
	static Dimension scsize=Toolkit.getDefaultToolkit().getScreenSize();
	public String PASSWORD;
	public String EMAIL_ID=null;
	public MI ob=null;
	
	public Application() throws Exception{
		setLayout(null);
		setSize(500,500);
		setMinimumSize(getSize());
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Bootcamp exercise\\Project1\\formIcon.png")); 
		setTitle("Manufacturer Module");
		Font f1=new Font("Times New Roman", Font.BOLD, 18);
		Font f2=new Font("Times New Roman", Font.PLAIN, 16);
		ob = (MI)Naming.lookup("rmi://localhost:1099/Manufacturer");
		setContentPane(new Login(this));
		setVisible(true);
	}
	public static void main(String[] args) {
		Application app=null;
		try {
			app=new Application();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(app, "Server Down", "Try again Later", JOptionPane.ERROR_MESSAGE);
		}
	}

}
