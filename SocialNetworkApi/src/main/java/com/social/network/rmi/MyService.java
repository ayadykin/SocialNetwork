package com.social.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyService extends Remote{

	String sayHello() throws RemoteException;
}
