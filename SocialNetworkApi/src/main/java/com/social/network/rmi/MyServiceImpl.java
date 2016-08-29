package com.social.network.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl extends UnicastRemoteObject implements MyService{

	protected MyServiceImpl() throws RemoteException {
	}

	public String sayHello(){
		return null;
	}

}
