package lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.ReuseStrategy;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.search.BoostAttribute;
import org.junit.Test;

/**
 * <p><b>Description:</b> 简单的Lucene测试类</p>
 * <p><b>Date:</b> 2016年6月15日上午9:41:36</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class LuceneSimpleTest {
	/**
	 * <p><b>Description:</b> Analyzer底层实现: <br/>
	 * 主要用于测试所使用的Analyzer的分词效果如何<br/>
	 * 详情参见官方API文档</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年6月15日 上午12:42:23</p>
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	@Test
	public void analyzerTest() throws IOException{
		Analyzer analyzer = new SimpleAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("Lucene", "Hello, Lucene! I love it!");
		CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
		OffsetAttribute offset = tokenStream.addAttribute(OffsetAttribute.class);
		BoostAttribute boost = tokenStream.addAttribute(BoostAttribute.class);
		tokenStream.reset();
		while(tokenStream.incrementToken()){
			//可以通过以下两行代码查看出全部状况
			/*String string = tokenStream.reflectAsString(true);
			System.out.println(string);*/
			System.out.println("CharTerm: "+term.toString()+"  Offset: "+offset.startOffset()+"----"+offset.endOffset()+"  Boost: "+boost.getBoost());
		}
		tokenStream.end();
		tokenStream.close();
	}
	@Test
	public void perFieldAnalyzerWrapperTest(){
		AnalyzerWrapper analyzer=new PerFieldAnalyzerWrapper(new KeywordAnalyzer());
		ReuseStrategy strategy = analyzer.getReuseStrategy();
		System.out.println(strategy.toString());
		analyzer.close();
	}
}
