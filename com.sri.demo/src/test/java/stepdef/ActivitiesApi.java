package stepdef;

import org.joda.time.LocalDateTime;
import org.testng.Assert;

import base.BaseTest;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.ActivitiesPojo;
import static org.hamcrest.Matchers.*;

public class ActivitiesApi extends BaseTest  {
	Response response;
	JsonPath jp ;
	ActivitiesPojo pojo;
	private Scenario scenario;

    @io.cucumber.java.Before
    public void before(Scenario scenario){
        this.scenario = scenario;
    }
	
	@Given("A list of Activities are available")
	public void a_list_of_activities_are_available() {
	    // set base URI and perform GET for activity 1
		log("Base URI ready");
		scenario.log("Base URI Ready");
		
	}

	@When("Get the Activities details of {string} and {string}")
	public void get_the_activities_details(String id,String title) {
		
		response = getRequest().get("/api/v1/Activities/"+id);
		//try using jsonPath
		jp = response.jsonPath();
		System.out.println("title of id : "+jp.get("title"));
		Assert.assertEquals(jp.getString("title"),title );
		
		//same action using pojo class
		pojo = getRequest().get("/api/v1/Activities/"+id).as(ActivitiesPojo.class);
		System.out.println("title of id "+ id+" : "+pojo.getTitle());
		Assert.assertEquals(pojo.getTitle(), title);
		scenario.log("Response:\n" + response.asPrettyString());
		
		//same action using when and then
		response = getRequest().given().when().get("/api/v1/Activities/"+id);
		response.then().body("title", equalTo(title));
		scenario.log("Response:\n" + response.asPrettyString());
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
		getRequest().body(pojo);
		response = getRequest().post("/api/v1/Activities");
		System.out.println("Post response check: "+response.getBody().asString());
	}

	@When("Put the Activities details")
	public void put_the_activities_details() {
		LocalDateTime l = new LocalDateTime().now();
		String s = l.toString();
		ActivitiesPojo pojo = new ActivitiesPojo(1,"Activity Srini","2025-12-19T04:20:55.4373722+00:00",true);
		getRequest().header("Content-Type", "application/json");
		getRequest().body(pojo);
		response = getRequest().put("/api/v1/Activities/1");
		System.out.println("put response check: "+response.getBody().asString());
	}
	@When("Del the {string} Activities details")
	public void del_the_activities_details(String id) {
		 
		response = getRequest().delete("/api/v1/Activities/"+id);
		
	}
	@Then("Validate status code  and status line")
	public void validate_status_code_and_status_line() {
		int status=response.getStatusCode();
		//Accept 200 (OK) or 201 (Created) for POST responses
		Assert.assertTrue(status == 200 || status == 201, "Expected status 200 or 201 but found " + status);
		System.out.println(response.getStatusLine());
	}
}