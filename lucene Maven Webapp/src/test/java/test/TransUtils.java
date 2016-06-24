/**
 * 
 */
package test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.spreada.utils.chinese.ZHConverter;

/**
 * <p><b>Description:</b> 中文简繁体互转</p>
 * <p><b>Date:</b> 2016年6月24日上午11:07:04</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class TransUtils {
	public static void main(String[] args) throws UnsupportedEncodingException {
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		String sentence = converter.convert("中華人民共和國");
		System.out.println(sentence);
	}
	@Test
	public void testReader() throws IOException{
		Reader reader=new StringReader("中華人民共和國");
		int count=0;
		while((count=reader.read())!=-1){
			char c=(char)count;
		}
	}
}
