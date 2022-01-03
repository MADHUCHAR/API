import org.testng.Assert;
import org.testng.annotations.Test;
import files.Payload;
import io.restassured.path.json.JsonPath;
public class SumValidation {
	@Test
	public void SumValidationCheck()
	{
		JsonPath js = new JsonPath(Payload.CoursePrice());
		//Print No of courses returned by API
		int Size = js.getInt("courses.size()");
		System.out.println("No of courses returned by API: "+Size);
		//Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: "+purchaseAmount);
		//Print Title of the first course
		System.out.println("Title of the first course: "+js.getString("courses[0].title"));
		//Print All course titles and their respective Prices
		for(int i=0;i<Size;i++)
		{
			System.out.println("course title: "+js.getString("courses["+i+"].title"));
			System.out.println("course Prices: "+js.getInt("courses["+i+"].price"));
		}
		//Print no of copies sold by RPA Course
		for(int i=0;i<Size;i++)
			if(js.getString("courses["+i+"].title").equals("RPA"))
			{
				System.out.println("Print no of copies sold by RPA Course: "+js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies"));
				break;
			}
		//Verify if Sum of all Course prices matches with Purchase Amount
		int ActualAmount=0;
		for(int i=0;i<Size;i++)
			ActualAmount+= js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies");
			
		System.out.println("ActualAmount : "+ActualAmount);
		Assert.assertEquals(ActualAmount, purchaseAmount);
	}
}
