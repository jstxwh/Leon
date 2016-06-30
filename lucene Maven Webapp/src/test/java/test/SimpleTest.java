/**
 * 
 */
package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import timer.IndexTimerTask;

import com.spreada.utils.chinese.ZHConverter;

import entity.ChineseSimpleAnalyzer;

/**
 * <p><b>Description:</b> 中文简繁体互转</p>
 * <p><b>Date:</b> 2016年6月24日上午11:07:04</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class SimpleTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		String sentence = converter.convert("中華人民共和國");
		System.out.println(sentence);
	}
	/**
	 * <p><b>Description:</b> 测试自定义中文繁体转简体分词器</p>
	 * <p><b>Date:</b> 2016年6月25日下午12:02:50</p>
	 * @throws IOException
	 */
	@Test
	public void testReader() throws IOException{
		Analyzer analyzer=new ChineseSimpleAnalyzer();
		TokenStream stream = analyzer.tokenStream("test", "吉沢明步、吉澤明步,Yoshizawa Akiho");
		stream.reset();
		while(stream.incrementToken()){
			CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);
			System.out.println(attribute.toString());
		}
		stream.end();
		analyzer.close();
	}
	/**
	 * <p><b>Description:</b> 定时器测试，在JUnit测试中看不出来，用main()方法可见</p>
	 */
	@Test
	public void timerTest(){
		Timer time=new Timer();
		TimerTask task=new IndexTimerTask();
		time.scheduleAtFixedRate(task, new Date(), 1000);
	}
}
