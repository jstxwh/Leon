/**
 * 
 */
package service;

import java.util.List;

import entity.WebSite;

/**
 * <p><b>Description:</b> 网站信息查询接口</p>
 * <p><b>Date:</b> 2016年6月29日下午2:26:29</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public interface WebSiteService {
	public void addWebSite(WebSite webSite);
	public void addBatchWebSite(String path) throws Exception;
	public void removeWebSite(WebSite webSite);
	public void removeAll() throws Exception;
	public void modifyWebSite(WebSite webSite);
	public WebSite queryWebSite(WebSite webSite);
	public List<WebSite> queryWebSiteByCondition(String condition,int pageNum,int num) throws Exception;
	public List<WebSite> queryAll();
	public int countByCondition(String condition) throws Exception;
}
