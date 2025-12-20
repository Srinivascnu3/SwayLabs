package stepdef;

import java.time.LocalDate;

import org.joda.time.LocalDateTime;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.ActivitiesPojo;
import com.google.gson.Gson;

public class ActivitiesApi {
	String infyBase = "https://fakerestapi.azurewebsites.net";
	Response response;
	RequestSpecification req;
	JsonPath jp ;
	ActivitiesPojo pojo;
	
	@Given("A list of Activities are available")
	public void a_list_of_activities_are_available() {
	    // set base URI and perform GET for activity 1
		RestAssured.baseURI = infyBase; 
		req = RestAssured.given();
		
	}

	@When("Get the Activities details")
	public void get_the_activities_details() {
		// print and assert title
		response = req.get("/api/v1/Activities/1");
		jp = response.jsonPath();
		pojo = req.get("/api/v1/Activities/1").as(ActivitiesPojo.class);
		System.out.println("title of id 1: "+jp.get("title"));
		Assert.assertEquals(jp.getString("title"), "Activity 1");
		System.out.println("title of id 1: "+pojo.getTitle());
		Assert.assertEquals(pojo.getTitle(), "Activity 1");
	}
	@When("Post the Activities details")
	public void post_the_activities_details() {
		LocalDateTime l = new LocalDateTime().now();
		
		String s = l.toString();
		pojo = new ActivitiesPojo (31,"Srini activity",s,true);
		// Serialize POJO to JSON and send with proper Content-Type to avoid 415 Unsupported Media Type
//		Gson gson = new Gson();
//		String jsonBody = gson.toJson(pojo);
//		response = req.contentType("application/json").body(jsonBody).post("/api/v1/Activities");
		req.header("Content-Type", "application/json");
		req.body(pojo);
		response = req.post("/api/v1/Activities");
		System.out.println("Post response check: "+response.getBody().asString());
	}

	@When("Put the Activities details")
	public void put_the_activities_details() {
		LocalDateTime l = new LocalDateTime().now();
		String s = l.toString();
		ActivitiesPojo pojo = new ActivitiesPojo(1,"Activity Srini","2025-12-19T04:20:55.4373722+00:00",true);
		req.header("Content-Type", "application/json");
		req.body(pojo);
		response = req.put("/api/v1/Activities/1");
		System.out.println("put response check: "+response.getBody().asString());
	}
	@When("Del the {string} Activities details")
	public void del_the_activities_details(String id) {
		 
		response = req.delete("/api/v1/Activities/"+id);
		
	}
	@Then("Validate status code  and status line")
	public void validate_status_code_and_status_line() {
		int status=response.getStatusCode();
		//Accept 200 (OK) or 201 (Created) for POST responses
		Assert.assertTrue(status == 200 || status == 201, "Expected status 200 or 201 but found " + status);
		System.out.println(response.getStatusLine());
	}
}