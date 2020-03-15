package edu.duke.ece651.classbuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class FieldExtraction {
   private JSONArray fields;
   private String className;
   private String myCode;
   private String deCode;
   private HashMap<String,String> mp;

    public FieldExtraction(JSONArray value, String name){
        fields = value;
        className = name;
        mp = new HashMap<String, String>();
        myCode = "";
        deCode = "";

        setMyCode();
    }
  
    /*
     * This function set up the code both for normal set and get function, 
     * and also handles serialization and deserialization.
     */
    private void setMyCode(){
        ArrayList<String> arr = new ArrayList<String>();
        // handle JSONObject in JSONArray
        for(int i=0;i<fields.length();i++){
            JSONObject obj = fields.getJSONObject(i);
            String name = obj.getString("name");
            Object type = obj.get("type");

            // use MethodExtraction to handle each field
            MethodExtraction temp = new MethodExtraction(name,type,className);
            myCode += temp.getMyCode();
            // put field name and type into map
            mp.put(name,temp.getMyType());
            arr.add(i,name);
        }

        // do serialization
        SerializationHandler serializer = new SerializationHandler(mp,arr);
        myCode += serializer.getMyCode();
        // do deserialization
        DeserializationHandler deserializer = new DeserializationHandler(mp,className,arr);
        deCode += deserializer.getMyCode();

    }

    /*
     * Return class code
     */
    public String getMyCode(){
        return myCode;
    }

    /*
     * Return deserializer code
     */
    public String getDeCode(){ return deCode;}
}
