package top.huajieyu001.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolFactory {

    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(8);
        config.setMaxIdle(8);
        config.setMinIdle(0);
        config.setMaxWaitMillis(200);

        jedisPool = new JedisPool(config, "192.168.179.140", 6379, 1000, "123456");
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
