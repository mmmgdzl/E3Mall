package jedis;

import com.mmmgdzl.common.jedis.JedisClient;
import com.mmmgdzl.common.jedis.JedisClientPool;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    @Test
    public void testJedis() throws Exception {
        //创建连接对象 参数ip port
        Jedis jedis = new Jedis("192.168.25.131", 7001);
        //直接使用jedis操作redis  每个操作对应一个方法
        jedis.set("b", "456");
        String b = jedis.get("b");
        System.out.println(b);
        //关闭连接
        jedis.close();
    }

    @Test
    public void test2() throws Exception {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.131", 7001));
        nodes.add(new HostAndPort("192.168.25.131", 7002));
        nodes.add(new HostAndPort("192.168.25.131", 7003));
        nodes.add(new HostAndPort("192.168.25.131", 7004));
        nodes.add(new HostAndPort("192.168.25.131", 7005));
        nodes.add(new HostAndPort("192.168.25.131", 7006));

        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("c", "gg");
        System.out.println(jedisCluster.get("b"));

    }

    @Test
    public void test3() throws  Exception {
        //初始化spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        //从容器中获取jedisclient对象
        JedisClient jedisClient = ac.getBean(JedisClient.class);
        jedisClient.set("d", "gogo");
        System.out.println(jedisClient.get("d"));
    }

}
