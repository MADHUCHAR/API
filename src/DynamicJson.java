import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReUseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	
	@Test(dataProvider="MapsData")
	public void addBook(String phone_number, String address)
	{
		//given().when().then
				RestAssured.baseURI = "https://rahulshettyacademy.com";
				
				String ResponseOfAPI = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
				body(Payload.AddPlace(phone_number,address)).
				when().post("maps/api/place/add/json").
				then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").
				extract().response().asString();
				
				System.out.println("\nResponseOfAPI:\n"+ResponseOfAPI);
				
				//JsonPath js=new JsonPath(ResponseOfAPI)
				JsonPath js=ReUseableMethods.RawToJson(ResponseOfAPI);
				String place_id = js.getString("place_id");
				
				System.out.println("\nplace_id:\n"+place_id);
	}
	@DataProvider(name="MapsData")
	public Object[][] getData()
	{
		return new Object[][] {{"phone_number1","address1"},{"phone_number2","address2"}, {"phone_number3","address3"}};
	}
	
}
