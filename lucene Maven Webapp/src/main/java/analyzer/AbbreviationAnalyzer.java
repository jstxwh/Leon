/**
 * 
 */
package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;

/**
 * <p><b>Description:</b> 自定义用于简称的分词器</p>
 * <p><b>Date:</b> 2016年6月29日下午10:48:15</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class AbbreviationAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer=new KeywordTokenizer();
		TokenStream stream=new LowerCaseFilter(tokenizer);
		return new TokenStreamComponents(tokenizer,stream);
	}

}
