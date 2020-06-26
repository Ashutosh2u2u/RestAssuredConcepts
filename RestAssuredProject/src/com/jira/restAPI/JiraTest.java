package com.jira.restAPI;

import static io.restassured.RestAssured.*;
import java.io.File;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest
{
	public static void main(String[] args) 
	{

		RestAssured.baseURI="http://localhost:8888";

		//Login Scenario
		SessionFilter session=new SessionFilter();
		String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{\r\n" +
				"    \"username\": \"AshuHiman\",\r\n" +
				"    \"password\": \"Golu@2004\"\r\n" +
				"}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		//create issue
		String addissueResponse = given().log().all()
				.header("Content-Type","application/json")
				.body("{\r\n" + 
						"    \"fields\": {\r\n" + 
						"        \"project\": \r\n" + 
						"            {\r\n" + 
						"\"key\": \"MYL100\"\r\n" + 
						"},\r\n" + 
						"                \"summary\": \"Debit card Details\",\r\n" + 
						"                    \"description\": \"this is my 1st bug\",\r\n" + 
						"                    \"issuetype\": {\r\n" + 
						"\"name\": \"Bug\"\r\n" + 
						"}\r\n" + 
						"               }\r\n" + 
						"}\r\n" + 
						"").filter(session).when().post("/rest/api/2/issue").then().log().all()
				.assertThat().statusCode(201).extract().response().asString();

		
		String expectedMessage ="Hi How are you?";

		//Add comment
		String addCommentResponse = given().pathParam("key", "10000").log().all()
				.header("Content-Type","application/json").body("{\r\n" +
						"    \"body\": \""+expectedMessage+"\",\r\n" +
						"    \"visibility\": {\r\n" +
						"        \"type\": \"role\",\r\n" +
						"        \"value\": \"Administrators\"\r\n" +
						"    }\r\n" +
						"}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all()
				.assertThat().statusCode(201).extract().response().asString();

		JsonPath js=new JsonPath(addCommentResponse);
		String commentId= js.getString("id");

		//Add Attachment

		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10000")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("jira.txt")).when().
		post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

		//Get Issue

		String issueDetails=given().filter(session).pathParam("key", "10000")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{key}").then()
				.log().all().extract().response().asString();
		System.out.println(issueDetails);

		JsonPath js1 =new JsonPath(issueDetails);
		int commentsCount=js1.getInt("fields.comment.comments.size()");

		for(int i=0;i<commentsCount;i++)
		{
			String commentIdIssue =js1.get("fields.comment.comments["+i+"].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId))
			{
				String message= js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
	}



}