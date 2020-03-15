package hwk1.testing.prims;
import org.json.*;
import java.util.*;
public class Deserializer {
public static Prims readPrims(JSONObject js) throws JSONException{
	HashMap<Integer,Object> mp = new HashMap<Integer,Object>();
	return readPrimsHelper(mp, js);
}

private static Prims readPrimsHelper(HashMap<Integer, Object> mp, JSONObject js){
	if(js.optString("ref")!=""){
		int id = js.getInt("ref");
		return (Prims) mp.get(id);
	}
	else{
		Prims obj = new Prims();
		int id = js.getInt("id");
		mp.put(id,obj);
		JSONArray values = js.getJSONArray("values");

		JSONObject x = values.getJSONObject(0);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addX(x.getJSONArray("x").getInt(i));
		}

		JSONObject isAwesome = values.getJSONObject(1);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addIsAwesome(isAwesome.getJSONArray("isAwesome").getBoolean(i));
		}

		JSONObject ateBits = values.getJSONObject(2);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addAteBits(ateBits.getJSONArray("ateBits").getByte(i));
		}

		JSONObject boat = values.getJSONObject(3);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addBoat(boat.getJSONArray("boat").getFloat(i));
		}

		JSONObject trouble = values.getJSONObject(4);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addTrouble(trouble.getJSONArray("trouble").getDouble(i));
		}

		JSONObject isntPronouncedLikeCare = values.getJSONObject(5);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addIsntPronouncedLikeCare(isntPronouncedLikeCare.getJSONArray("isntPronouncedLikeCare").getChar(i));
		}

		JSONObject waysAway = values.getJSONObject(6);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addWaysAway(waysAway.getJSONArray("waysAway").getLong(i));
		}

		JSONObject stackOfPancakes = values.getJSONObject(7);
		for (int i = 0; i < x.getJSONArray("x").length(); ++i) {
			obj.addStackOfPancakes(stackOfPancakes.getJSONArray("stackOfPancakes").getShort(i));
		}

		return obj;
	}
}


}
