package deliverapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import Network.DI;
import Network.DeliveryStatusConstants;

public class StatutsUpdater {

	JFrame fr_status;
	JLabel lb_ordid;
	JButton but_update;
	JButton but_settransact;
	JComboBox jcombox_status;
	JProgressBar jpbar_status;
	public StatutsUpdater(int row_orderid,Login parent) {
		fr_status=new JFrame("Update Status");
		String str_orderid=String.valueOf(row_orderid);
		lb_ordid=new JLabel("Order id : "+str_orderid);
		lb_ordid.setBounds(50,50, 120, 30);
		String str_statusarr[]= {"Shipped","Received","Delivered","Cancelled"};
		jcombox_status=new JComboBox(str_statusarr);
		jcombox_status.setBounds(80,80,120,30);

		but_update=new JButton("update");
		but_update.setBounds(140, 150,120,30);
		but_update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fr_status.dispose();				
				try {
					String s=(String) jcombox_status.getSelectedItem();
					int selected_status=0;
					switch(s) {
						case "Shipped":selected_status=DeliveryStatusConstants.PRODUCT_RECIEVED_FROM_MANUFACTURER;
						    break;
						case "Received": selected_status=DeliveryStatusConstants.PRODUCT_REACHED_DELIVERY_CENTER;
						    break;
						case "Cancelled":selected_status=DeliveryStatusConstants.PRODUCT_CANCELED;
							break;
						case "Delivered":selected_status=DeliveryStatusConstants.PRODUCT_DELIVERED;
							break;
					}
					parent.objdi.updateStatus(row_orderid, selected_status,parent.EMAIL,parent.PASSWORD);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				new Progress(row_orderid,parent);
			}
		});

		but_settransact=new JButton("Set Transaction ID");
		but_settransact.setBounds(220, 10, 150, 30);
		but_settransact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fr_status.dispose();
				try {
					new Settrans(row_orderid,parent);
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		});

		fr_status.add(jcombox_status);
		fr_status.add(lb_ordid);
		fr_status.add(but_update);
		fr_status.add(but_settransact);
		fr_status.setLayout(null);
		fr_status.setBounds(250,200,400,300);
		fr_status.setVisible(true);
	}
}
