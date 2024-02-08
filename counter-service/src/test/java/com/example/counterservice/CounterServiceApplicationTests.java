package com.example.counterservice;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class CounterServiceApplicationTests {

	@Test
	void contextLoads() {
		String baseUrl = "http://localhost:8080/api/score/";
		HttpClient httpClient = HttpClient.newHttpClient();

		//GET
		URI getUri = URI.create(baseUrl + "scores");
		HttpRequest getRequest = HttpRequest.newBuilder()
			.uri(getUri)
			.GET()
			.build();
		
		//PUT
		URI putUri = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.path("scores")
			.queryParam("wins", 5)
			.queryParam("losses", 13)
			.queryParam("ties", 4)
			.build()
			.toUri();
		HttpRequest putRequest = HttpRequest.newBuilder()
			.uri(putUri)
			.PUT(HttpRequest.BodyPublishers.noBody())
			.build();

		//POST
		URI postUri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("wins").build().toUri();
		HttpRequest postRequest = HttpRequest.newBuilder()
			.uri(postUri)
			.POST(HttpRequest.BodyPublishers.noBody())
			.build();

		try {
			
			//GET test
			HttpResponse<String> response = httpClient.send(getRequest, BodyHandlers.ofString());
			System.out.println("Initial Get Test Restult: " + response.body());
			
			//PUT test
			response = httpClient.send(putRequest, BodyHandlers.ofString());
			System.out.println("PUT Test Restult: " + response.body());
			response = httpClient.send(getRequest, BodyHandlers.ofString());
			System.out.println("Get Test Restult after PUT Test: " + response.body());

			//POST test
			response = httpClient.send(postRequest, BodyHandlers.ofString());
			System.out.println("POST Test Restult: " + response.body());
			response = httpClient.send(getRequest, BodyHandlers.ofString());
			System.out.println("Get Test Restult after POST Test: " + response.body());
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
