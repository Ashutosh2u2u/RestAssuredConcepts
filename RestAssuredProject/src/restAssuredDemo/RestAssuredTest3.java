package restAssuredDemo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;//for accessing static pckzs
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import files.payload;

public class RestAssuredTest3 {
	//validate if Add Place API is working as expected
	//given()-All input details...>QueryParam<><header><><body>
	//when()-Submit the API.....>post<>
	//then()-validate the response...assertThat><statuscode>
	//add place—update place with new address -> get place to validate if new address is present in response
	public static void main(String[] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("server","Apache/2.4.18 (Ubuntu)")
				.extract().response().asString();
		System.out.println(response);

		JsonPath js=new JsonPath(response);//for parsing the json path
		String placeID= js.getString("place_id");//provide place-id path for getting place-id value
		System.out.println(placeID);

		//PUT for updating address
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\"70 Summer walk, USA\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
	}

}
