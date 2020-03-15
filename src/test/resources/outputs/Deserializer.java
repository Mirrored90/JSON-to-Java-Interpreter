import org.json.*;
import java.util.*;
public class Deserializer {
public static Course readCourse(JSONObject js) throws JSONException{
	HashMap<Integer,Object> mp = new HashMap<Integer,Object>();
	return readCourseHelper(mp, js);
}

private static Course readCourseHelper(HashMap<Integer, Object> mp, JSONObject js){
	if(js.optString("ref")!=""){
		int id = js.getInt("ref");
		return (Course) mp.get(id);
	}
	else{
		Course obj = new Course();
		int id = js.getInt("id");
		mp.put(id,obj);
		JSONArray values = js.getJSONArray("values");

		JSONObject instructor = values.getJSONObject(0);
		obj.setInstructor(readFacultyHelper(mp, instructor.getJSONObject("instructor")));

		JSONObject numStudents = values.getJSONObject(1);
		obj.setNumStudents(numStudents.getInt("numStudents"));

		JSONObject grades = values.getJSONObject(2);
		for (int i = 0; i < grades.getJSONArray("grades").length(); ++i) {
			obj.addGrades(readGradeHelper(mp, grades.getJSONArray("grades").getJSONObject(i)));
		}

		return obj;
	}
}

public static Office readOffice(JSONObject js) throws JSONException{
	HashMap<Integer,Object> mp = new HashMap<Integer,Object>();
	return readOfficeHelper(mp, js);
}

private static Office readOfficeHelper(HashMap<Integer, Object> mp, JSONObject js){
	if(js.optString("ref")!=""){
		int id = js.getInt("ref");
		return (Office) mp.get(id);
	}
	else{
		Office obj = new Office();
		int id = js.getInt("id");
		mp.put(id,obj);
		JSONArray values = js.getJSONArray("values");

		JSONObject occupant = values.getJSONObject(0);
		obj.setOccupant(readFacultyHelper(mp, occupant.getJSONObject("occupant")));

		JSONObject number = values.getJSONObject(1);
		obj.setNumber(number.getInt("number"));

		JSONObject building = values.getJSONObject(2);
		obj.setBuilding(building.getString("building"));

		return obj;
	}
}

public static Faculty readFaculty(JSONObject js) throws JSONException{
	HashMap<Integer,Object> mp = new HashMap<Integer,Object>();
	return readFacultyHelper(mp, js);
}

private static Faculty readFacultyHelper(HashMap<Integer, Object> mp, JSONObject js){
	if(js.optString("ref")!=""){
		int id = js.getInt("ref");
		return (Faculty) mp.get(id);
	}
	else{
		Faculty obj = new Faculty();
		int id = js.getInt("id");
		mp.put(id,obj);
		JSONArray values = js.getJSONArray("values");

		JSONObject name = values.getJSONObject(0);
		obj.setName(name.getString("name"));

		JSONObject taught = values.getJSONObject(1);
		for (int i = 0; i < taught.getJSONArray("taught").length(); ++i) {
			obj.addTaught(readCourseHelper(mp, taught.getJSONArray("taught").getJSONObject(i)));
		}

		return obj;
	}
}

public static Grade readGrade(JSONObject js) throws JSONException{
	HashMap<Integer,Object> mp = new HashMap<Integer,Object>();
	return readGradeHelper(mp, js);
}

private static Grade readGradeHelper(HashMap<Integer, Object> mp, JSONObject js){
	if(js.optString("ref")!=""){
		int id = js.getInt("ref");
		return (Grade) mp.get(id);
	}
	else{
		Grade obj = new Grade();
		int id = js.getInt("id");
		mp.put(id,obj);
		JSONArray values = js.getJSONArray("values");

		JSONObject course = values.getJSONObject(0);
		obj.setCourse(readCourseHelper(mp, course.getJSONObject("course")));

		JSONObject student = values.getJSONObject(1);
		obj.setStudent(student.getString("student"));

		JSONObject grade = values.getJSONObject(2);
		obj.setGrade(grade.getDouble("grade"));

		return obj;
	}
}


}
