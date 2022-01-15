import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReUseableMethods;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//given().when().then
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String ResponseOfAPI = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body(Payload.AddPlace("phone_number1","address1")).
		when().post("maps/api/place/add/json").
		then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").
		extract().response().asString();
		
		
		
		System.out.println("\nResponseOfAPI:\n"+ResponseOfAPI);
		
		//JsonPath js=new JsonPath(ResponseOfAPI)
		JsonPath js=ReUseableMethods.RawToJson(ResponseOfAPI);
		String place_id = js.getString("place_id");
		
		System.out.println("\nplace_id:\n"+place_id);
		
		//Update the Place
		given().
		log().all().
		queryParam("key", "qaclick123").
		header("Content-Type","application/json").
		body(Payload.UpdatePlace(place_id)).
		when().
		put("maps/api/place/update/json").
		then().
		assertThat().
		log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//GET place and check address = "70 winter walk, USA"
		String GETResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id).
		when().get("maps/api/place/get/json").
		then().assertThat().log().all().statusCode(200).body("address", equalTo("70 winter walk, USA")).
		extract().response().asString();
		
		JsonPath js1=ReUseableMethods.RawToJson(GETResponse);
		String Address = js1.getString("address");
		System.out.println("\nAddress:\n"+Address);
		
		Assert.assertEquals(Address, "70 winter walk, USA");

	}

}
