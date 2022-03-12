package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import HelperPack.*;
import Network.MI;

public class TablePage extends JPanel {
	static Dimension scsize=Toolkit.getDefaultToolkit().getScreenSize();
	String imagePath=null;
	transient Image img=null;
	Object[][] data;
	Object[][] data1;
	String[] column={"S.NO","Name","Price","Rating","Stock","Image","Action","Remove"};
	JTable jt;
	Application parent;
	JScrollPane sp;
	JScrollBar sb;
	public TablePage(Application jp) throws RemoteException {
		parent=jp;
		setLayout(null);
		Font f1=new Font("Times New Roman", Font.BOLD, 18);
		Font f2=new Font("Times New Roman", Font.PLAIN, 16);
		
		JLabel lp=new JLabel("PRODUCT DETAILS");
		lp.setFont(f1);
		lp.setBounds(((scsize.width)/2)-100, 50, 200, 30);
		
		data= getData() ;
		data1= getData1(data);		
		jt=new JTable(data1,column);    
		jt.setRowHeight(30);
		
		JTextField jtf=new JTextField();
		jtf.setSize(100, 30);
		jtf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtf.getText().equals("")) return;
				try {
					data=getLowstockProduct(Integer.parseInt(jtf.getText()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				data1=getData1(data);
				jt=new JTable(data1,column);
				sp.setViewportView(jt);
				tableRender(jt);
			}
		});
		
		sp=new JScrollPane();
		sp.setViewportView(jt);
		sp.setBounds(100,150,scsize.width-200,500); 
		sb =sp.getVerticalScrollBar();
		sb.setUI(new TransScrollBarUI());
		sb.setSize(10, sb.getHeight());
		
		jtf.setLocation(650, sp.getY()-30);
		
