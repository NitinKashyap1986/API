package EPAM.APITEST;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.ResponseBody;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import java.util.List;

public class FunctionalTests {
	
	@Test(priority=1)
	public void TC_1() 
	// Verify that the end point for  {GET all} is accessible, up and running
	{
		int StatusCode= RestAssured.get("http://swapi.co/api/planets/").getStatusCode();
		Assert.assertEquals(StatusCode, 200);
	}
	
	@Test(priority=1)
	public void TC_2() 
	// Verify that the end point for {GET by id} is accessible, up and running
	{
		int StatusCode= RestAssured.get("http://swapi.co/api/planets/2").getStatusCode();
		Assert.assertEquals(StatusCode, 200);
	}
	
	@Test(priority=1)
	public void TC_3() 
	//Verify that data received using {GET all} is as expected
	//The assumption is that we already know that the name of first planet is Alderaan
	{
		String CompleteJSON= RestAssured.get("http://swapi.co/api/planets/").getBody().asString();
		JsonPath jsonPath = new JsonPath(CompleteJSON);
		String PlanetName = jsonPath.get("results.name[0]");
		Assert.assertEquals(PlanetName, "Alderaan");
	}
	
	@Test(priority=1)
	public void TC_4() 
	//Verify that data received using {GET by id} is as expected
	//The assumption is that we already know that the diameter of Tattoine is 10465 
	{
		String CompleteJSON= RestAssured.get("http://swapi.co/api/planets/1").getBody().asString();
		JsonPath jsonPath = new JsonPath(CompleteJSON);
		int Diameter = jsonPath.getInt("diameter");
		Assert.assertEquals(Diameter, 10465);
	}
	
	@Test(priority=2)
	public void TC_5() 
	//Validate that planet residents are valid api end points
	{
		String CompleteJSON= RestAssured.get("http://swapi.co/api/planets/1").getBody().asString();
		JsonPath jsonPath = new JsonPath(CompleteJSON);
		List<String> residents = jsonPath.getList("residents");
		int StatusCode= RestAssured.get(residents.get(0)).getStatusCode();
		Assert.assertEquals(StatusCode, 200);

	}
	
	@Test(priority=2)
	public void TC_6() 
	//Validate that planet films are valid api end points
	{
		String CompleteJSON= RestAssured.get("http://swapi.co/api/planets/1").getBody().asString();
		JsonPath jsonPath = new JsonPath(CompleteJSON);
		List<String> films = jsonPath.getList("films");
		int StatusCode= RestAssured.get(films.get(0)).getStatusCode();
		Assert.assertEquals(StatusCode, 200);
	}
	
	@Test(priority=3)
	public void TC_7() 
	//Validate that API is not accessible when accessed with incorrect id for{GET by id}
	{
		int StatusCode= RestAssured.get("http://swapi.co/api/planets/200").getStatusCode();
		Assert.assertEquals(StatusCode, 404);
	}
}

