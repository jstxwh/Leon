/**
 * 
 */
package entity;

import java.io.BufferedReader;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;

/**
 * <p><b>Description:</b> </p>
 * <p><b>Date:</b> 2016年6月24日下午3:41:08</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class ChineseSimpleAnalyzer extends Analyzer {

	@Override
	public int getPositionIncrementGap(String fieldName) {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	protected Reader initReader(String fieldName, Reader reader) {
		// TODO Auto-generated method stub
		while(reader.ready()){
			
		}
		return super.initReader(fieldName, reader);
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

}
