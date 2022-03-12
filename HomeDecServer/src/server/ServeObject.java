package server;

import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServeObject {
	public static void main(String[] args) {
		try {
			Remote mob = new ManufacObject();
			Remote cob = new ClientObject();
			Remote dob = new DeliveryCenterObject();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("rmi://localhost:1099/Manufacturer", mob);
			Naming.rebind("rmi://localhost:1099/Client", cob);
			Naming.rebind("rmi://localhost:1099/DeliveryCenter", dob);
			System.out.println("server started");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}