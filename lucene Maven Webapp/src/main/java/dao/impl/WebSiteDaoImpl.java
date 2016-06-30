/**
 * 
 */
package dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.GradientFormatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;

import util.IndexUtil;
import dao.WebSiteDao;
import entity.WebSite;

/**
 * <p><b>Description:</b> </p>
 * <p><b>Date:</b> 2016年6月29日下午10:10:08</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class WebSiteDaoImpl implements WebSiteDao {

	public void insertWebSite(Document doc) throws IOException{
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		writer.addDocument(doc);
	}

	public void insertBatchWebSite(List<Document> docs) throws IOException {
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		writer.addDocuments(docs);
	}

	public void deleteWebSite(String condition) throws IOException, ParseException {
		// TODO Auto-generated method stub
		IndexWriter writer=IndexUtil.getIndexWriter();
		QueryParser parser=new MultiFieldQueryParser(new String[]{"sname","fname","username","password","email","description"},IndexUtil.getAnalyzer());
		Query query = parser.parse(condition);
		writer.deleteDocuments(query);
	}

	public void deleteAll() throws IOException {
		// TODO Auto-generated method stub
		IndexWriter writer = IndexUtil.getIndexWriter();
		writer.deleteAll();
	}

	public WebSite selectWebSite(int docId) throws IOException {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		Document doc = reader.document(docId);
		WebSite site=new WebSite();
		site.setSname(doc.get("sname"));
		site.setFname(doc.get("fname"));
		site.setUsername(doc.get("username"));
		site.setPassword(doc.get("password"));
		site.setEmail(doc.get("email"));
		site.setDescription(doc.get("description"));
		return site;
	}

	public List<WebSite> selectWebSiteByCondition(String condition,int pageNum, int num,int count) throws ParseException, IOException, InvalidTokenOffsetsException {
		// TODO Auto-generated method stub
		List<WebSite> sites=new ArrayList<WebSite>();
		IndexReader reader = IndexUtil.getIndexReader();
		IndexSearcher searcher=new IndexSearcher(reader);
		QueryParser parser=new MultiFieldQueryParser(new String[]{"sname","fname","username","password","email","description"},IndexUtil.getAnalyzer());
		parser.setAllowLeadingWildcard(true);//规定可以以*号开头
		Query query = parser.parse(condition);
		QueryScorer scorer=new QueryScorer(query);
		Formatter formatter=new GradientFormatter(3,"#000000","#000000","#9AFF9A","#9AFF9A");
		Highlighter lighter=new Highlighter(formatter,scorer);
		lighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
		TopDocs docs = searcher.search(query, count);
		ScoreDoc score[]=docs.scoreDocs;
		for(int i=(num-1)*pageNum;i<Math.min(count,num*pageNum);i++){
			WebSite site=new WebSite();
			Document doc = searcher.doc(score[i].doc);
			String text=lighter.getBestFragment(IndexUtil.getAnalyzer(), "sname", doc.get("sname"));
			site.setSname(text==null?doc.get("sname"):text);
			text=lighter.getBestFragment(IndexUtil.getAnalyzer(), "fname", doc.get("fname"));
			site.setFname(text==null?doc.get("fname"):text);
			text=lighter.getBestFragment(IndexUtil.getAnalyzer(), "username", doc.get("username"));
			site.setUsername(text==null?doc.get("username"):text);
			text=lighter.getBestFragment(IndexUtil.getAnalyzer(), "password", doc.get("password"));
			site.setPassword(text==null?doc.get("password"):text);
			text=lighter.getBestFragment(IndexUtil.getAnalyzer(), "email", doc.get("email"));
			site.setEmail(text==null?doc.get("email"):text);
			text=lighter.getBestFragment(IndexUtil.getAnalyzer(), "description", doc.get("description"));
			site.setDescription(text==null?doc.get("description"):text);
			sites.add(site);
		}
		return sites;
	}

	public List<WebSite> selectAll(int pageNum, int num,int count) throws IOException {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		IndexSearcher searcher=new IndexSearcher(reader);
		Query query=new MatchAllDocsQuery();
		TopDocs docs = searcher.search(query, count);
		ScoreDoc[] scores=docs.scoreDocs;
		List<WebSite> sites=new ArrayList<WebSite>();
		for(int i=(num-1)*pageNum;i<Math.min(count,num*pageNum);i++){
			Document doc = searcher.doc(scores[i].doc);
			WebSite site=new WebSite();
			site.setSname(doc.getField("sname").stringValue());
			site.setFname(doc.get("fname"));
			site.setUsername(doc.get("username"));
			site.setPassword(doc.get("password"));
			site.setEmail(doc.get("email"));
			site.setDescription(doc.get("description"));
			sites.add(site);
		}
		return sites;
	}

	public int countByCondition(String condition) throws ParseException, IOException {
		// TODO Auto-generated method stub
		IndexReader reader = IndexUtil.getIndexReader();
		IndexSearcher searcher=new IndexSearcher(reader);
		TotalHitCountCollector collector=new TotalHitCountCollector();
		QueryParser parser=new MultiFieldQueryParser(new String[]{"sname","fname","username","password","email","description"},IndexUtil.getAnalyzer());
		Query query = parser.parse(condition);
		searcher.search(query, collector);
		return collector.getTotalHits();
	}
}
