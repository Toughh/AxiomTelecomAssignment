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
import utility.ExcelUtils;

import java.util.regex.Pattern;

public class TC_Scenario2 {
	ConfigFileReader configFileReader;
	ExcelUtils excel;
	RequestSpecification request;
	Response response;
	JsonPath jsonPathEvaluator;
	Pattern patternMatch;
	
	String datapath = System.getProperty("user.dir");
	String excelPath = datapath + "\\src\\test\\java\\data\\employeeData.xlsx";
	String sheetName = "Employee";
	String resbody, message;
	String expectedMessage = "Successfully! Record has been fetched.";
	
	@BeforeTest
	public void preRequisite() {
		excel = new ExcelUtils(excelPath, sheetName);
		configFileReader = new ConfigFileReader();
		RestAssured.baseURI = configFileReader.getBaseURI();
		request = RestAssured.given();
		patternMatch = Pattern.compile("\\{\"status\":\"success\",\"data\":\\{\"id\":1,\"employee_name\":\"Tiger Nixon\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"\\},\"message\":\"Successfully! Record has been fetched\\.\"\\}", Pattern.CASE_INSENSITIVE);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRequestDataFromExternalFile() {
		response = request.request(Method.GET, configFileReader.getSingleEmployeeEndpoint() + '/' + excel.readCellData(1, 0));
		resbody = response.getBody().asString();
		System.out.println(resbody);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		jsonPathEvaluator = response.jsonPath();
		message = jsonPathEvaluator.get("message");
		Assert.assertEquals(message, expectedMessage, "Actual message is: " + message);
//		Assert.assertTrue(Pattern.matches(patternMatch, resbody));
	}
}
 