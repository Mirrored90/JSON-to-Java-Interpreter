import org.json.*;
import java.util.*;
public class Office {
public void setOccupant( Faculty toSet ){
	occupant = toSet;
}
public Faculty getOccupant(){
	return occupant;
}

private Faculty occupant;

public void setNumber( int toSet ){
	number = toSet;
}
public int getNumber(){
	return number;
}

private int number;

public void setBuilding( String toSet ){
	building = toSet;
}
public String getBuilding(){
	return building;
}

private String building;

public JSONObject toJSON() throws JSONException{
	HashMap<Object,Integer> mp = new HashMap<Object,Integer>();
	String ans = helper(mp);
	return new JSONObject(ans);
}

public String helper(HashMap<Object,Integer> mp){
	if(mp.containsKey(this)){
		return "{ \"ref\"" + " : " + mp.get(this) + "}";
	}
	else{
		mp.put(this,mp.size()+1);
		int ID = mp.get(this);
		String res = "";
		res += "{\"occupant\" : " + occupant.helper(mp)  + "},";
		res += "{\"number\" : " + number + "},";
		res += "{\"building\" : \"" + building + "\"},";
		if(res.length()>1){
			res = res.substring(0,res.length()-1);
		}

		String valueContent =  "[" + res + "]";
		return printHelper(valueContent,ID);
	}
}

public String printHelper(String valueContent, int ID){
	String value = "\"values\" : " + valueContent;
	String type = "\"type\": " + "\"" + this.getClass().getSimpleName() + "\"" + ", ";
	String id = "\"id\" : " + ID + ", ";

	return  "{" + id + type + value + "}";
}

}
