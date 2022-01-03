import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		
		//Print No of courses returned by API
		int Size = js.getInt("courses.size()");
		System.out.println("No of courses returned by API: "+Size);
		
		//Print Purchase Amount
		System.out.println("Purchase Amount: "+js.getInt("dashboard.purchaseAmount"));
		
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
			
	}

}
