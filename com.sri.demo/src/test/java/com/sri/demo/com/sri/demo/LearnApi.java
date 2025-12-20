package com.sri.demo.com.sri.demo;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.ActivitiesPojo;


public class LearnApi {
	String baseUrl;
	String infyBase;
	Response response;
	
  @Test
	public void getApi() {
		// response = RestAssured.get(baseUrl+"objects/4");
		response = RestAssured.get(infyBase + "/api/v1/Activities");

		String res = response.getBody().toString();
		System.out.println(res);

		String contentType = response.getContentType();
		System.out.println("Content-Type value: " + contentType);

		int status = response.getStatusCode();
		System.out.println("get status code:" + status);
		Assert.assertEquals(200, status);
		
		// Get all the headers, return value is of type Headers.
		Headers allHeaders = response.getHeaders();
		// Headers class implements Iterable interface.
		// Iterate over all the Headers using an advance for loop as shown in the code
		// below
		for (Header header : allHeaders) {
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
		}
		
		String head= response.header("Server");
		System.out.println(head);
		
		JsonPath path = response.jsonPath();
		
		List<Object> names = path.getList("");
		for(Object name:names) {
	          System.out.println(name);
	      }    
		//{id=1, title=Activity 1, dueDate=2025-12-19T04:20:55.4373722+00:00, completed=false}
		
		
		
		
		
	}

	@Test(enabled = true)
	public void postAPI() {
		
		RestAssured.baseURI = infyBase; // use base host and post to the explicit path
		RequestSpecification req = RestAssured.given();

		req.header("Content-Type", "application/json");

		JSONObject reqParam = new JSONObject();

		// Use proper JSON types: id as number, completed as boolean
		reqParam.put("id", 33);
		reqParam.put("title", "Srini Activity");
		reqParam.put("dueDate", "2025-12-22T09:57:16.6203154+00:00");
		reqParam.put("completed", false);
		
	
		// Print the request body for debugging
		System.out.println("Request JSON: " + reqParam.toString());
		
		req.body(reqParam.toString());
		
		// Post to the explicit resource path
		 response = req.post("/api/v1/Activities");
		
		int statusCode = response.getStatusCode();
		String statusLine = response.getStatusLine();
		String respBody = response.getBody().asString();
		System.out.println("Status code: " + statusCode);
		System.out.println("Status line: " + statusLine);
		System.out.println("Response body: " + respBody);
		
		// Helpful checks: 201 Created is common for successful POSTs
		if (statusCode >= 200 && statusCode < 300) {
			System.out.println("POST succeeded");
		} else {
			System.out.println("POST failed - check request body and API contract");
		}

	}
	
	@Test(priority=3)
	public void delApi() {
		RestAssured.baseURI = infyBase; // use base host and post to the explicit path
		RequestSpecification req = RestAssured.given();
		//delete id 30 for testing
		response = req.delete("/api/v1/Activities/30");
		int statusCode = response.getStatusCode();
		System.out.println("delete Status code: "+ statusCode);
		
	}
	@Test(priority=0)
	public void putApi() {
		RestAssured.baseURI = infyBase; // use base host and post to the explicit path
		RequestSpecification req = RestAssured.given();
		req.header("Content-Type", "application/json");
		//{id=1, title=Activity 1, dueDate=2025-12-19T04:20:55.4373722+00:00, completed=false}
		ActivitiesPojo pojo = new ActivitiesPojo(1,"Activity Srini","2025-12-19T04:20:55.4373722+00:00",true);
		req.body(pojo);
		response = req.put("/api/v1/Activities/1");
		int status = response.getStatusCode();
		Assert.assertEquals(200, status);
		System.out.println("put status code: "+status);
	}
  @BeforeClass
  public void beforeClass() {
	   baseUrl="https://api.restful-api.dev/";  
	   infyBase = "https://fakerestapi.azurewebsites.net";
  }

  @AfterClass
  public void afterClass() {
  }

}