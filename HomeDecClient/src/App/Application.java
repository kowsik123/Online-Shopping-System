package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.DefaultColorSelectionModel;

import HelperPack.Center;
import HelperPack.ProductLayout;
import HelperPack.TransScrollBarUI;
import Network.CI;

public class Application extends JFrame{
	
	public String EMAIL_ID=null;
	public String PASSWORD=null;
	public CI ob=null;
	
	public Application() throws Exception{
		ob=(CI)Naming.lookup("rmi://localhost:1099/Client");
		setTitle("HomeDec Shoping");
		setSize(500,500);
		setMinimumSize(getSize());
		setPreferredSize(getSize());
		setIconImage(this.getToolkit().createImage("E:\\eclipse\\ProjectForm\\src\\img\\icon2.png"));
		setLocation(Center.getCenter(getToolkit().getScreenSize(), getSize()));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		JPanel lo=new Login(this);
		setContentPane(lo);

		setVisible(true);
	}
	public static void main(String[] args) {
		Application j=null;
		try {
			j=new Application();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(j, "Error", "Got Error in Application", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}