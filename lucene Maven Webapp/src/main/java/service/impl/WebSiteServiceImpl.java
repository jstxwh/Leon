/**
 * 
 */
package service.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
	private WebSiteDao webSiteDao=new WebSiteDaoImpl();
	public void addWebSite(WebSite webSite){
		// TODO Auto-generated method stub
		IndexWriter writer = IndexUtil.getIndexWriter();
		Document doc=new Document();
		doc.add(new Field("sname",webSite.getSname(),IndexUtil.snameType));
		doc.add(new Field("fname",webSite.getFname(),IndexUtil.fnameType));
		doc.add(new Field("username",webSite.getUsername(),IndexUtil.unameType));
		doc.add(new Field("password",webSite.getPassword(),IndexUtil.unameType));
		doc.add(new Field("email",webSite.getEmail(),IndexUtil.fnameType));
		doc.add(new Field("description",webSite.getDescription(),IndexUtil.fnameType));
		try {
			webSiteDao.insertWebSite(doc);
			writer.prepareCommit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				writer.rollback();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			IndexUtil.close(writer, null);
		}
	}

	public void addBatchWebSite(String path){
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		List<Document> docs=new ArrayList<Document>();
		Workbook book=null;
		try {
			book = new XSSFWorkbook(path);
			Sheet sheet = book.getSheetAt(1);
			Iterator<Row> rows = sheet.iterator();
			while(rows.hasNext()){
				Row row = rows.next();
				Document doc=new Document();
				String sname = POIUtil.getCellValue(row.getCell(0));
				Field snameField=new Field("sname",sname,IndexUtil.snameType);
				doc.add(snameField);
				String fname = POIUtil.getCellValue(row.getCell(1));
				Field fnameField=new Field("fname",fname,IndexUtil.fnameType);
				doc.add(fnameField);
				String username = POIUtil.getCellValue(row.getCell(2));
				Field usernameField=new Field("username",username,IndexUtil.unameType);
				doc.add(usernameField);
				String password = POIUtil.getCellValue(row.getCell(3));
				Field passwordField=new Field("password",password,IndexUtil.unameType);
				doc.add(passwordField);
				String email = POIUtil.getCellValue(row.getCell(4));
				Field emailField=new Field("email",email,IndexUtil.fnameType);
				doc.add(emailField);
				String description = POIUtil.getCellValue(row.getCell(5));
				Field descriptionField=new Field("description",description,IndexUtil.fnameType);
				doc.add(descriptionField);
				docs.add(doc);
			}
			webSiteDao.insertBatchWebSite(docs);
			writer.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				writer.rollback();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(book!=null)
				try {
					book.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			IndexUtil.close(writer,null);
		}
	}

	public void removeWebSite(String condition){
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		try {
			webSiteDao.deleteWebSite(condition);
			writer.prepareCommit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				writer.rollback();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			IndexUtil.close(writer, null);
		}
	}

	public void removeAll(){
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		try {
			webSiteDao.deleteAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				writer.rollback();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			IndexUtil.close(writer, null);
		}
	}

	public WebSite queryWebSite(int docId) {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		try {
			WebSite site=webSiteDao.selectWebSite(docId);
			return site;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("要查询的文档不存在");
		}finally{
			IndexUtil.close(null, reader);
		}
	}

	public List<WebSite> queryWebSiteByCondition(String condition,int pageNum,int num) {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		try {
			int count = webSiteDao.countByCondition(condition);
			if((num-1)*pageNum>count||count==0)
				return null;
			List<WebSite> sites=webSiteDao.selectWebSiteByCondition(condition, pageNum, num, count);
			return sites;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("请检查参数是否正确");
		}finally{
			IndexUtil.close(null, reader);
		}
	}

	public List<WebSite> queryAll(int pageNum, int num){
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		int count=reader.numDocs();
		try {
			List<WebSite> sites=webSiteDao.selectAll(pageNum, num, count);
			return sites;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("请查看参数是否正确");
		}finally{
			IndexUtil.close(null, reader);
		}
	}

	public int countByCondition(String condition){
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		try {
			int hits = webSiteDao.countByCondition(condition);
			return hits;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("查询错误，请检查查询条件是否正确");
		} finally{
			IndexUtil.close(null, reader);
		}
	}

}
