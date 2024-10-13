package com.ss.aws.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainControllerTest {
	
	@Autowired
	private TestRestTemplate rest;
	

	@Test
	public void test_main() {
 
		ResponseEntity<String> resp = 
				rest.getForEntity("/aws/v1",
						       String.class);
		// 실행했을 때 리턴 값이 맞니?
		// 결과값이 다르면 test에서는 에러가 발생!
		assertEquals("<h1> aws v1</h1>",
						resp.getBody());
	}
}
