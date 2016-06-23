/**
 * 
 */
package server.servie;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p><b>Description:</b> RMI接口<br/>
 * 继承Remote接口<br/>
 * 每个方法的后面都必须抛出一个RemoteException异常</p>
 * <p><b>Date:</b> 2016年6月23日下午7:55:31</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public interface IHello extends Remote {
	public void hello() throws RemoteException;
	public String get() throws RemoteException;
}
