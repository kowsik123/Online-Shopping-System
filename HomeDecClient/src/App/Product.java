package App;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.*;

import HelperPack.*;

public class Product extends JPanel {
	public static final int NAME_HEIGHT=40; 
	Application parent=null;
	ImgPack ip=null;
	public JButton jprice;
	
	
	public Product(Object[] arr,Application j) {
		parent=j;
		setLayout(null);
		
		if(arr[5] instanceof String) {
			ip=new ImgPack(arr[5].toString());
		}
		else ip=new ImgPack(arr[5]);
		
		JLabel jimg=new JLabel();
		
		JPanel jcon=new JPanel(null);
		jcon.setBackground(new Color(255,233,0));
		JLabel jname=new JLabel(arr[1].toString());
		jname.setHorizontalAlignment(JLabel.CENTER);
		jname.setVerticalAlignment(JLabel.CENTER);
		jprice=new MyButton("₹ "+arr[2]);
		jprice.setBackground(Color.green.darker());
		jprice.setSize(60, 16);
		jcon.add(jname);
		jcon.add(jprice);
		jprice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arr.length==7) {
					new OrderPanel(parent,arr);
				}
				else new ConfirmOrder(arr,parent);
			}
		});
		
		JPanel jr=new JPanel(null);
		JLabel jr1=new JLabel(arr[3]+"★");
		jr1.setLocation(0, 0);
		jr1.setHorizontalAlignment(JLabel.CENTER);
		jr1.setVerticalAlignment(JLabel.CENTER);
		jr1.setSize(30,17);
		jr.setSize(30,17);
		jr.setBackground(Color.GREEN.darker());
		jr1.setForeground(Color.WHITE);
		jr.add(jr1);
		
		add(jr);
		add(jimg);
		add(jcon);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				jimg.setSize(getWidth(), 200-NAME_HEIGHT);
				jimg.setIcon(new ImageIcon(ip.getImage(getWidth(), jimg.getHeight())));
				jcon.setSize(getWidth(),NAME_HEIGHT);
				jcon.setLocation(0, jimg.getHeight());
				jname.setSize(getWidth(),NAME_HEIGHT/2);
				jprice.setLocation(Center.getCenter(getWidth(),jprice.getWidth()),NAME_HEIGHT/2+2);
				jr.setLocation( getWidth() - 5 - 30 , 160-5-17 );
				super.componentResized(e);
			}
		});
	}
	
//	public static void main(String[] args) {
//		JFrame jf=new JFrame();
//		jf.setSize(500, 500);
//		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
//		Container co=jf.getContentPane();
//		co.setLayout(null);
//		
//		JPanel jp=new Product(new Object[] {10,"HomeDecor",200,5.0,20,"E:\\eclipse\\ChitChat\\src\\img\\back2.png"});
//		jp.setSize(160,200);
//		jp.setBackground(Color.WHITE);
//		jp.setLocation(20, 20);
//		co.add(jp);
//		co.addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				jp.setSize(co.getWidth()/3,200);
//				super.componentResized(e);
//			}
//		} );
//		jf.setVisible(true);
//	}
}