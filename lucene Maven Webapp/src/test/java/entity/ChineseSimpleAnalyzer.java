/**
 * 
 */
package entity;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;

import com.spreada.utils.chinese.ZHConverter;

/**
 * <p><b>Description:</b> 该自定义Analyzer主要用于中文繁体转换成简体，以及英文名转为小写</p>
 * <p><b>Date:</b> 2016年6月24日下午3:41:08</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class ChineseSimpleAnalyzer extends StopwordAnalyzerBase {

	@Override
	public int getPositionIncrementGap(String fieldName) {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	protected Reader initReader(String fieldName, Reader reader) {
		// TODO Auto-generated method stub
		int acc=0;
		StringBuilder sb=new StringBuilder();
		try {
			while((acc=reader.read())!=-1)
				sb.append((char)acc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("initReader encountered an error!");
		}
		ZHConverter zh=ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		reader=new StringReader(zh.convert(sb.toString()));
		return super.initReader(fieldName, reader);
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer=new LowerCaseTokenizer();
		TokenStream tokenStream=new StopFilter(tokenizer,stopwords);
		return new TokenStreamComponents(tokenizer,tokenStream);
	}
}
