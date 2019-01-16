package com.example.RedisSpringBootApplication;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;


@Configuration
@EnableConfigurationProperties
@EnableCaching
public class RedisCacheConfig {
	/*@Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.redis.port}")
    private int redisPort;*/
   
	/*@Bean
	JedisConnectionFactory jedisConnectionFactory() {			
		JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHostName);
        factory.setPort(redisPort);
        factory.setUsePool(true);	               
        return factory;
	}*/
		
	/*@Bean
	public JedisPool jedisPool() {				
		JedisPool jedisPool = new JedisPool("192.168.20.153", 6379);
		return jedisPool;
	}*/
	
	@Bean
	public JedisSentinelPool jedisSentinelPool() {
		Set<String> sentinels=new HashSet<>();	
		sentinels.add("192.168.20.40:26379");
		sentinels.add("192.168.20.55:26379");
		JedisSentinelPool jedisSentinelPool=new JedisSentinelPool("mymaster", sentinels);
		return jedisSentinelPool;
	}
	
		
/*	@Bean("redisTemplate")
	public RedisTemplate<String, Object>  redisTemplate(){		
		RedisTemplate<String, Object> rd= new RedisTemplate<>();
		rd.setKeySerializer(new StringRedisSerializer());
		rd.setValueSerializer(new StringRedisSerializer());
		
		rd.setConnectionFactory(jedisConnectionFactory());
		rd.afterPropertiesSet(); 		
		return rd;
	}*/
	
	/*@Bean
	public HashOperations<String, Object, Object> gethashOperations(){
		return redisTemplate().opsForHash();
	}*/
	

}
