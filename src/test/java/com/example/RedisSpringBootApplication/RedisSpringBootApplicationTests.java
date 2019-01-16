package com.example.RedisSpringBootApplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSpringBootApplicationTests {

	@Test
	public void redisPost() throws InterruptedException, ExecutionException {
		System.err.println("executing post test");
		long beginTime = System.currentTimeMillis();
		ThreadPoolTaskExecutor postPool = new ThreadPoolTaskExecutor();
		postPool.setCorePoolSize(5);
		postPool.setMaxPoolSize(10);
		postPool.setWaitForTasksToCompleteOnShutdown(true);
		postPool.initialize();
		Future<Long> responseCount = null;
		for (int i = 0; i < 50000; i++) {
			String postUrl = "http://localhost:8090/jediscache/terminal/" + i;
			String value = "Hello" + i;
			PostWorker postWorker = new PostWorker(postUrl, value);
			responseCount = postPool.submit(postWorker);
		}
		System.out.println("Total record inseted " + responseCount.get());
		long endTime = System.currentTimeMillis();
		double totalTakenTime = endTime - beginTime;
		System.err.println("total time taken in second " + (totalTakenTime / 1000));
		System.err.println("exiting post test");
	}
	
	@Test
	public void redistake() throws InterruptedException, ExecutionException, IOException {
		System.err.println("executing get test");
		long beginTime = System.currentTimeMillis();
		ThreadPoolTaskExecutor getPool = new ThreadPoolTaskExecutor();
		getPool.setCorePoolSize(5);
		getPool.setMaxPoolSize(10);
		getPool.setWaitForTasksToCompleteOnShutdown(true);
		getPool.initialize();
		Future<Long> count = null;
		for (int i = 0; i < 50000; i++) {
			//System.err.println(Thread.currentThread().getName());
			String url = "http://localhost:8090/jediscache/terminal/" + i;
			GetWorker getWorker = new GetWorker(url);
			count = getPool.submit(getWorker);
			
		}
		System.out.println(count.get());
		long endTime = System.currentTimeMillis();
		double totalTakenTime = endTime - beginTime;
		double avgTakenTIme=totalTakenTime/50000;
		BufferedWriter writer=new BufferedWriter(new FileWriter("logtime.txt"));
		writer.write(GetWorker.buffer.toString());
		System.err.println("total time taken in second " + (totalTakenTime / 1000));
		System.err.println("average taken time "+avgTakenTIme);
		System.err.println("exiting get test");
	}

}
