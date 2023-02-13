import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class ListJsonReader{
     
    
    public ListJsonReader(){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("list.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray chanceList = (JSONArray) jsonfile.get("chanceList");
            for(Object i:chanceList){
				Card.chance_list.add(((String)((JSONObject)i).get("item")));
                 /*
				 You can reach items by using:
				 ((String)((JSONObject)i).get("item"));
				 And you can add these items to any datastructure (e.g. array, linkedlist, etc.)
				 */
				 
            }
            JSONArray communityChestList = (JSONArray) jsonfile.get("communityChestList");
            for(Object i:communityChestList){
				
				//You can reach items by using:
				Card.chest_list.add(((String)((JSONObject)i).get("item")));
				//And you can add these items to any datastructure (e.g. array, linkedlist, etc.) 
				
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
     }
     //You can add function if you want
}

