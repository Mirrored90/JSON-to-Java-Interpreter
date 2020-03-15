import org.json.*;
import java.util.*;
public class Course {
public void setInstructor( Faculty toSet ){
	instructor = toSet;
}
public Faculty getInstructor(){
	return instructor;
}

private Faculty instructor;

public void setNumStudents( int toSet ){
	numStudents = toSet;
}
public int getNumStudents(){
	return numStudents;
}

private int numStudents;

public Course(){
	grades = new ArrayList<Grade>();
}
public void setGrades( int index, Grade toSet ){
	grades.set(index,toSet);
}
public Grade getGrades( int i ){
	return grades.get(i);
}
public int numGrades(){
	return grades.size();
}
public void addGrades( Grade toAdd ){
	grades.add(toAdd);
}

private ArrayList<Grade> grades;

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
		res += "{\"instructor\" : " + instructor.helper(mp)  + "},";
		res += "{\"numStudents\" : " + numStudents + "},";
		String array = "";
		for(int i = 0;i<grades.size();i++){
			array += grades.get(i).helper(mp) + ",";
		}
		array = "[" + array + "]";
		res += "{\"grades\" : " + array  + "},";
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
