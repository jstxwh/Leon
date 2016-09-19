package cluster;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
public class JedisClusterOperation {
	private JedisCluster jedisCluster=null;
	@Before
	public void beforeConfig(){
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxIdle(1000);//����������ʱ��
		config.setMaxTotal(10);//�������������Ŀ
		config.setMaxWaitMillis(1000);//�������ȴ�ʱ��
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		Set<HostAndPort> hostAndPorts=new HashSet<HostAndPort>();
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6379));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6380));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6381));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6382));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6383));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6384));
		jedisCluster=new JedisCluster(hostAndPorts, config);
	}
	@Test
	public void testCluster() throws IOException{
		
	}
	/**
	 * 
	 * <p><b>Description:</b> POI���Գ��򣬴�3.8��ʼ�ſ�ʼ֧��xlsx��ʽ��Excel�ļ�����ʱ������</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016��5��15�� ����11:57:14</p>
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testPOI() throws FileNotFoundException, IOException{
		InputStream inputStream=new FileInputStream("C:\\test.xls");
		Workbook workbook=new  HSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheet("XXX");
		Iterator<Row> iterator = sheet.iterator();
		iterator.next();
		while(iterator.hasNext()){
			Row row = iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();  
			while(cellIterator.hasNext()){
				System.out.print(cellIterator.next()+"\t");
			}
			System.out.println();
		}
		workbook.close();
		inputStream.close();
	}
	@After
	public void closeCluster() throws IOException{
		jedisCluster.close();
	}
}
