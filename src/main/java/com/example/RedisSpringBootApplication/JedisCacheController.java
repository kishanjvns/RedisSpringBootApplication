package com.example.RedisSpringBootApplication;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

@RestController
@RequestMapping("/jediscache")
public class JedisCacheController {

	
	/*@Autowired
	JedisPool jedisPool;*/
	
	@Autowired
	JedisSentinelPool jedisPool;
		
	
	@PostMapping("{cacheName}/{key}")
	public String addToCache(@PathVariable("cacheName")String cacheName, HttpEntity<String> httpEntity,@PathVariable("key") String key) {	
		try(Jedis jedis=jedisPool.getResource();){
			  jedis.hset(cacheName.getBytes(), key.getBytes(), httpEntity.getBody().getBytes());
		}
		//redisTemplate.opsForHash().put(cacheName, key,httpEntity.getBody());
		return httpEntity.getBody();
	}
	
	
	@RequestMapping(method= {RequestMethod.GET}, path="{cacheName}/{key}",produces="application/json")
	public ResponseEntity<String> getFromCache(@PathVariable("cacheName") String cacheName,@PathVariable("key")String key) throws JSONException{		
		String data=null;
		HttpStatus httpStatus=null;
		try (Jedis jedis=jedisPool.getResource()){
			byte[] response= jedis.hget(cacheName.getBytes(), key.getBytes());
			data=new String(response);
			httpStatus=httpStatus.OK; 
		} catch (Exception e) {
			httpStatus=HttpStatus.BAD_REQUEST;			
			data=String.format("element not found for given key %s under cache %s", key,cacheName);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", data);
			data=jsonObject.toString();
		}
		return new ResponseEntity<String>(data, httpStatus);
	}
	
	
	@RequestMapping(method= {RequestMethod.GET}, path="/getandexpire/{cacheName}/{key}",produces="application/json")
	public ResponseEntity<String> getFromCacheandExpire(@PathVariable("cacheName") String cacheName,@PathVariable("key")String key) throws JSONException{		
		String data=null;
		HttpStatus httpStatus=null;
		try (Jedis jedis=jedisPool.getResource()){
			byte[] response= jedis.hget(cacheName.getBytes(), key.getBytes());
			long isDeleted= jedis.hdel(cacheName.getBytes(), key.getBytes());
			//TODO log isDeleted
			httpStatus=httpStatus.OK; 
		} catch (Exception e) {
			httpStatus=HttpStatus.BAD_REQUEST;
			data=String.format("element not found for given key %s under cache %s", key,cacheName);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", data);
			data=jsonObject.toString();
		}
		return new ResponseEntity<String>(data, httpStatus);
	}
	
	
	@PostMapping("/expire/{cacheName}/{time}")
	public ResponseEntity<String> expireCache(@PathVariable("cacheName")String cacheName,@PathVariable("time")long miliSecond) {
		try (Jedis jedis=jedisPool.getResource()){
			jedis.del(cacheName.getBytes());
		}
		//redisTemplate.opsForHash().getOperations().expire(cacheName, miliSecond, TimeUnit.MILLISECONDS);
		return ResponseEntity.ok().build();
	}
}
