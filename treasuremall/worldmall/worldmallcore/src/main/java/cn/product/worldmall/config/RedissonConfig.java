package cn.product.worldmall.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * redisson通用化配置
 **/
//@Configuration
public class RedissonConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RedissonClient redissonClient(){//单机节点配置（一般够用）
        Config config=new Config();
        config.useSingleServer()
                .setAddress(environment.getProperty("redis.config.host"))
                .setPassword(environment.getProperty("spring.redis.password"));
        RedissonClient client=Redisson.create(config);
        return client;
    }


}