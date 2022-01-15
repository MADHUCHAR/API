import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SessionFilter session = new SessionFilter();
		
		RestAssured.baseURI= "http://localhost:8080";
		
		String response = given().
		header("Content-Type","application/json").
		body("{ \"username\": \"memadhucharcse\", \"password\": \"Madhu@0544\" }").
		log().all().filter(session).
		when().
		post("/rest/auth/1/session").
		then().extract().response().asString();
		
		System.out.println("\nresponse:\n"+response);
		System.out.println("\session:\n"+session.getSessionId());
		
		//ADD COMMENT
		String addCommentResponse = given().pathParam("key", "10006").log().all().
		header("Content-Type","application/json").
		body("{\r\n"
				+ "    \"body\": \"Body\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).
		when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js=new JsonPath(addCommentResponse);

		String commentId= js.getString("id");
		String expectedMessage ="Body";		
		//Get Issue

		String issueDetails=given().filter(session).pathParam("key", "10006")
		.queryParam("fields", "comment")
		.log().all().when().get("/rest/api/2/issue/{key}").then()
		.log().all().extract().response().asString();

		System.out.println(issueDetails);
		JsonPath js1 =new JsonPath(issueDetails);
		int commentsCount=js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentsCount;i++) {
			String commentIdIssue =js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId)) {
				String message= js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println("message: "+message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
		//Add Attachment
				/*There is no permission to add an attachment*/
				given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10006")
				.header("Content-Type","multipart/form-data")
				.multiPart("file",new File("pom.xml")).when().
				post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
	}
}

