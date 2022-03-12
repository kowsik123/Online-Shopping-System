package Network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MI extends Remote{
	int authenticate(String email,String password) throws RemoteException;
	Object[][] getProducts(String email,String password) throws RemoteException;
	boolean updateProduct(Object[] data,String name,String password) throws RemoteException;
	boolean addProduct(Object[] data,String name,String password) throws RemoteException;
	boolean removeProduct(int productId,String name,String password) throws RemoteException;
}