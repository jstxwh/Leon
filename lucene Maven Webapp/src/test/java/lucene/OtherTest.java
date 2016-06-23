/**
 * 
 */
package lucene;

import org.junit.Test;

/**
 * <p><b>Description:</b> </p>
 * <p><b>Date:</b> 2016年6月16日上午10:56:30</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class OtherTest {
	@Test
	public void AscIITest(){
		System.out.println((char)(-1));
		System.out.println("\r\n");
		String s=new String(new char[]{(char)(-1)});
		System.out.println(s.equals("\r\n"));
		System.out.println((int)'\n');
		System.out.println((int)';');
	}
}
