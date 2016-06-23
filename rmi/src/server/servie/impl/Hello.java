/**
 * 
 */
package server.servie.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.servie.IHello;

/**
 * <p><b>Description:</b> RMI接口的实现类<br/>
 * 接口中抛出的RemoteException必须向上抛出，到服务器端处理</p>
 * <p><b>Date:</b> 2016年6月23日下午8:00:29</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class Hello extends UnicastRemoteObject implements IHello {
	private static final long serialVersionUID = 1L;
	/**
	 * <p><b>Description:</b> 因UnicastRemoteObject构造函数抛出RemoteException异常，因而实现类的构造方法也要抛出相应的异常<br/>
	 * @throws RemoteException
	 */
	public Hello() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hello() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Hello RMI!");
	}

	@Override
	public String get() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Get a nice shoot!");
		return "Have Got It!";
	}

}
