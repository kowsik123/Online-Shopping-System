package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicTextFieldUI;

import HelperPack.*;
import Network.LoginConstants;

public class Login extends JPanel{
	Application parent;
	int value=0;
	Color[] colors= {Color.blue,Color.CYAN,Color.DARK_GRAY,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW,new Color(255,233,0),Color.GREEN.darker()};
	public Login(Application j) {
		parent=j;
				
		Font f=new Font(Font.SANS_SERIF, Font.PLAIN , 16);
		
		setLayout(null);
		JLabel bg=new JLabel();
		bg.setIcon(new ImageIcon( new ImgPack("E:\\img\\bg.jpg").getImage(1281,658)));
		
		JLabel color=new JLabel(new ImageIcon( new ImgPack("E:\\img\\color.png").getImage(35,35)));
		color.setSize(35,35);
		color.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JPanel cont=new JPanel(null);
		cont.setBackground(new Color(0,0,0,100));
		cont.setSize(400,450);
		
		JLabel img=new JLabel(new ImageIcon(new ImgPack("E:\\eclipse\\ProjectForm\\src\\img\\icon2.png").getImage(50,50)));
		img.setSize(400,50);
		img.setLocation(0, 70);
		cont.add(img);
		
		int emailY=190;
		
		JLabel emailLabel=new JLabel("E-mail");
		emailLabel.setSize(100,20);
		emailLabel.setLocation(50,emailY);
		emailLabel.setFont(f);
		emailLabel.setForeground(Color.white);
		JLabel etick=new JLabel();
		etick.setSize(20,20);
		etick.setLocation(340,emailY);
		JTextField emailInput=new JTextField(20);
		emailInput.setSize(200,20);
		emailInput.setBackground(Color.blue);
		emailInput.setForeground(Color.white);
		emailInput.setLocation(135,emailY);
		emailInput.setSelectionColor(Color.RED);
		emailInput.setUI(new BasicTextFieldUI());
		emailInput.setBorder(new LineBorder(Color.BLUE, 3));
		emailInput.getDocument().addDocumentListener(new TextListener() {
			@Override
			public void update(DocumentEvent e) {
				String s=emailInput.getText();
				if(isEmail(s)) {
					etick.setIcon(new ImageIcon(new ImgPack("E:\\eclipse\\ProjectForm\\src\\img\\tick.gif").getImage(16,16)));
				}
				else {
					etick.setIcon(null);
				}
				repaint();
			}
		});
		
		cont.add(emailLabel);
		cont.add(emailInput);
		cont.add(etick);
		
		int passwordY=260;
		
		JLabel passwordLabel=new JLabel("password");
		passwordLabel.setSize(100,20);
		passwordLabel.setLocation(50,passwordY);
		passwordLabel.setFont(f);
		passwordLabel.setForeground(Color.white);
		JPasswordField passwordInput=new JPasswordField(20);
		passwordInput.setBackground(Color.blue);
		passwordInput.setForeground(Color.white);
		passwordInput.setSelectionColor(Color.RED);
		passwordInput.setUI(new BasicTextFieldUI());
		passwordInput.setBorder(new LineBorder(Color.BLUE, 3));
		passwordInput.setSize(200,20);
		passwordInput.setLocation(135,passwordY);
		passwordInput.setEchoChar('*');
		passwordInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.EMAIL_ID=emailInput.getText();
				parent.PASSWORD=passwordInput.getText();
		        
				int status=10;
				try {
					status = parent.ob.authenticate(parent.EMAIL_ID, parent.PASSWORD);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				switch (status) {
					case LoginConstants.AUTHENTICATION_SUCCESS:
					try {
						parent.setContentPane(new TablePage(parent));
						parent.setVisible(true);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
						break;
					case LoginConstants.INVALID_EMAIL_ID:
						JOptionPane.showMessageDialog(parent, "Invalid Email Id", "Login status", JOptionPane.ERROR_MESSAGE);
						break;
					case LoginConstants.PASSWORD_INCORRECT:
						JOptionPane.showMessageDialog(parent, "Password Incorrect", "Login status", JOptionPane.ERROR_MESSAGE);
						break;
					default:
						JOptionPane.showMessageDialog(parent, "Server Down", "Try again later", JOptionPane.ERROR_MESSAGE);
						break;
				}
			}
		});
		
		cont.add(passwordLabel);
		cont.add(passwordInput);
		
		JButton login=new MyButton("Login");
		login.setSize(100,30);
		login.setLocation(150, 350);
		login.addActionListener(passwordInput.getActionListeners()[0]);
		login.setBackground(Color.BLUE);
		
		cont.add(login);
		
		color.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(value==colors.length) value=0;
				passwordInput.setBackground(colors[value]);
				emailInput.setBackground(colors[value]);
				emailInput.setBorder(new LineBorder(colors[value], 3));
				passwordInput.setBorder(new LineBorder(colors[value], 3));
				login.setBackground(colors[value++]);
			}
		});
		
		add(color);
		add(cont);
		add(bg);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				bg.setSize(getSize());
				color.setLocation(getWidth()-60,getHeight()-60);
				Center.align(cont);
			}
		});
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
}