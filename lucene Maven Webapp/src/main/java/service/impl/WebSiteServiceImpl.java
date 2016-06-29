/**
 * 
 */
package service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import service.WebSiteService;
import util.IndexUtil;
import util.POIUtil;
import dao.WebSiteDao;
import dao.impl.WebSiteDaoImpl;
import entity.WebSite;

/**
 * <p><b>Description:</b> 网站信息查询的实现类，通过Lucene实现</p>
 * <p><b>Date:</b> 2016年6月29日下午2:33:25</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class WebSiteServiceImpl implements WebSiteService {
	private WebSiteDao webSite=new WebSiteDaoImpl();
	public void addWebSite(WebSite webSite) {
		// TODO Auto-generated method stub
		
	}

	public void addBatchWebSite(String path) throws Exception {
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		List<Document> docs=new ArrayList<Document>();
		Workbook book=new XSSFWorkbook(path);
		Sheet sheet = book.getSheetAt(1);
		Iterator<Row> rows = sheet.iterator();
		FieldType snameType=new FieldType();
		snameType.setOmitNorms(true);
		snameType.setStored(true);
		snameType.setStoreTermVectors(false);
		snameType.setIndexOptions(IndexOptions.DOCS);
		FieldType fnameType=new FieldType();
		fnameType.setOmitNorms(true);
		fnameType.setStored(true);
		fnameType.setStoreTermVectors(true);
		fnameType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		while(rows.hasNext()){
			Row row = rows.next();
			Document doc=new Document();
			String sname = POIUtil.getCellValue(row.getCell(0));
			Field snameField=new Field("sname",sname,snameType);
			doc.add(snameField);
			String fname = POIUtil.getCellValue(row.getCell(1));
			Field fnameField=new Field("fname",fname,fnameType);
			doc.add(fnameField);
			String username = POIUtil.getCellValue(row.getCell(2));
			Field usernameField=new Field("username",username,snameType);
			doc.add(usernameField);
			String password = POIUtil.getCellValue(row.getCell(3));
			Field passwordField=new Field("password",password,snameType);
			doc.add(passwordField);
			String email = POIUtil.getCellValue(row.getCell(4));
			Field emailField=new Field("email",email,fnameType);
			doc.add(emailField);
			String description = POIUtil.getCellValue(row.getCell(5));
			Field descriptionField=new Field("description",description,fnameType);
			doc.add(descriptionField);
			docs.add(doc);
		}
		webSite.insertBatchWebSite(docs);
		book.close();
		IndexUtil.close(writer,null);
	}

	public void removeWebSite(WebSite webSite) {
		// TODO Auto-generated method stub
		
	}

	public void removeAll() throws Exception {
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		webSite.deleteAll();
		IndexUtil.close(writer, null);
	}

	public void modifyWebSite(WebSite webSite) {
		// TODO Auto-generated method stub
		
	}

	public WebSite queryWebSite(WebSite webSite) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WebSite> queryWebSiteByCondition(String condition,int pageNum,int num) throws Exception {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		int count = webSite.countByCondition(condition);
		if((num-1)*pageNum>count||count==0)
			return null;
		List<WebSite> sites=webSite.selectWebSiteByCondition(condition, pageNum, num, count);
		IndexUtil.close(null, reader);
		return sites;
	}

	public List<WebSite> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public int countByCondition(String condition) throws Exception {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		int hits = webSite.countByCondition(condition);
		IndexUtil.close(null, reader);
		return hits;
	}

}
