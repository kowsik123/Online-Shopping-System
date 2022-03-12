package App;

import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import HelperPack.*;

public class Product extends JPanel {
	public static final int NAME_HEIGHT=40; 
	public Product(Object[] arr) {
		ImgPack ip=new ImgPack(arr[5]);
		JLabel jimg=new JLabel();
		
		JPanel jdet=new JPanel();
		JLabel jname=new JLabel(arr[1].toString());
		JButton jprice=new MyButton(arr[2].toString());
		jprice.setSize(60, 16);
		jdet.add(jname);
		jdet.add(jprice);
		
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				jimg.setSize(getWidth(), 200-NAME_HEIGHT);
				jdet.setSize(getWidth(),NAME_HEIGHT);
				jname.setSize(getWidth(),NAME_HEIGHT/2);
				jprice.setLocation(Center.getCenter(getWidth(),jprice.getWidth()),NAME_HEIGHT/2+2);
				super.componentResized(e);
			}
		});
	}
	
	
	public static void main(String[] args) {
		
	}

}
