/**
 * 
 */
package entity;

import java.io.IOException;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.LeafCollector;

/**
 * <p><b>Description:</b> </p>
 * <p><b>Date:</b> 2016年6月17日上午11:57:47</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class CustomCollector implements Collector {
	public LeafCollector getLeafCollector(LeafReaderContext context)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean needsScores() {
		// TODO Auto-generated method stub
		return false;
	}

}
