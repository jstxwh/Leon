/**
 * 
 */
package dao;

import java.util.List;

import org.apache.lucene.document.Document;

import entity.WebSite;

/**
 * <p><b>Description:</b> </p>
 * <p><b>Date:</b> 2016年6月29日下午10:08:47</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public interface WebSiteDao {
	public void insertWebSite(WebSite webSite);
	public void insertBatchWebSite(List<Document> docs) throws Exception;
	public void deleteWebSite(WebSite webSite);
	public void deleteAll() throws Exception;
	public void updateWebSite(WebSite webSite);
	public WebSite selectWebSite(WebSite webSite);
	public List<WebSite> selectWebSiteByCondition(String condition,int pageNum,int num,int count) throws Exception;
	public List<WebSite> selectAll();
	public int countByCondition(String condition) throws Exception;
}
