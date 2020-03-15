package hwk1.testing.prims;
import org.json.*;
import java.util.*;
public class Prims {
public Prims(){
	x = new ArrayList<Integer>();
}
public void setX( int index, int toSet ){
	x.set(index,toSet);
}
public int getX( int i ){
	return x.get(i);
}
public int numX(){
	return x.size();
}
public void addX( int toAdd ){
	x.add(toAdd);
}

private ArrayList<Integer> x;

public Prims(){
	isAwesome = new ArrayList<Boolean>();
}
public void setIsAwesome( int index, boolean toSet ){
	isAwesome.set(index,toSet);
}
public boolean getIsAwesome( int i ){
	return isAwesome.get(i);
}
public int numIsAwesome(){
	return isAwesome.size();
}
public void addIsAwesome( boolean toAdd ){
	isAwesome.add(toAdd);
}

private ArrayList<Boolean> isAwesome;

public Prims(){
	ateBits = new ArrayList<Byte>();
}
public void setAteBits( int index, byte toSet ){
	ateBits.set(index,toSet);
}
public byte getAteBits( int i ){
	return ateBits.get(i);
}
public int numAteBits(){
	return ateBits.size();
}
public void addAteBits( byte toAdd ){
	ateBits.add(toAdd);
}

private ArrayList<Byte> ateBits;

public Prims(){
	boat = new ArrayList<Float>();
}
public void setBoat( int index, float toSet ){
	boat.set(index,toSet);
}
public float getBoat( int i ){
	return boat.get(i);
}
public int numBoat(){
	return boat.size();
}
public void addBoat( float toAdd ){
	boat.add(toAdd);
}

private ArrayList<Float> boat;

public Prims(){
	trouble = new ArrayList<Double>();
}
public void setTrouble( int index, double toSet ){
	trouble.set(index,toSet);
}
public double getTrouble( int i ){
	return trouble.get(i);
}
public int numTrouble(){
	return trouble.size();
}
public void addTrouble( double toAdd ){
	trouble.add(toAdd);
}

private ArrayList<Double> trouble;

public Prims(){
	isntPronouncedLikeCare = new ArrayList<Character>();
}
public void setIsntPronouncedLikeCare( int index, char toSet ){
	isntPronouncedLikeCare.set(index,toSet);
}
public char getIsntPronouncedLikeCare( int i ){
	return isntPronouncedLikeCare.get(i);
}
public int numIsntPronouncedLikeCare(){
	return isntPronouncedLikeCare.size();
}
public void addIsntPronouncedLikeCare( char toAdd ){
	isntPronouncedLikeCare.add(toAdd);
}

private ArrayList<Character> isntPronouncedLikeCare;

public Prims(){
	waysAway = new ArrayList<Long>();
}
public void setWaysAway( int index, long toSet ){
	waysAway.set(index,toSet);
}
public long getWaysAway( int i ){
	return waysAway.get(i);
}
public int numWaysAway(){
	return waysAway.size();
}
public void addWaysAway( long toAdd ){
	waysAway.add(toAdd);
}

private ArrayList<Long> waysAway;

public Prims(){
	stackOfPancakes = new ArrayList<Short>();
}
public void setStackOfPancakes( int index, short toSet ){
	stackOfPancakes.set(index,toSet);
}
public short getStackOfPancakes( int i ){
	return stackOfPancakes.get(i);
}
public int numStackOfPancakes(){
	return stackOfPancakes.size();
}
public void addStackOfPancakes( short toAdd ){
	stackOfPancakes.add(toAdd);
}

private ArrayList<Short> stackOfPancakes;

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
		String array = "";
		for(int i = 0;i<x.size();i++){
			array += x.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"x\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<isAwesome.size();i++){
			array += isAwesome.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"isAwesome\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<ateBits.size();i++){
			array += ateBits.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"ateBits\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<boat.size();i++){
			array += boat.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"boat\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<trouble.size();i++){
			array += trouble.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"trouble\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<isntPronouncedLikeCare.size();i++){
			array += isntPronouncedLikeCare.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"isntPronouncedLikeCare\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<waysAway.size();i++){
			array += waysAway.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"waysAway\" : " + array  + "},";
		String array = "";
		for(int i = 0;i<stackOfPancakes.size();i++){
			array += stackOfPancakes.get(i) + ",";
		}
		array = "[" + array + "]";
		res += "{\"stackOfPancakes\" : " + array  + "},";
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
