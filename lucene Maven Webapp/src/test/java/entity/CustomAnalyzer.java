package entity;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;

/**
 * <p><b>Description:</b> 自定义Analyzer<br/>
 * 实现多域值分析时将间隙手动变大，位置查询时不至于出现在错误的边界</p>
 * <p><b>Date:</b> 2016年6月15日下午2:46:07</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class CustomAnalyzer extends StopwordAnalyzerBase {

	@Override
	public int getPositionIncrementGap(String fieldName) {
		// TODO Auto-generated method stub
		return super.getPositionIncrementGap(fieldName)+100;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		final StandardTokenizer src = new StandardTokenizer();
	    TokenStream tok = new StandardFilter(src);
	    tok = new LowerCaseFilter(tok);
	    tok = new StopFilter(tok, stopwords);
	    return new TokenStreamComponents(src, tok);
	}

}
