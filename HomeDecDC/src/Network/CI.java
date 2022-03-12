package Network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CI extends Remote{
	int authenticate(String email,String password) throws RemoteException;
	boolean register(String[] arr) throws RemoteException;
	Object[][] getProductTable() throws RemoteException;
	String[] getProfileDetails(String email,String password) throws RemoteException;
	Object[][] getOrdersPlaced(String email,String password) throws RemoteException;
	Object[] getOrderDetails(int orderId, String email,String password) throws RemoteException;
	boolean placeOrder(int data[], String email, String password) throws RemoteException;
	boolean updateProfile(String[] data,String email,String password) throws RemoteException;
	boolean setProductRating(int orderId,int rating,String email,String password) throws RemoteException;
	boolean requestResetPassword(String email) throws RemoteException;
	boolean changePassword(String email,String OTP,String newPassword) throws RemoteException;
}