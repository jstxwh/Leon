/**
 * 
 */
package tika;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.translate.Translator;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p><b>Description:</b> Tika API</p>
 * <p><b>Date:</b> 2016年6月22日上午11:47:19</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class TikaOperationTest {
	@Test
	public void simpleOperation() throws TikaException, IOException, SAXException{
		InputStream is=new FileInputStream("D:\\Personal\\审计样例数据\\审计1.2.doc");
		Tika tika=new Tika();
		//调用detect()方法的返回值为响应的探测类型
		String str = tika.detect(is);
		System.out.println(str);
		Detector detector = tika.getDetector();
		System.out.println("Detector:  "+detector.getClass());
		int length = tika.getMaxStringLength();
		System.out.println("MaxStringLength:  "+length);
		Parser parser = tika.getParser();
		System.out.println("Parser:  "+parser);
		Translator translator = tika.getTranslator();
		System.out.println("Translator:  "+translator);
		ContentHandler contentHandler=new DefaultHandler();
		Metadata metadata=new Metadata();
		ParseContext parseContext=new ParseContext();
		parser.parse(is, contentHandler, metadata, parseContext);
	}
	/**
	 * <p><b>Description:</b> 简单的解析Word文档</p>
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws TikaException
	 */
	@Test
	public void splitFile() throws FileNotFoundException, IOException, TikaException{
		Tika tika=new Tika();
		String text = tika.parseToString(new FileInputStream("D:\\Personal\\审计样例数据\\审计1.2.doc"));
		System.out.println(text);
	}
}
