/**
 * 
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.store.FSDirectory;

import analyzer.AbbreviationAnalyzer;

/**
 * <p><b>Description:</b> 索引创建、读写的工具类</p>
 * <p><b>Date:</b> 2016年6月29日下午3:03:09</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class IndexUtil {
	private static Properties pro=new Properties();
	private static String path;
	private static Map<String,Analyzer> fieldAnalyzer=new HashMap<String,Analyzer>();//可以考虑将分词器信息写入XML文件中解析
	private static final ThreadLocal<IndexWriter> t=new ThreadLocal<IndexWriter>();
	private static final ThreadLocal<IndexReader> r=new ThreadLocal<IndexReader>();
	static{
		InputStream is=null;
		try{
			is=IndexUtil.class.getResourceAsStream("/index.properties");
			pro.load(is);
			path=pro.getProperty("path");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public static Analyzer getAnalyzer(){
		fieldAnalyzer.put("sname", new AbbreviationAnalyzer());
		fieldAnalyzer.put("username", new KeywordAnalyzer());
		fieldAnalyzer.put("password", new KeywordAnalyzer());
		return new PerFieldAnalyzerWrapper(new StandardAnalyzer(),fieldAnalyzer);
	}
	public static IndexWriter getIndexWriter(){
		IndexWriter writer=t.get();
		if(writer==null){
			IndexWriterConfig config=new IndexWriterConfig(getAnalyzer());
			try {
				writer=new IndexWriter(FSDirectory.open(Paths.get(path)),config);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("请查看配置文件");
			}
			t.set(writer);
		}
		return writer;
	}
	public static IndexReader getIndexReader(){
		IndexReader reader=r.get();
		if(r.get()==null){
			try {
				IndexReader indexReader=DirectoryReader.open(FSDirectory.open(Paths.get(path)));
				reader=new MultiReader(indexReader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("请查看索引目录配置");
			}
			r.set(reader);
		}
		return reader;
	}
	public static void close(IndexWriter writer,IndexReader reader){
		if(writer!=null)
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(reader!=null)
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
