import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;


public class JSONChallenge {

	private static String target = "\"dailyprogrammer\"";
	private static String path = "root";
	
	private static final long MEGABYTE = 1024 * 1024;
	private static Runtime runtime = Runtime.getRuntime();
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//Create JSON Reader pointed at target file, then read and parse
			JsonReader myReader = Json.createReader(new BufferedReader(new FileReader(new File("." + File.separator + "src" + File.separator + "challenge2.json"))));
			JsonObject input = myReader.readObject();
		
		//Diagnostic timer
			long startTime = System.currentTimeMillis();
		
		//Initial method call, recursion begins
			boolean found = traverseObject(input);
			
		//Final Output
			if(found)
				System.out.println(path);
			else
				System.out.println("Value not found");
			
		//Diagnostic section
			long stopTime = System.currentTimeMillis();
			
			long elapsedTime = (stopTime - startTime);
			System.out.println("Elapsed Time:\n--" + elapsedTime);
			
			System.out.println("Before collection:");
			memMonitor();
			
			runtime.gc();
			System.out.println("After collection:");
			memMonitor();
	}
	
	//Method implemented strictly for diagnostic purposes
	private static void memMonitor()
	{
		System.out.println("--Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / MEGABYTE);
	}

	//Recursive method for JsonObject case
	private static boolean traverseObject(JsonObject input) {
		
		Iterator<String> keyList = input.keySet().iterator();
		
		String key;
		JsonValue value;
		
		while(keyList.hasNext()) {
			key = keyList.next();
			value = input.get(key);
			
			if(value.getValueType() == JsonValue.ValueType.ARRAY)
			{
				String temp = new String(path);
				path = path + " -> " + key;
				boolean found = traverseArray((JsonArray)value);
				if(found)
					return true;
				else
					path = temp;
			}
			if(value.getValueType() == JsonValue.ValueType.OBJECT)
			{
				String temp = new String(path);
				path = path + " -> " + key;
				boolean found = traverseObject((JsonObject)value);
				if(found)
					return true;
				else
					path = temp;
			}
			if(value.toString().equals(target))
			{
				System.out.println("Success");
				path = path + " " + key;
				return true;
			}
		}

		
		return false;
	}

	//Recursive method for JsonArray case
	private static boolean traverseArray(JsonArray input) {

		
		for(int i = 0; i < input.size(); i++)
		{
			JsonValue value = input.get(i);
			
			if(value.getValueType() == JsonValue.ValueType.ARRAY)
			{
				String temp = new String(path);
				path = path + " -> " + i;
				boolean found = traverseArray((JsonArray)value);
				if(found)
					return true;
				else
					path = temp;
			}
			if(value.getValueType() == JsonValue.ValueType.OBJECT)
			{
				String temp = new String(path);
				path = path + " -> " + i;
				boolean found = traverseObject((JsonObject)value);
				if(found)
					return true;
				else
					path = temp;
			}
			if(value.toString().equals(target))
			{
				System.out.println("Success");
				path = path + " -> " + i;
				return true;
			}
		}

		
		return false;
	}
}
