package com.restassured.dynamicJson;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;//for accessing static pckzs
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;

public class RestAssurParamatrize {	
	@Test(dataProvider="DataSet")
	//Addbook
	public static void dynamicJson(String isbn,String aisle) 
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all().header("Content-Type","application/json")
				.body(payload.AddBook(isbn,aisle)).when().post("Library/Addbook.php")
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

	@DataProvider(name="DataSet")
	public Object[][] paraDataAddBook()
	{
		return new Object[][] {{"as12as12","1234"},{"Pq12pq12","1234"},{"mn12mn12","1234"}};
		}

		}
