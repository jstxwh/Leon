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
	 * 使用前，要设置好连接池配置
	 */
	@Before
	public void beforeConfig(){
		config.setMaxIdle(1000);//设置最大空闲时间
		config.setMaxTotal(10);//设置最大链接数目
		config.setMaxWaitMillis(1000);//设置最大等待时间
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
	}
	/**
	 * 单机模式下的使用
	 */
	@Test
	public void testJedisPool(){
		//1、根据配置连接池配置参数，创建连接池对象
		JedisPool pool=new JedisPool(config, "192.168.1.110",6390);
		//2、由连接池获取一个Jedis实例
		Jedis jedis=pool.getResource();
		//3、执行业务逻辑操作
		jedis.set("user", "逗你玩");
		System.out.println(jedis.get("user"));
		//4、关闭连接，销毁/关闭连接池
		jedis.close();
		pool.close();
	}
	/**
	 * 集群模式下的使用
	 * @throws IOException
	 */
	@Test
	public void testCluster() throws IOException{
		//1、给出集群节点信息
		Set<HostAndPort> hostAndPorts=new HashSet<HostAndPort>();
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6379));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6380));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6381));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6382));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6383));
		hostAndPorts.add(new HostAndPort("192.168.1.110", 6384));
		//2、将节点信息以Set集合的形式与连接池配置一起作为参数构造一个Jedis集群对象
		JedisCluster jedisCluster=new JedisCluster(hostAndPorts, config);
		//3、执行业务逻辑操作
		String user = jedisCluster.get("user");
		System.out.println(user);
		//4、关闭连接与池
		jedisCluster.close();
	}
}
