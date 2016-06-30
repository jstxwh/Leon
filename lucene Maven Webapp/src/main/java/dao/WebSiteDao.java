/**
 * 
 */
package dao;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

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
	public void insertWebSite(Document doc) throws IOException;
	public void insertBatchWebSite(List<Document> docs) throws IOException;
	public void deleteWebSite(String condition) throws IOException, ParseException;
	public void deleteAll() throws IOException;
	public WebSite selectWebSite(int docId) throws IOException;
	public List<WebSite> selectWebSiteByCondition(String condition,int pageNum,int num,int count) throws ParseException, IOException,InvalidTokenOffsetsException;
	public List<WebSite> selectAll(int pageNum, int num,int count) throws IOException;
	public int countByCondition(String condition)  throws ParseException, IOException;
}
