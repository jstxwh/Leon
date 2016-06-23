/**
 * 
 */
package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import server.servie.IHello;
import server.servie.impl.Hello;

/**
 * <p><b>Description:</b> RMI暴露服务的服务器端程序<br/>
 * 程序一旦启动即作为服务器启动</p>
 * <p><b>Date:</b> 2016年6月23日下午8:03:56</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class Server {
	public static void main(String[] args) throws RemoteException {
	try {
			//1、创建一个RMI实例
			IHello hello=new Hello();
			//2、向本地的远程对象RMI注册表注册端口号，如果不注册的话，默认为1099
			LocateRegistry.createRegistry(8888);
			//3、将远程对象注册到RMI注册服务器上，并指定一个唯一的名字RHello，协议名rmi可以省略
			//注意：绑定的是远程对象的一个实例
			Naming.bind("rmi://localhost:8888/RHello", hello);
			System.out.println("--------------------远程对象IHello绑定成功！------------------");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
