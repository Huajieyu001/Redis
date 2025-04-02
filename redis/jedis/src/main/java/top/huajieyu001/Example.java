package top.huajieyu001;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class Example {

    public static Jedis jedis;

    static {
        jedis = new Jedis("192.168.179.140", 6379);
        jedis.auth("123456");
        jedis.select(0);
    }

    public static void main(String[] args) {
        testHash();

        jedis.close();
    }

    public static void testString(){
        jedis.set("name", "HAHAHAH");
        String string = jedis.get("name");
        System.out.println(string);
    }

    public static void testHash(){
        jedis.hset("huajieyu:user", "age", "18");
        jedis.hset("huajieyu:user", "name", "Tom");

        String name = jedis.hget("huajieyu:user", "name");
        System.out.println(name);

        Map<String, String> map = jedis.hgetAll("huajieyu:user");
        map.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}
