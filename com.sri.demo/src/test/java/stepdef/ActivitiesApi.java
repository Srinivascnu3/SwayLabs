package stepdef;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.ActivitiesPojo;

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

	@Then("Validate status code  and status line")
	public void validate_status_code_and_status_line() {
		int status=response.getStatusCode();
		Assert.assertEquals(status, 200);
		System.out.println(response.getStatusLine());
	}
}