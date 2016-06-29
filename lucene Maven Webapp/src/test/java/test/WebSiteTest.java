/**
 * 
 */
package test;

import org.junit.Test;

import service.WebSiteService;
import service.impl.WebSiteServiceImpl;

/**
 * <p><b>Description:</b> WebSite测试</p>
 * <p><b>Date:</b> 2016年6月29日下午5:40:15</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class WebSiteTest {
	private static WebSiteService service=new WebSiteServiceImpl();
	@Test
	public void queryWebSiteByConditionTest() throws Exception{
		String condition="jstxwh Saka";
		System.out.println(service.countByCondition(condition));
	}
	@Test
	public void addBatchWebSiteTest() throws Exception{
		service.addBatchWebSite("D:/Personal/资料.xlsx");
	}
}