		tableRender(jt);
		add(lp);
		add(sp);
		add(jtf);
		
	}
	
	// s.no prod.name price rating stock image  action    remove 
	//									 view   update     x
	
	protected Object[][] getLowstockProduct(int min) throws RemoteException {
		data=getData();
		Object[][] data2=data;
		Object[][] lowData=null; 
		Arrays.sort(data2,new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o1, Object[] o2) {
				if(((BigDecimal)o1[4]).intValue()>((BigDecimal)o2[4]).intValue()) {
					return 1;
				}
				return -1;
			}
		});
		int count=0;
		for(int i=0;i<data2.length;i++) {
			if(((BigDecimal)data2[i][4]).intValue()>min) break;
			count++;
		}
		lowData=new Object[count][];
		
		for(int i=0;i<count;i++) {
			lowData[i]=data2[i];
		}
		
		return lowData;
		
	}

	public Object[][] getData() throws RemoteException{
		return parent.ob.getProducts(parent.EMAIL_ID, parent.PASSWORD); 
	}
	
	public Object[][] getData1(Object[][] data){
		Object data1[][]= new Object[data.length+1][8];
		for(int i=0;i<data1.length-1;i++) {
			data1[i][0]=i+1;
			data1[i][1]=data[i][1];
			data1[i][2]=data[i][2];
			data1[i][3]=data[i][3];
			data1[i][4]=data[i][4];
			data1[i][5]="view";
			data1[i][6]="save";
			data1[i][7]="X";
		}
		data1[data.length][0]=data.length+1;
		data1[data.length][1]=null;
		data1[data.length][2]=null;
		data1[data.length][3]=null;
		data1[data.length][4]=null;
		data1[data.length][5]="add";
		data1[data.length][6]="add";
		data1[data.length][7]="";
		
		return data1;
	}
	
	public void tableRender(JTable t) {
		jt.setRowHeight(30);
		jt.getColumn("Image").setCellRenderer(new TableButton());
		jt.getColumn("Image").setCellEditor(new ButtonEditor((event)->{
			int i=((ButtonEditor)event.getSource()).i;
			int j=((ButtonEditor)event.getSource()).j;
			if(i==data.length) {
				if(imagePath!=null) {
					JFrame f=new JFrame();
//					f.setLayout(null);
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					Container fcon=f.getContentPane();
					fcon.setLayout(null);
					JLabel jl=new JLabel(new ImageIcon( img ));
					jl.setSize(fcon.getSize());
					fcon.add(jl);
					JButton jb=new MyButton("Change");
					jb.setSize(150,30);
					
					
					jb.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							FileDialog f=new FileDialog(parent, "Choose an Image");
							f.setVisible(true);
							String imgPath=f.getDirectory()+f.getFile();
							
							if(imgPath!=null) {
								imagePath=imgPath;
								img=(new ImgPack(imgPath).getImage(300,300));
								f.setVisible(false);
								System.out.println("Image Changed");
							}
							
						}
					});
					fcon.add(jb);
					f.setSize(500, 550);
					f.setVisible(true);	
					jl.setSize(fcon.getSize());
					jb.setLocation(Center.getCenter(f.getWidth(),jb.getWidth()),f.getHeight()-100);
					f.setResizable(false);
				}else {
					FileDialog f=new FileDialog(parent, "Choose an Image");
					f.setVisible(true);
					imagePath=f.getDirectory()+f.getFile();
					if(imagePath!=null) {
						jt.getCellEditor(i, j).getTableCellEditorComponent(jt, "view", false, i, j);
						ImgPack ii=(new ImgPack(65,65));
						ii.LoadImage(imagePath);
						img=ii.getImage(300,300);
					}
				}
				
			}
			else {
				JFrame f=new JFrame();
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				Container fcon=f.getContentPane();
				fcon.setLayout(null);
				
				JLabel jl=new JLabel(new ImageIcon( getImage(data[i][5]) ));
				jl.setSize(fcon.getSize());
				fcon.add(jl);
				JButton jb=new MyButton("Change");
				jb.setSize(150,30);
				
				
				jb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						FileDialog f=new FileDialog(parent, "Choose an Image");
						f.setVisible(true);
						String imagePat=f.getDirectory()+f.getFile();
						if(imagePat!=null) {
							BufferedImage bimage;
							try {
								bimage = ImageIO.read(new File(imagePat));
								ByteArrayOutputStream baos=new ByteArrayOutputStream();
								ImageIO.write(bimage, "png", baos );
								byte[] imageInByte=baos.toByteArray();
								data[i][5]=(Object) imageInByte; 
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							f.setVisible(false);
							System.out.println("Image Changed");
						}
					}
				});
				f.setResizable(false);
				fcon.add(jb);
				f.setSize(500, 550);
				f.setVisible(true);	
				jl.setSize(fcon.getSize());
				jb.setLocation(Center.getCenter(f.getWidth(),jb.getWidth()),f.getHeight()-80);
			}
			
		}));
		
		jt.getColumn("Action").setCellRenderer(new TableButton());
		jt.getColumn("Action").setCellEditor(new ButtonEditor((event)->{
			int i=((ButtonEditor)event.getSource()).i;
			int j=((ButtonEditor)event.getSource()).j;
			Object name =jt.getValueAt(i, 1);
			Object price = Float.parseFloat(jt.getValueAt(i, 2).toString());
			Object stock = Integer.parseInt(jt.getValueAt(i, 4).toString());
			
			System.out.println(name+" "+price+" "+stock);
			if(data.length==i) {
				
				try {
					BufferedImage bimage=ImageIO.read(new File(imagePath));
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					ImageIO.write(bimage, "png", baos );
					byte[] imageInByte=baos.toByteArray();
					parent.ob.addProduct(new Object[]{name,price,stock,imageInByte}, parent.EMAIL_ID, parent.PASSWORD);
					data=getData();
					System.out.println(data[0].length);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(parent, "Server Down", "Try again Later", JOptionPane.ERROR_MESSAGE);
				}
				img=null;
				imagePath=null;
				data1=getData1(data);
				jt=new JTable(data1,column);
				sp.setViewportView(jt);
				tableRender(jt);
			}
			else {
				try {
					parent.ob.updateProduct(new Object[]{name,price,stock,data[i][5]}, parent.EMAIL_ID, parent.PASSWORD);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}));
		
		jt.getColumn("Remove").setCellRenderer(new TableButton());
		jt.getColumn("Remove").setCellEditor(new ButtonEditor((event)->{
			int i=((ButtonEditor)event.getSource()).i;
			int j=((ButtonEditor)event.getSource()).j;
			try {
				parent.ob.removeProduct(((BigDecimal)data[i][0]).intValue(), parent.EMAIL_ID, parent.PASSWORD);
				data=getData();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			img=null;
			imagePath=null;
			data1=getData1(data);
			jt=new JTable(data1,column);
			sp.setViewportView(jt);
			tableRender(jt);
		}));
	}

	public Image getImage(Object object) {
	    byte [] data =(byte[]) object ;
	    ByteArrayInputStream bis = new ByteArrayInputStream(data);
	    BufferedImage bImage2;
		try {
			bImage2 = ImageIO.read(bis);
			return bImage2.getScaledInstance(400 , 400, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return null;
	}
}