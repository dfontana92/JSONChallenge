import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;


public class JSONChallenge {

	public static void main(String[] args) throws FileNotFoundException {
		
		//Create JSON Reader
		JsonReader myReader = Json.createReader(new BufferedReader(new FileReader(new File(".\\src\\test.txt"))));
		
		//Read and Parse the Object
		JsonObject input = myReader.readObject();
		
		
		Iterator<String> keyList = input.keySet().iterator();
		
		
		
		String a;
		JsonValue b;
		while(keyList.hasNext()) {
			a = keyList.next();
			b = input.get(a);
			
			System.out.println(b.getValueType().toString());
			
			if(b.getValueType() == JsonValue.ValueType.ARRAY)
			{
				JsonArray c = (JsonArray)b;
				for(int i = 0; i < c.size(); i++)
				{
					System.out.println("-- " + c.get(i).toString().equals("\"dailyprogrammer\""));
				}
			}
				
			
			//System.out.println(a + " : " + input.get(a));
		
			
		}
	}
}
