package com.example.RedisSpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PostWorker implements Callable<Long> {	
	private String url;
	private String value;
	static long totalRecordCount=0;
	public PostWorker(String url, String value) {		
		this.url = url;
		this.value = value;
	}

	public PostWorker() {
	}

	/*@Override
	public List<String> call() throws Exception {
		List<String> responseCount=new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			String postUrl = "http://localhost:8090/jediscache/terminal/" + i;
			String value = "Hello" + i;
			try {
				HttpEntity<String> entity = new HttpEntity<String>("name" + value);				
				ResponseEntity<String> request = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class);
				responseCount.add(request.getBody());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return responseCount;
	}*/
	
	@Override
	public Long call() throws Exception {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> entity = new HttpEntity<String>(String.format("{name: %s}", value));
			ResponseEntity<String> request = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			totalRecordCount++;
		} catch (Exception e) {
			System.out.println(e);
			totalRecordCount--;
		}
		return totalRecordCount;
	}		
}
