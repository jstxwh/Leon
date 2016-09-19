package cluster;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public class JedisClusterDemo {
	private JedisPoolConfig config=new JedisPoolConfig();
	/**
	 * ʹ��ǰ��Ҫ���ú����ӳ�����
	 */
	@Before
	public void beforeConfig(){
		config.setMaxIdle(1000);//����������ʱ��
		config.setMaxTotal(10);//�������������Ŀ
		config.setMaxWaitMillis(1000);//�������ȴ�ʱ��
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
	}
	/**
	 * ����ģʽ�µ�ʹ��
	 */
	@Test
	public void testJedisPool(){
		//1�������������ӳ����ò������������ӳض���
		JedisPool pool=new JedisPool(config, "192.168.1.110",6390);
		//2�������ӳػ�ȡһ��Jedisʵ��
		Jedis jedis=pool.getResource();
		//3��ִ��ҵ���߼�����
		jedis.set("user", "������");
		System.out.println(jedis.get("user"));
		//4���ر����ӣ�����/�ر����ӳ�
		jedis.close();
		pool.close();
	}
	/**
	 * ��Ⱥģʽ�µ�ʹ��
	 * @throws IOException
	 */
	@Test
	public void testCluster() throws IOException{
		//1��������Ⱥ�ڵ���Ϣ
		Set<HostAndPort> hostAndPorts=new HashSet<HostAndPort>();
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6379));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6380));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6381));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6382));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6383));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6384));
		//2�����ڵ���Ϣ��Set���ϵ���ʽ�����ӳ�����һ����Ϊ��������һ��Jedis��Ⱥ����
		JedisCluster jedisCluster=new JedisCluster(hostAndPorts, config);
		//3��ִ��ҵ���߼�����
		String user = jedisCluster.get("user");
		System.out.println(user);
		//4���ر��������
		jedisCluster.close();
	}
}
