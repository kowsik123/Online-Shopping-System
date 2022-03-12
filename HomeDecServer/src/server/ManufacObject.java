package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import DBMS.ProductTable;
import Network.LoginConstants;
import Network.MI;

public class ManufacObject extends UnicastRemoteObject implements MI{

	String email="manufacturer@homedec.com";
	String password="mhomedec";
	ProductTable pt=new ProductTable();

	protected ManufacObject() throws RemoteException {
		super();
	}

	@Override
	public int authenticate(String name, String password) throws RemoteException {
		if(name.equals(this.email)) {
			if(password.equals(this.password)) return LoginConstants.AUTHENTICATION_SUCCESS;
			else return LoginConstants.PASSWORD_INCORRECT;
		}
		else return LoginConstants.INVALID_EMAIL_ID;
	}

	@Override
	public Object[][] getProducts(String email, String password) throws RemoteException {
		if(authenticate(email, password)==LoginConstants.AUTHENTICATION_SUCCESS) {
			return pt.getAllProducts();
		}
		else return null;
	}

	@Override
	public boolean updateProduct(Object[] data, String email, String password) throws RemoteException {
		if(authenticate(email, password)==LoginConstants.AUTHENTICATION_SUCCESS) {
			return pt.updateProduct(data);
		}
		else return false;
	}

	@Override
	public boolean addProduct(Object[] data, String email, String password) throws RemoteException {
		if(authenticate(email, password)==LoginConstants.AUTHENTICATION_SUCCESS) {
			return pt.addProduct(data);
		}
		else return false;
	}

	@Override
	public boolean removeProduct(int productId, String email, String password) throws RemoteException {
		if(authenticate(email, password)==LoginConstants.AUTHENTICATION_SUCCESS) {
			return pt.removeProduct(productId);
		}
		else return false;
	}
}