package test;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import service.WebSiteService;
import service.impl.WebSiteServiceImpl;
import analyzer.AbbreviationAnalyzer;
import entity.WebSite;

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
	public void countByConditionTest() throws Exception{
		String condition="Test";
		System.out.println(service.countByCondition(condition));
	}
	@Test
	public void addBatchWebSiteTest() throws Exception{
		service.addBatchWebSite("C:/资料.xlsx");
	}
	@Test
	public void removeAllTest() throws Exception{
		service.removeAll();
	}
	@Test 
	public void queryWebSiteByConditionTest() throws Exception{
		List<WebSite> list = service.queryWebSiteByCondition("xbt~", 15, 1);
		System.out.println(list==null?"null":list.size());
		if(list!=null)
			for(WebSite w:list)
				System.out.println(w);
	}
	@Test
	public void abbreviationAnalyzerTest() throws IOException{
		Analyzer analyzer=new AbbreviationAnalyzer();
		TokenStream stream = analyzer.tokenStream("sname","HD-S");
		CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);
		stream.reset();
		while(stream.incrementToken())
			System.out.println(attribute.toString()+"----");
		stream.end();
		stream.close();
		analyzer.close();
	}
	@Test
	public void addWebSiteTest() throws Exception{
		WebSite webSite=new WebSite("Test","Test","Test","Test","Test","Test");
		service.addWebSite(webSite);
	}
	@Test
	public void removeWebSiteTest() throws Exception{
		service.removeWebSite("sname:Test");
	}
	@Test
	public void queryAllTest() throws Exception{
		List<WebSite> sites=service.queryAll(50, 1);
		for(WebSite s:sites)
			System.out.println(s);
	}
}
