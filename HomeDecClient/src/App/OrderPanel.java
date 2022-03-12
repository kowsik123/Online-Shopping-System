package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import HelperPack.*;
import Network.DeliveryStatusConstants;

public class OrderPanel extends JFrame{
	Application parent;
	private Object[] data={1,2,"1/1/2002",DeliveryStatusConstants.PRODUCT_DELIVERED,"T"};
	
	public OrderPanel(Application j,Object[] arr) {
		Font f=new Font(Font.SANS_SERIF, Font.PLAIN , 17);
		parent = j;
		
		try {//{ orderid,quantity,date,status,israted }
			System.out.println(arr[6]);
			data=parent.ob.getOrderDetails(Integer.parseInt(arr[6].toString()),parent.EMAIL_ID , parent.PASSWORD);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		setTitle("Order Details");
		setSize(650, 400);
		setLocation(Center.getCenter(getToolkit().getScreenSize(),getSize()));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container co=getContentPane();
		co.setLayout(null);
		co.setBackground(new Color(138,46,226));
		Product pro=new Product(arr,null);
		pro.setSize(170,200);
		pro.setLocation(10,10);
		pro.jprice.removeActionListener(pro.jprice.getActionListeners()[0]);
		pro.jprice.removeMouseListener(pro.jprice.getMouseListeners()[1]);
		pro.jprice.setBackground(Color.GRAY);
		co.add(pro);
		
		JLabel a=new JLabel("Amount:");
		a.setForeground(new Color(255,233,0));
		a.setFont(f);
		a.setSize(100,20);
		a.setLocation(200,20);
		JLabel av=new JLabel("₹"+(Integer.parseInt(data[1].toString())*Float.parseFloat( arr[2].toString())  )+"/-");
		av.setFont(f);
		av.setSize(100,20);
		av.setLocation(300, 20);
		av.setForeground(new Color(255,233,0));
		co.add(a);
		co.add(av);
		
		JLabel q=new JLabel("Quantity:");
		q.setSize(100,20);
		q.setLocation(200, 60);
		q.setFont(f);
		q.setForeground(new Color(255,233,0));
		JLabel qv=new JLabel(data[1].toString());
		qv.setForeground(new Color(255,233,0));
		qv.setSize(100,20);
		qv.setLocation(300, 60);
		qv.setFont(f);
		co.add(q);
		co.add(qv);
		
		JLabel r=new JLabel("Rating:");
		r.setForeground(new Color(255,233,0));
		r.setSize(100,20);
		r.setLocation(200, 100);
		r.setFont(f);
		
		JLabel rv=new JLabel();
		rv.setSize(100,20);
		rv.setLocation(300, 100);
		rv.setFont(f);
		rv.setForeground(new Color(255,233,0));
		
		JProgressBar pb=new JProgressBar(JProgressBar.HORIZONTAL,0,50);
		pb.setSize(300,25);
		pb.setLocation(200, 140);
		pb.setValue(50);
		pb.setForeground(new Color(255,233,0));
		pb.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				pb.setValue(e.getX()/6);
				rv.setText(((pb.getValue()/10.0)+"").substring(0,3));
			}
		});
		JButton rb=new MyButton("Rate product");
		rb.setForeground(new Color(138,46,226));
		rb.setSize(100,25);
		rb.setLocation(510,140);
		rb.setBackground(new Color(255,233,0));
		rb.addActionListener((event)->{
			try {
				parent.ob.setProductRating(Integer.parseInt(arr[6].toString()),(int)(Float.parseFloat(rv.getText())*10), parent.EMAIL_ID, parent.PASSWORD);
			} catch (Exception e1) {e1.printStackTrace();}
			dispose();
			new OrderPanel(j, arr);
		});
		
		JLabel d=new JLabel("Date:");
		d.setSize(100,20);
		d.setForeground(new Color(255,233,0));
		d.setLocation(200, 190);
		d.setFont(f);
		
		JLabel dv=new JLabel(data[2].toString().substring(0,10));
		dv.setSize(100,20);
		dv.setForeground(new Color(255,233,0));
		dv.setLocation(250, 190);
		dv.setFont(f);
		
		co.add(d);
		co.add(dv);
		
		setStatus(Integer.parseInt( data[3].toString()));
		
		co.add(rb);
		co.add(r);
		co.add(rv);
		co.add(pb);
		
		if(data[4].toString().equals("T")) {
			rb.removeActionListener(rb.getActionListeners()[0]);
			rb.removeMouseListener(rb.getMouseListeners()[1]);
			rb.setBackground(Color.GRAY);
			pb.removeMouseMotionListener(pb.getMouseMotionListeners()[0]);
			pb.setValue((int) (Float.parseFloat(arr[3].toString())*10));
			pb.setForeground(Color.GRAY);
			rv.setText(arr[3].toString());
		}
		
		setVisible(true);
	}
	
	private void setStatus(int status) {
		Container co=getContentPane();
		JLabel s1=new JLabel(new ImageIcon(new ImgPack("E:\\img\\gb.png").getImage(7, 7)));
		s1.setSize(20, 20);
		s1.setLocation(10, 240);
		
		JLabel sl1=new JLabel("Order Placed");
		sl1.setForeground(new Color(255,233,0));
		sl1.setSize(200, 20);
		sl1.setLocation(40, 240);
		co.add(s1);
		co.add(sl1);
		if(status==4) return;
		
		JLabel s2=new JLabel(new ImageIcon(new ImgPack("E:\\img\\gb.png").getImage(7, 7)));
		s2.setSize(20, 20);
		s2.setLocation(10, 270);
		
		JLabel sl2=new JLabel("Shipped");
		sl2.setSize(200, 20);
		sl2.setLocation(40, 270);
		sl2.setForeground(new Color(255,233,0));
		co.add(s2);
		co.add(sl2);
		if(status==3) return;
		
		JLabel s3=new JLabel(new ImageIcon(new ImgPack("E:\\img\\gb.png").getImage(7, 7)));
		s3.setSize(20, 20);
		s3.setLocation(10, 300);
		
		JLabel sl3=new JLabel("Reached Delivery Center");
		sl3.setSize(200, 20);
		sl3.setLocation(40, 300);
		sl3.setForeground(new Color(255,233,0));
		co.add(s3);
		co.add(sl3);
		if(status==2) return;
		
		JLabel s4=new JLabel(new ImageIcon(new ImgPack("E:\\img\\"+((status==0)?"gb.png":"rb1.png")).getImage(7, 7)));
		s4.setSize(20, 20);
		s4.setLocation(10, 330);
		
		JLabel sl4=new JLabel( (status==0)?"Delivered":"Cancelled");
		sl4.setSize(200, 20);
		sl4.setLocation(40, 330);
		sl4.setForeground(new Color(255,233,0));
		co.add(s4);
		co.add(sl4);
	}

//	public static void main(String[] args) {
//		new OrderPanel(null,new Object[] {10,"HomeDecor",200,5.0,20,"E:\\eclipse\\ChitChat\\src\\img\\back2.png"});
//	}
}