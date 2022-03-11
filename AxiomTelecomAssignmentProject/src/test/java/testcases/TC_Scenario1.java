package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dataProviders.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC_Scenario1 {
	
	ConfigFileReader configFileReader;
	RequestSpecification request;
	Response response;
	JsonPath jsonPathEvaluator;
	
	String resbody;
	int i, profile_image_size;
	
	@BeforeTest
	public void preRequisite() {
		configFileReader = new ConfigFileReader();
		RestAssured.baseURI = configFileReader.getBaseURI();
		request = RestAssured.given();
	}
	
	@Test
	public void testApiLinkFromConfigFile() {
		Response response = request.request(Method.GET, configFileReader.getEmployeeEndpoint());
		String resbody = response.getBody().asString();
		System.out.println(resbody);
		Assert.assertEquals(response.getStatusCode(), 200);
		JsonPath path = new JsonPath(resbody);
		profile_image_size = path.getInt("data.size()");
		for(i=0; i<profile_image_size; i++) {
			Assert.assertEquals(path.getString("data["+i+"].profile_image"), "", "Actual profile_image is " + path.getString("data["+i+"].profile_image"));
		}
	}
}

