package com.restassured.dynamicJson;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;//for accessing static pckzs
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;

public class RestAssurStaticJson {	
	@Test
	//Addbook
	public static void StaticJson() throws IOException 
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all().header("Content-Type","application/json")
				.body(GenerateStringFromResourse("D:\\Study Related\\Udemy_Rest_Assured\\AddBookDetails.json")).when().post("Library/Addbook.php")
				.then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println(response);
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.getString("ID");
		System.out.println(id);

		//Deletebook
		String DelResponse=given().log().all().header("Content-Type","application/json")
				.body(payload.DelBook(id)).when().post("Library/DeleteBook.php")
				.then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println(DelResponse);
	}
	
	//to fatch complete data bytes from .json static file from directory
	public static String GenerateStringFromResourse(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
	}



}
