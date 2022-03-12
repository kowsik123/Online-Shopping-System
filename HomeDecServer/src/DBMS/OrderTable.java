package DBMS;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicButtonUI;

import Network.DeliveryStatusConstants;
import Network.PaymentConstants;

public class OrderTable extends dbms{
	UserTable ut=new UserTable();
	public OrderTable() {
		super("skcet","java");
	}
	public void createTable() { //col=9
		query("create table orders("
				+ "orderid integer primary key,"
				+ "pid integer,"
				+ "useid integer,"
				+ "quantity integer,"
				+ "orderdate date,"
				+ "status integer,"
				+ "paymenttype integer,"
				+ "transactionid integer,"
				+ "israted char(1)"
				+ ")");
		query("create sequence order_id start with 1 increment by 1");
	}
	public boolean insert(int[] data) { //data= {pid,userid,quantity,payment_type,transactionid}
		PreparedStatement ps=getPS("insert into orders values(order_id.nextval,?,?,?,?,?,?,?,'F')");
		try {
			ps.setInt(1, data[0]);            					//product id
			ps.setInt(2, data[1]);           				 	//user id
			ps.setInt(3, data[2]);            					//quantity
			ps.setDate(4,Date.valueOf(LocalDate.now()));        //date
			ps.setInt(5, DeliveryStatusConstants.ORDER_PLACED); //status
			ps.setInt(6, data[3]);            					//payment type
			if(data[3]==PaymentConstants.CASH_ON_DELIVERY) ps.setInt(7, 0);
			else ps.setInt(7, data[4]);							//transaction id
			new ProductTable().decreaseStock(data[0]);
			ps.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean setRating(int orderId, int rating) {
		return query("update orders set israted='T' where orderid="+orderId) 
				&& new ProductTable().addRating(getProductId(orderId), rating);
	}
	public int getProductId(int orderId) {
		return Integer.parseInt(getResult("select pid from orders where orderid="+orderId,1)[0][0].toString());
	}
	public int getUserId(int orderId) {
		return Integer.parseInt(getResult("select useid from orders where orderid="+orderId,1)[0][0].toString());
	}
	public Object[] getOrderDetails(int orderId) { // returns { orderid,quantity,date,status,israted }
		return getResult("select orderid,quantity,orderdate,status,israted from orders where orderid="+orderId,5)[0];
	}
	public Object[][] getOrderedProducts(String email) { 
		//          product_id, name,price,rating,stock,image
		// returns {product_id, name,price,rating,stock,image,order id}
		int userId=ut.getUserId(email);
		Object[] pds=getCol("select pid from orders where useid="+userId);
		Object[] os=getCol("select orderid from orders where useid="+userId);
		Object[][] res=new Object[pds.length][5];
		for(int i=0;i<pds.length;i++) {
			res[i]=getResult("select productid,name,price,rating,stock,image,productid from products where productid="+(((BigDecimal)pds[i]).intValue()),7)[0];
			Blob b=(Blob)res[i][5];
			try {
				res[i][5]= b.getBytes(1, (int)b.length());
			} catch (SQLException e) {
				e.printStackTrace();
			};
			res[i][6]=os[i];
		}
		return res;
	}
	public Object[][] getPlacedOrders() { // {order id, quantity,payment_type,product name, price, user name, address,mobilenumber}
		//order id, p name, amount, username, address, mobile number, status
		Object[][] op=getResult("select orderid,quantity,paymenttype,pid,useid,status from orders where status>"+DeliveryStatusConstants.PRODUCT_CANCELED,6);
		Object[][] res=new Object[op.length][8];
		for(int i=0;i<res.length;i++) {
			Object[] arr=getResult("select name,price from products where productid="+(((BigDecimal)op[i][3]).intValue()),2)[0];
			res[i][0]=op[i][0];//order id
			res[i][1]=arr[0];//p name
			res[i][2]=Float.parseFloat( arr[1].toString()) * Integer.parseInt(op[i][1].toString());//amount
			arr=getResult("select name,address,mobilenumber from users where userid="+(((BigDecimal)op[i][4]).intValue()),3)[0];
			res[i][3]=arr[0];//usename
			res[i][4]=arr[1];//address
			res[i][5]=arr[2];//mo no
			res[i][6]=op[i][2];//payment
			res[i][7]=op[i][5];//status
		}
		return res;
	}
	public boolean updateStatus(int orderid, int status) {
		return query("update orders set status="+status+" where orderid="+orderid);
	}
	public boolean setTransactionId(int orderId, int transactionId) {
		return query("update orders set transactionId="+transactionId+" where orderid="+orderId);
	}
//	public static void main(String[] args) {
//		new OrderTable().createTable();
//	}
}