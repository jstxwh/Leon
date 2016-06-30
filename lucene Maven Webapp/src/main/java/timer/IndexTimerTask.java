/**
 * 
 */
package timer;
import java.io.IOException;
import java.util.TimerTask;
import org.apache.lucene.index.IndexWriter;
import util.IndexUtil;

/**
 * <p><b>Description:</b> 索引定时优化</p>
 * <p><b>Date:</b> 2016年6月30日下午3:14:20</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class IndexTimerTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		IndexWriter writer = IndexUtil.getIndexWriter();
		try {
			if(writer.hasDeletions())
				writer.forceMergeDeletes();
			writer.forceMerge(5);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IndexUtil.close(writer, null);
		}
	}

}
