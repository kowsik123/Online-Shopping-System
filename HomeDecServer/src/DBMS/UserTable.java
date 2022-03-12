package DBMS;

import java.awt.Image;
import java.io.InputStream;
import java.security.spec.*;
import java.sql.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.imageio.ImageIO;

import Network.CI;
import Network.LoginConstants;

public class UserTable extends dbms{
	public UserTable() {
		super("skcet","java");
	}
	
	public void createTable() {// col=6;
		query("create table users(userid integer primary key,name varchar2(50),email varchar2(50),password varchar2(50),mobilenumber char(10),address varchar2(200))");
		query("create sequence user_id start with 1 increment by 1");
	}
	
	public int authenticate(String email,String password) {
		ResultSet rs=queryWithResult("select password from users where email='"+email+"'");
		try {
			if(rs.next()) {
				if(hashp( password).equals(rs.getString(1))) {
					return LoginConstants.AUTHENTICATION_SUCCESS;
				}				else {
					return LoginConstants.PASSWORD_INCORRECT;
				}
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		return LoginConstants.INVALID_EMAIL_ID;
	}
	
	public boolean insert(String[] arr) {// arr={name,email,password,mobile_number,address}
		if(isEmailThere(arr[1])) return false;
		try {
			PreparedStatement st= conn.prepareStatement("insert into users values(user_id.nextval,?,?,?,?,?)");
			st.setString(1,arr[0]);       			  //name
			st.setString(2,arr[1]);       			  //email
			st.setString(3,hashp(arr[2]));			  //password
			st.setString(4,arr[3]);       			  //mobile number
			st.setString(5,arr[4]);       			  //address
			st.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public String hashp(String s) {
		byte[] salt= {95, 80, 32, 93, 22, -33, 45, -38, -73, 98, 60, -91, -117, -11, 52, 29};
		KeySpec spec = new PBEKeySpec(s.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash=null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(hash);
	}

	public String[] getProfile(String email) {//return {userid,name,email,mobile_number,address}
		Object[] arr=getResult("select userid,name,email,mobilenumber,address from users where email='"+email+"'", 5 )[0];
		String[] str=new String[arr.length];
		for(int i=0;i<arr.length;i++) str[i]=arr[i].toString();
		return str;
	}

	public boolean updateProfile(String[] data, String email) {//arr={name,email,mobile_number,address}
//		if(isEmailThere(data[1])) return false;
		try {
			PreparedStatement ps= getPS("update users set name=?,email=?,mobilenumber=?,address=? where userid=?");
			ps.setString(1, data[0]);   //1.name
			ps.setString(2, data[1]);   //2.new email id
			ps.setString(3, data[2]);   //3.mobile number
			ps.setString(4, data[3]);   //4.address
			ps.setInt(5, getUserId(email));     //5.current email
			ps.execute();
			System.out.println();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean isEmailThere(String email) {
		boolean isthere=false;
		try {
			isthere=queryWithResult("select email from users where email='"+email+"'").next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isthere;
	}
	public boolean changePassword(String newPassword,String email) {
		query("update users set password='"+hashp( newPassword)+"' where email='"+email+"'");
		return false;
	}
	public int getUserId(String email) {
		return Integer.parseInt(getResult("select userid from users where email='"+email+"'",1)[0][0].toString());
	}
//	public static void main(String[] args) {
//		new UserTable().createTable();
//	}
}