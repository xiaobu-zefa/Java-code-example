package site.xiaobu.example.reidscluster;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisOperationDemoBean {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void operateAndShowInfo() {
        System.out.println("1. 设置一个key");
        stringRedisTemplate.opsForValue().set("RedisOperationDemoBeanStringKey", StrUtil.toString("当前时间:" + System.currentTimeMillis()));
        System.out.println("2. 获取设置的值");
        String redisOperationDemoBeanStringValue = stringRedisTemplate.opsForValue().get("RedisOperationDemoBeanStringKey");
        System.out.println("值为:[" + redisOperationDemoBeanStringValue + "]");
    }
}
