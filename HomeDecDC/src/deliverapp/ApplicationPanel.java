package deliverapp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.*;

import Network.DI;

public class ApplicationPanel extends MouseAdapter implements ActionListener{

	JPanel panel_app;
	JTable tab_ordertable;
	JButton but_reset;
	JScrollPane jc_pane;
	String[] str_colname={"Orderid","Product Name","Amount","User Name","Address","Mobile No.","Payment Type","Status"};
	Login parent=null;
	public ApplicationPanel(Login p) throws MalformedURLException, RemoteException, NotBoundException {
		parent=p;
		panel_app=new JPanel();
		Object[][] str_ordertab=parent.objdi.getOrderTable(parent.EMAIL, parent.PASSWORD);
		System.out.println(str_ordertab.length);
	
		tab_ordertable=new JTable(str_ordertab, str_colname);
		
		tab_ordertable.setBounds(80, 80, 700, 500);
		tab_ordertable.addMouseListener(this);
		
		but_reset=new JButton(new ImageIcon(new ImgPack("E:\\img\\refresh.jpg").getImage(120, 50)));
		but_reset.addActionListener(this);
		but_reset.setBounds(10, 10, 115, 45);

		jc_pane=new JScrollPane(tab_ordertable);
		jc_pane.setBounds(80, 80, 700, 400);
		JScrollBar sc=jc_pane.getVerticalScrollBar();
		sc.setUI(new TransScrollBarUI());

		jc_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  

		panel_app.add(jc_pane);
        panel_app.add(but_reset);

		panel_app.setSize(800, 600);
		panel_app.setLayout(null);
		panel_app.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if(e.getSource()==tab_ordertable) {
				int t=tab_ordertable.getSelectedRow();
				new StatutsUpdater(t+1,parent);
			}
		}catch (Exception er) {er.printStackTrace();}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object[][] obj_ordertab=parent.objdi.getOrderTable(parent.EMAIL,parent.PASSWORD);
			tab_ordertable=new JTable(obj_ordertab,str_colname);
			jc_pane.setViewportView(tab_ordertable);
			tab_ordertable.addMouseListener(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}
}