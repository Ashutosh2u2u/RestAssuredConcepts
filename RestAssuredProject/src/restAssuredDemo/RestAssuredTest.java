package restAssuredDemo;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;//for accessing static pckzs
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import files.payload;

public class RestAssuredTest {
	//validate if Add Place API is working as expected
	//given()-All input details...>QueryParam<><header><><body>
	//when()-Submit the API.....>post<>
	//then()-validate the response...assertThat><statuscode>

	public static void main(String[] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
        .body(payload.AddPlace()).when().post("maps/api/place/add/json")
        .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).header("server","Apache/2.4.18 (Ubuntu)");
       
	}

}
