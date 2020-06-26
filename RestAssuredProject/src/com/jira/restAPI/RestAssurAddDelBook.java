package com.jira.restAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;//for accessing static pckzs
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;

public class RestAssurAddDelBook {	
	@Test
	//Addbook
	public static void loginSession() 
	{
		SessionFilter session= new SessionFilter();
		RestAssured.baseURI="http://localhost:8888";
		String response=given().filter(session).log().all().header("Content-Type","application/json")
				.body("").when().post("Library/Addbook.php")
				.then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println(response);
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.getString("ID");
		System.out.println(id);
	
	//Deletebook
	String DelResponse=given().log().all().header("Content-Type","application/json")
			.body(payload.DelBook("abcd1234112233")).when().post("Library/DeleteBook.php")
			.then().assertThat().statusCode(200)
			.extract().response().asString();
	        System.out.println(DelResponse);
}

}
