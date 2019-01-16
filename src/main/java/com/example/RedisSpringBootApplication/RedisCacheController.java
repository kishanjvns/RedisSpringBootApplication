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

/*@RestController
@RequestMapping("/sbcache")
public class RedisCacheController {
	@Autowired
	@Qualifier("terminalMap")
	Map<String, Object> terminalMap;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
		
	
	@PostMapping("{cacheName}/{key}")
	public String addToCache(@PathVariable("cacheName")String cacheName, HttpEntity<String> httpEntity,@PathVariable("key") String key) {	
		redisTemplate.opsForHash().put(cacheName, key,httpEntity.getBody());
		return httpEntity.getBody();
	}
	
	
	@RequestMapping(method= {RequestMethod.GET}, path="{cacheName}/{key}",produces="application/json")
	public ResponseEntity<String> getFromCache(@PathVariable("cacheName") String cacheName,@PathVariable("key")String key) throws JSONException{		
		String data=null;
		HttpStatus httpStatus=null;
		try {
			data= redisTemplate.opsForHash().get(cacheName, key).toString();
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
		try {
			data= redisTemplate.opsForHash().get(cacheName, key).toString();
			redisTemplate.opsForHash().delete(cacheName, key);
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
		redisTemplate.opsForHash().getOperations().expire(cacheName, miliSecond, TimeUnit.MILLISECONDS);
		return ResponseEntity.ok().build();
	}
}
*/