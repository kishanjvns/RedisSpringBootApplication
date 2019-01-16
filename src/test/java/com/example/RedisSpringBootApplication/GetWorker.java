package com.example.RedisSpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

public class GetWorker implements Callable<Long> {
	private String value;
	 static long totalRecordCount=0;
	static StringBuffer buffer=new StringBuffer(String.format("%s \t %s \n", "name","eta"));
	public GetWorker() {
	}




	public GetWorker(String value) {
		super();
		this.value = value;
	}




	/*@Override
	public List<String> call() throws Exception {
		List<String> responseCount=new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			String url = "http://localhost:8090/jediscache/terminal/" + i;
			try {	
				RestTemplate restTemplate = new RestTemplate();
				String reponse = restTemplate.getForObject(url, String.class);
				responseCount.add(reponse);
				System.out.println(reponse);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return responseCount;
	}*/
	
	@Override
	public Long call() throws Exception {
		long beginTime = System.currentTimeMillis();
			try {	
				RestTemplate restTemplate = new RestTemplate();
				String reponse = restTemplate.getForObject(value, String.class);		
				System.out.println(reponse);
				totalRecordCount++;
			} catch (Exception e) {
				System.err.println(e);
				totalRecordCount--;
			}
			long endTime = System.currentTimeMillis();
			double totalTakenTime = endTime - beginTime;
			buffer.append(String.format("%s %f \n", Thread.currentThread().getName(),totalTakenTime));
		return totalRecordCount;
	}
	
	
}                                  