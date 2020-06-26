package com.OAuth2;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import files.Api;
import files.POJOtest;
import files.WebAutomation;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
//import pojo.Api;
//import pojo.GetCourse;

public class PojoOAuth2Test2 {

	public static void main(String[] args) throws InterruptedException 
	{
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"}; //use for assertion
		//To get code hit 
		//url :https://accounts.google.com/signin/oauth/oauthchooseaccount?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verifyfjdss&o2v=2&as=-Jw861s7O4ugcK8hH_Ljqw&flowName=GeneralOAuthFlow
		//String url ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
		String url="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AGq87tSpLIyjej7RiDDE7vpsYQDzr0GoXLjTI_3ynHVcAmf71P8tHWEn6tUI_OP_tpdn2j3kAH3RvywTkUcUJ4&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=1&prompt=none#";
		String partialcode=url.split("code=")[1];//split url in two array , we are interested in 2nd
		String code=partialcode.split("&scope")[0];//split string in two array ,we are interested in 1st 
		System.out.println(code);
		//For getting access token
		String response =
				given() 
				.urlEncodingEnabled(false)
				.queryParams("code",code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code")
				.queryParams("state", "verifyfjdss")
				.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
				// .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		// System.out.println(response);
		//For accessing courses from given secure url
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);

		//get object for pojo class
		POJOtest pj=given().
				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.as(POJOtest.class);
	System.out.println(	pj.getExpertise());
		System.out.println(pj.getInstructor());
		System.out.println(pj.getLinkedIn());
		System.out.println(pj.getServices());
		System.out.println(pj.getUrl());
		System.out.println(pj.getCourses().getWebAutomation().get(0).getCourseTitle());
		//System.out.println(r2);
		
		List<WebAutomation> webAuto=pj.getCourses().getWebAutomation();
		for(int i=0;i<webAuto.size();i++) {
			if(webAuto.get(i).getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java"))
			{
				System.out.println(webAuto.get(i).getPrice());
			}
		}
		

		List<Api> apiCourses=pj.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					{
				System.out.println(apiCourses.get(i).getPrice());
					}
		}
		
		//Get the course names of WebAutomation
		ArrayList<String> a= new ArrayList<String>();
		
		
		List<WebAutomation> w=pj.getCourses().getWebAutomation();
		
		for(int j=0;j<w.size();j++)
		{
			a.add(w.get(j).getCourseTitle());
		}
		
		List<String> expectedList=	Arrays.asList(courseTitles);
		
		Assert.assertTrue(a.equals(expectedList));;
	}
}




