package DBMS;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class ProductTable extends dbms{
	public ProductTable() {
		super("skcet","java");
	}
	public void createTable() {// col=6;
		query("create table products(productid integer primary key,name varchar2(100),price float,rating float,stock integer,image blob)");
		query("create sequence product_id start with 1 increment by 1");
	}
	public Object[][] getAllProducts(){// returns {product_id, name,price,rating,stock,image}
		Object[][] data= getResult("select productid, name, price, rating, stock, image from products",6);
		for(Object[] i:data) {
			Blob b=(Blob)i[5];
			try {
				i[5]= b.getBytes(1, (int)b.length());
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		
		return data;
	}
	public boolean updateProduct(Object[] data){  // data = {product_id , name, price, stock, image}
		 try {
			PreparedStatement ps= getPS("update products set name=?,price=?,stock=?,image=? where productid=?");
			ps.setString(1, (String)data[1]); //1.name
			ps.setFloat (2, (float)data[2]);  //2.price
			ps.setInt(3, (int)data[3]);       //3.stock
			ps.setBlob(4, new ByteArrayInputStream((byte[])data[3]) ); //4.image
			ps.setInt(5, (int)data[0]);       //0.product id
			ps.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean addProduct(Object[] data) {// data={name,price,stock,image}
		PreparedStatement ps=getPS("insert into products values(product_id.nextval,?,?,5.0,?,?)");
		try {
			ps.setString(1, (String)data[0]);
			ps.setFloat(2, (float)data[1]);
			ps.setInt(3,(int)data[2]);
			ps.setBlob(4, new SerialBlob((byte[])data[3]).getBinaryStream());
			ps.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean removeProduct(int productId) {
		return query("delete from products where productid="+productId);
	}
	public boolean addRating(int productId, int rating) {
		int rate=(int)(((BigDecimal)getResult("select rating from products where productid="+productId,1)[0][0]).doubleValue()*10);
		return query("update products set rating="+( rating+rate )/20.0+" where productid='"+productId+"'");
	}
	public void decreaseStock(int pid) {
		int currstock=Integer.parseInt(getValue("select stock from products where productid="+pid).toString());
		query("update products set stock="+(currstock-1)+" where productid="+pid);
	}
}