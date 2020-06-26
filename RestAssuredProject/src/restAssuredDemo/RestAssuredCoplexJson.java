package restAssuredDemo;

import files.payload;
import io.restassured.path.json.JsonPath;

public class RestAssuredCoplexJson {

	public static void main(String[] args) {
		
		JsonPath js=new JsonPath(payload.complexJSON());
		//1. Print No of courses returned by API
		int count=js.getInt("courses.size()");//size() to get array item count
		System.out.println(count);
		
		//2.Print Purchase Amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//3. Print Title of the first course
		String title1=js.getString("courses[0].title");
		System.out.println(title1);
		
		//4. Print All course titles and their respective Prices
		for(int i=0;i<count;i++)
		{
		System.out.println("course is " +js.getString("courses["+i+"].title")); //pass the variable in concatenate fashion
		System.out.println("price is " +js.getInt("courses["+i+"].price"));
		}

		//5. Print no of copies sold by RPA Course
		for(int j=0;j<count;j++)
		{
		String courseTitle=js.getString("courses["+j+"].title"); //pass the variable in concatenate fashion
		if(courseTitle.equalsIgnoreCase("RPA"))
		{
		System.out.println("copies are " +js.getInt("courses["+j+"].copies"));
		break;
		}
		}
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int sum=0;
		for(int j=0;j<count;j++)
		{
		int price=js.getInt("courses["+j+"].price"); //pass the variable in concatenate fashion
		int copies=js.getInt("courses["+j+"].copies");
		int amount=price*copies;
		sum=sum+amount;		
		}
		System.out.println(sum);
	}

}
