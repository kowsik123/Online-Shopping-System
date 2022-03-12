package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import DBMS.OrderTable;
import DBMS.ProductTable;
import DBMS.UserTable;
import Network.CI;
import Network.LoginConstants;

public class ClientObject extends UnicastRemoteObject implements CI{
	UserTable ut=new UserTable();
	ProductTable pt=new ProductTable();
	OrderTable ot=new OrderTable();
	HashMap<String,String> otps=new HashMap<String,String>();
	protected ClientObject() throws RemoteException {
		super();
	}

	@Override
	public int authenticate(String email, String password) throws RemoteException {
		return ut.authenticate(email, password);
	}

	@Override
	public boolean register(String[] arr) throws RemoteException {
		return ut.insert(arr);
	}

	@Override
	public Object[][] getProductTable() throws RemoteException {
		return pt.getAllProducts();
	}

	@Override
	public String[] getProfileDetails(String email, String password) throws RemoteException {
		if(authenticate(email, password)==LoginConstants.AUTHENTICATION_SUCCESS) {
			return ut.getProfile(email);
		}
		else return null;
	}

	@Override
	public Object[][] getOrdersPlaced(String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return null;
		
		return ot.getOrderedProducts(email);
	}

	@Override
	public Object[] getOrderDetails(int orderId, String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return null;
		
		return ot.getOrderDetails(orderId);
	}

	@Override
	public boolean placeOrder(int[] data, String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return false;
		
		data[1]=ut.getUserId(email);
		
		return ot.insert(data);
	}

	@Override
	public boolean updateProfile(String[] data, String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return false;
		
		return ut.updateProfile(data,email);
	}

	@Override
	public boolean setProductRating(int orderId, int rating,String email,String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return false;
		
		return ot.setRating(orderId,rating);
	}

	@Override
	public boolean requestResetPassword(String email) throws RemoteException{
		if(ut.isEmailThere(email)) {
			String otp=Integer.toString((int)(Math.random()*10000));
			int len=4-otp.length();
			String fill="";
			for(int i=0;i<len;i++) fill+="0";
			otp=fill+otp;
			otps.put(email, otp);
			System.out.println(otp);
			String html="<body style=\"margin: 0; height: 520px; background-color: rgb(255, 255, 255);\">\r\n"
					+ "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\r\n"
					+ "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\r\n"
					+ "    <link href=\"https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap\" rel=\"stylesheet\">\r\n"
					+ "    <div id=\"holder\" style=\"margin: 10px;height: 300px;width: calc(100% - 20px);background-color: rgb(255, 255, 255);margin-top: 75px;\">\r\n"
					+ "        <div id=\"box\" style=\"padding-top: 5px; background-color: rgb(238, 238, 255);height: 150px;width: 300px;border-radius: 10px;margin: auto;\">\r\n"
					+ "            <div id=\"title\" style=\"width: 100%;font-size: x-large;font-family: 'Open Sans', sans-serif;text-align: center;margin-top: 20px;color: rgb(128, 20, 77);\">verification code</div>\r\n"
					+ "            <div id=\"otp\" style=\"width: fit-content;margin: auto; margin-top: 14px;\">\r\n"
					+ "                <div style=\"height: 30px;width: 25px;border-radius: 5px;background-color: rgb(221, 221, 221);margin: 5px;text-align: center;font-size: x-large;color: rgb(73, 73, 73);float: left;\">"+otp.charAt(0)+"</div>\r\n"
					+ "                <div style=\"height: 30px;width: 25px;border-radius: 5px;background-color: rgb(221, 221, 221);margin: 5px;text-align: center;font-size: x-large;color: rgb(73, 73, 73);float: left;\">"+otp.charAt(1)+"</div>\r\n"
					+ "                <div style=\"height: 30px;width: 25px;border-radius: 5px;background-color: rgb(221, 221, 221);margin: 5px;text-align: center;font-size: x-large;color: rgb(73, 73, 73);float: left;\">"+otp.charAt(2)+"</div>\r\n"
					+ "                <div style=\"height: 30px;width: 25px;border-radius: 5px;background-color: rgb(221, 221, 221);margin: 5px;text-align: center;font-size: x-large;color: rgb(73, 73, 73);float: left;\">"+otp.charAt(3)+"</div>\r\n"
					+ "            </div>\r\n"
					+ "        </div>\r\n"
					+ "    </div>\r\n"
					+ "</body>";
			MailSender.sendMail(html,email);
			return true;
		}
		else return false;
	}

	@Override
	public boolean changePassword(String email, String OTP,String newPassword) throws RemoteException {
		if(otps.containsKey(email)) {
			if(otps.get(email).equals(OTP)) {
				ut.changePassword(newPassword,email);
				return true;
			}
		}
		return false;
	}
}
