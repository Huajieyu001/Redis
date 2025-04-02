import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class Test {

    @Autowired
    private RedisTemplate redisTemplate;

    @org.junit.jupiter.api.Test
    public void test1(){
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
