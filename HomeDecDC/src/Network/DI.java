package Network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DI extends Remote{
	int authenticate(String email,String password) throws RemoteException;
	Object[][] getOrderTable(String email,String password) throws RemoteException;
	boolean updateStatus( int orderid, int status ,String email,String password) throws RemoteException;
	boolean setTransactionId( int orderid, int transactionId ,String email,String password) throws RemoteException;
}