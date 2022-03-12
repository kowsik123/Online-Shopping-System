package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import DBMS.OrderTable;
import DBMS.ProductTable;
import Network.DI;
import Network.DeliveryStatusConstants;
import Network.LoginConstants;

public class DeliveryCenterObject extends UnicastRemoteObject implements DI{
	
	String email="deliverycenter@homedec.com";
	String password="dchomedec";
	
	OrderTable ot=new OrderTable();
	ProductTable pt=new ProductTable();
	
	protected DeliveryCenterObject() throws RemoteException {
		super();
	}

	@Override
	public int authenticate(String email, String password) throws RemoteException {
		if(email.equals(this.email)) {
			if(password.equals(this.password)) return LoginConstants.AUTHENTICATION_SUCCESS;
			else return LoginConstants.PASSWORD_INCORRECT;
		}
		else return LoginConstants.INVALID_EMAIL_ID;
	}

	@Override
	public Object[][] getOrderTable(String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return ot.getPlacedOrders();
		
		return ot.getPlacedOrders();
	}

	@Override
	public boolean updateStatus(int orderid, int status, String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return false;
		
		if(status==DeliveryStatusConstants.PRODUCT_DELIVERED) {
			int pid=ot.getProductId(orderid);
			pt.decreaseStock(pid);
		}
		
		return ot.updateStatus(orderid, status);
	}

	@Override
	public boolean setTransactionId(int orderid, int transactionId, String email, String password) throws RemoteException {
		if(authenticate(email, password)!=LoginConstants.AUTHENTICATION_SUCCESS) return false;
		
		return ot.setTransactionId(orderid, transactionId);
	}

}
