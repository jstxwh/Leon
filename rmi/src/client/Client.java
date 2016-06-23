/**
 * 
 */
package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.servie.IHello;

/**
 * <p><b>Description:</b> RMI调用远程对象的客户端程序<br/>
 * 程序的执行并不是在客户端，而是在服务端<br/>
 * 但是执行的结果会返回给客户端</p>
 * <p><b>Date:</b> 2016年6月23日下午8:20:05</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class Client {
	public static void main(String[] args) {
		try {
			//在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
			IHello hello = (IHello)Naming.lookup("rmi://localhost:8888/RHello");
			hello.hello();
			String message = hello.get();
			System.out.println(message);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
