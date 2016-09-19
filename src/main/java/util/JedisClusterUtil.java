package util;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * <p><b>Description:</b> 获取与关闭Redis连接的工具类</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Date:</b> 2016年5月15日 上午3:04:05</p>
 * @author Saka
 * @version v1.0
 */
public class JedisClusterUtil {
	private static final Logger LOGGER=LogManager.getLogger();
	private static final JedisPoolConfig CONFIG=new JedisPoolConfig();
	private static final Properties PROPERTIES=new Properties();
	private static final ThreadLocal<JedisPool> JEDISPOOLCONNECTION=new ThreadLocal<JedisPool>();
	private static final ThreadLocal<JedisCluster> JEDISCLUSTERCONNECTION=new ThreadLocal<JedisCluster>();
	/**
	 * 初始化Jedis连接池配置
	 */
	static{
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = JedisClusterUtil.class.getResourceAsStream("/redis.properties");//参数命名规则参见官方API
			PROPERTIES.load(resourceAsStream);
			CONFIG.setMaxIdle(Integer.parseInt(PROPERTIES.getProperty("maxIdle")));
			CONFIG.setMaxTotal(Integer.parseInt(PROPERTIES.getProperty("maxTotal")));
			CONFIG.setMaxWaitMillis(Integer.parseInt(PROPERTIES.getProperty("maxWaitMillis")));
			CONFIG.setTestOnBorrow(Boolean.getBoolean(PROPERTIES.getProperty("testOnBorrow")));
			CONFIG.setTestOnReturn(Boolean.getBoolean(PROPERTIES.getProperty("testOnReturn")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Check the properties configuration file");
		}finally{
			if(resourceAsStream!=null){
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LOGGER.error("Can not close the inputstream");
				}
			}
		}
	}
	/**
	 * <p><b>Description:</b> 获取Jedis连接池</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年5月15日 上午3:08:22</p>
	 */
	public static JedisPool getJedisPool(){
		JedisPool jedisPool=JEDISPOOLCONNECTION.get();
		if(jedisPool==null){
			jedisPool=new JedisPool(CONFIG,PROPERTIES.getProperty("host"),Integer.parseInt(PROPERTIES.getProperty("port")));
			JEDISPOOLCONNECTION.set(jedisPool);
		}
		return jedisPool;
	}
	/**
	 * <p><b>Description:</b> 获取JedisCluster</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年5月15日 上午3:09:43</p>
	 * @return JedisCluster
	 */
	public static JedisCluster getJedisCluster(){
		JedisCluster jedisCluster=JEDISCLUSTERCONNECTION.get();
		if(jedisCluster==null){
			Set<HostAndPort> set=new HashSet<HostAndPort>();
			Pattern pattern=Pattern.compile("^hostAndPort[0-9]+$");
			Set<Object> keySet = PROPERTIES.keySet();
			for(Object o:keySet){
				if(pattern.matcher(o.toString()).matches()) {
					String[] strs = PROPERTIES.getProperty(o.toString()).split(":");
					set.add(new HostAndPort(strs[0],Integer.parseInt(strs[1])));
				}
			}
			jedisCluster=new JedisCluster(set,CONFIG);
			JEDISCLUSTERCONNECTION.set(jedisCluster);
		}
		return jedisCluster;
	}
	/**
	 * <p><b>Description:</b> 关闭Jedis连接池以及Jedis连接</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年5月15日 上午3:10:22</p>
	 * @param jedisPool
	 * @param jedis
	 */
	public static void closeJedis(JedisPool jedisPool,Jedis jedis){
		if(jedis!=null&&jedis.isConnected()){
			jedis.close();
		}
		if(jedisPool!=null&&!jedisPool.isClosed()){
			jedisPool.close();
		}
	}
	/**
	 * <p><b>Description:</b> 关闭JedisCluster连接</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年5月15日 上午3:10:53</p>
	 * @param jedisCluster
	 */
	public static void closeJedisCluster(JedisCluster jedisCluster){
		if(jedisCluster!=null){
			try {
				jedisCluster.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.error("Can not close Redis Cluster");
			}
		}
	}
}
