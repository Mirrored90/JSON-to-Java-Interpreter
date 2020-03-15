import org.json.*;
import java.util.*;
public class Faculty {
public void setName( String toSet ){
	name = toSet;
}
public String getName(){
	return name;
}

private String name;

public Faculty(){
	taught = new ArrayList<Course>();
}
public void setTaught( int index, Course toSet ){
	taught.set(index,toSet);
}
public Course getTaught( int i ){
	return taught.get(i);
}
public int numTaught(){
	return taught.size();
}
public void addTaught( Course toAdd ){
	taught.add(toAdd);
}

private ArrayList<Course> taught;

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
		res += "{\"name\" : \"" + name + "\"},";
		String array = "";
		for(int i = 0;i<taught.size();i++){
			array += taught.get(i).helper(mp) + ",";
		}
		array = "[" + array + "]";
		res += "{\"taught\" : " + array  + "},";
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
