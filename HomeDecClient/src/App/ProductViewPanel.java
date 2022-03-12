package App;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import HelperPack.*;

public class ProductViewPanel extends JPanel{
	static int SCROLL_BAR_WIDTH=10;
	protected static final int TITLE_BAR_HEIGHT=70;
	Application parent=null;
	Object[][] data=null;
	
	public ProductViewPanel(Application j){
		parent=j;
		Font f=new Font(Font.SANS_SERIF, Font.PLAIN , 36);
		
		setLayout(null);
		JPanel title=new JPanel(null);
		title.setSize(500,TITLE_BAR_HEIGHT);
		title.setBackground(new Color(255,233,0));
		title.setLayout(null);
		
		JLabel icon=new JLabel(new ImageIcon(new ImgPack("E:\\img\\homedec.png").getImage(40, 40)));
		icon.setSize(50,50);
		icon.setLocation(10, 10);
		icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		title.add(icon);
		
		JLabel homedec=new JLabel("HomeDec Shopping");
		homedec.setFont(f);
		homedec.setSize(400, 45);
		homedec.setLocation(65,12);
		title.add(homedec);
		
		JLabel cart=new JLabel(new ImageIcon(new ImgPack("E:\\img\\cart.png").getImage(40, 40)));
		cart.setSize(50,50);
		cart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		title.add(cart);
		
		JLabel profile=new JLabel(new ImageIcon(new ImgPack("E:\\img\\pro.png").getImage(40, 40)));
		profile.setSize(50,50);
		profile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		profile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				new Profile(parent);
			}
		});
		title.add(profile);
		
		JScrollBar sc=new JScrollBar(Scrollbar.VERTICAL,0,10,0,50);
		add(sc);
		sc.setUI(new TransScrollBarUI() );
		sc.setBackground(new Color(138,46,226));
		
		JPanel content=new JPanel(null);
		content.setBackground(new Color(138,46,226));
		
		JPanel holder=new JPanel(new ProductLayout(20, 6, 200));
		holder.setBackground(new Color(138,46,226));
		
		try {
			data=parent.ob.getProductTable();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		for(int i=0;i<data.length;i++) {
			JPanel jp=new Product(data[i],parent);
			jp.setBackground(Color.white);
			holder.add(jp,ProductLayout.PRODUCT);
		}
		
		cart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				try {
					data=parent.ob.getOrdersPlaced(parent.EMAIL_ID, parent.PASSWORD);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				holder.removeAll();
				for(int i=0;i<data.length;i++) {
					JPanel jp=new Product(data[i],parent);
					jp.setBackground(Color.white);
					holder.add(jp,ProductLayout.PRODUCT);
				}
			}
		});
		
		icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				try {
					data=parent.ob.getProductTable();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				holder.removeAll();
				for(int i=0;i<data.length;i++) {
					JPanel jp=new Product(data[i],parent);
					jp.setBackground(Color.white);
					holder.add(jp,ProductLayout.PRODUCT);
				}
			}
		});
		
		sc.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				holder.setLocation( 0 , -e.getValue() );
			}
		});
		
		holder.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				sc.setValue( sc.getValue()+e.getUnitsToScroll()*10 );
			}
		});
		
		content.add(holder);
		add(title);
		add(content);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				title.setSize(getWidth(),TITLE_BAR_HEIGHT);
				cart.setLocation(getWidth()-10-50,10);
				profile.setLocation(cart.getX()-10-50, 10);
				content.setLocation(0, TITLE_BAR_HEIGHT );
				content.setSize(getWidth(), getHeight()-TITLE_BAR_HEIGHT );
				holder.setSize(content.getWidth()-SCROLL_BAR_WIDTH,holder.getHeight());
				sc.setSize( SCROLL_BAR_WIDTH , getHeight()-TITLE_BAR_HEIGHT );
				sc.setLocation(getWidth()- SCROLL_BAR_WIDTH ,TITLE_BAR_HEIGHT);
				sc.setValues(0 , 200 , 0, holder.getHeight()-200);
			}
		});
	}
}