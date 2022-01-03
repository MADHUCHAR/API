package files;

import io.restassured.path.json.JsonPath;

public class ReUseableMethods {
	
	public static JsonPath RawToJson(String response)
	{
		JsonPath js1=new JsonPath(response); 
		return js1;
	}

}
