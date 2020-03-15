import org.json.*;
import java.util.*;
public class Grade {
public void setCourse( Course toSet ){
	course = toSet;
}
public Course getCourse(){
	return course;
}

private Course course;

public void setStudent( String toSet ){
	student = toSet;
}
public String getStudent(){
	return student;
}

private String student;

public void setGrade( double toSet ){
	grade = toSet;
}
public double getGrade(){
	return grade;
}

private double grade;

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
		res += "{\"course\" : " + course.helper(mp)  + "},";
		res += "{\"student\" : \"" + student + "\"},";
		res += "{\"grade\" : " + grade + "},";
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
