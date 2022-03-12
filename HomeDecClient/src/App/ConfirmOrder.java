package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;

import HelperPack.Center;
import HelperPack.MyButton;
import Network.PaymentConstants;

public class ConfirmOrder extends JFrame{
	
	Application parent=null;
	
	public ConfirmOrder(Object[] arr,Application j) {
		parent=j;
		
		setTitle("Order Confirmation");
		setSize(500, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(Center.getCenter(getToolkit().getScreenSize(),getSize()));
		Container co=getContentPane();
		co.setLayout(null);
		co.setBackground(new Color(138,46,226));
		Product pro=new Product(arr, null);
		pro.setSize(170,200);
		pro.jprice.removeActionListener(pro.jprice.getActionListeners()[0]);
		pro.jprice.removeMouseListener(pro.jprice.getMouseListeners()[1]);
		pro.jprice.setBackground(Color.GRAY);
		
		JLabel q=new JLabel("Quantity");
		q.setSize(170,20);
		q.setHorizontalAlignment(JLabel.CENTER);
		q.setVerticalAlignment(JLabel.CENTER);
		q.setFont(new Font(Font.SANS_SERIF, Font.PLAIN , 16));
		
		JTextField qt=new JTextField("1");
		qt.setSize(60,20);
		
		JLabel pm=new JLabel("Payment Method");
		pm.setSize(170,20);
		pm.setHorizontalAlignment(JLabel.CENTER);
		pm.setVerticalAlignment(JLabel.CENTER);
		pm.setFont(new Font(Font.SANS_SERIF, Font.PLAIN , 16));
		
		ButtonGroup bg=new ButtonGroup();
		JRadioButton p1=new JRadioButton("Credit card");
		p1.setSize(150,20);
		p1.setLocation(10, 10);
		JRadioButton p2=new JRadioButton("Debit card");
		p2.setSize(150,20);
		p2.setLocation(10, 40);
		JRadioButton p3=new JRadioButton("NetBanking");
		p3.setSize(150,20);
		p3.setLocation(10, 70);
		JRadioButton p4=new JRadioButton("UPI");
		p4.setSize(150,20);
		p4.setLocation(10, 100);
		JRadioButton p5=new JRadioButton("Cash On Delivery");
		p5.setSize(150,20);
		p5.setLocation(10, 130);
		
		bg.add(p1);
		bg.add(p2);
		bg.add(p3);
		bg.add(p4);
		bg.add(p5);
		
		JPanel pp=new JPanel(null);
		pp.setSize(170,160);
		pp.setBackground(new Color(0,0,0,100));
		pp.add(p1);
		pp.add(p2);
		pp.add(p3);
		pp.add(p4);
		pp.add(p5);
		
		JButton jb=new MyButton("Pay");
		jb.setSize(70, 25);
		
		jb.addActionListener((event)->{
			try {//data= {pid,userid,quantity,payment_type,transactionid}
				int paymentType = 0;
				int transid =((int)Math.random()*1000);
				if(p1.isSelected()) paymentType=PaymentConstants.CREDIT_CARD;
				else if(p2.isSelected()) paymentType=PaymentConstants.DEBIT_CARD;
				else if(p3.isSelected()) paymentType=PaymentConstants.NETBANKING;
				else if(p4.isSelected()) paymentType=PaymentConstants.UPI;
				else {
					paymentType=PaymentConstants.CASH_ON_DELIVERY;
					transid = 0;
				}
				j.ob.placeOrder(new int[] {Integer.parseInt( arr[0].toString() ) ,0,Integer.parseInt(qt.getText()),paymentType,transid }, parent.EMAIL_ID, parent.PASSWORD);
				dispose();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
		
		co.add(q);
		co.add(qt);
		co.add(pro);
		co.add(pm);
		co.add(pp);
		co.add(jb);
		
		co.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				pro.setLocation(Center.getCenter( co.getWidth() , pro.getWidth() ),30);
				q.setLocation(Center.getCenter( co.getWidth() , q.getWidth() ), 250);
				qt.setLocation(Center.getCenter( co.getWidth() , qt.getWidth() ), 275);
				pm.setLocation(Center.getCenter( co.getWidth() , pm.getWidth() ), 300);
				pp.setLocation(Center.getCenter( co.getWidth() , pp.getWidth() ), 330);
				jb.setLocation(Center.getCenter( co.getWidth() , jb.getWidth() ), 340+pp.getHeight());
				super.componentResized(e);
			}
		});
		setVisible(true);
	}
	
//	public static void main(String[] args) {
//		new ConfirmOrder(new Object[] {10,"HomeDecor",200,5.0,20,"E:\\eclipse\\ChitChat\\src\\img\\back2.png"});
//	}
}
