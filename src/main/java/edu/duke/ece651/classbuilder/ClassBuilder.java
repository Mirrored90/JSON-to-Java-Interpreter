package edu.duke.ece651.classbuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class ClassBuilder {
    private JSONObject myJson;
    private ArrayList<String> classList;
    private HashMap<String, JSONArray> classMap;
    private String deCode; // code for Deserializer

    /**
     * This constructor takes an string to construct the class.
     */
    public ClassBuilder(String inputJson){
        classMap = new HashMap<String, JSONArray>();
        classList = new ArrayList<String>();
        myJson = new JSONObject(inputJson);
        deCode = "";
        setClassMap();
    }

     /**                                                                                                                                                                               * This constructor takes an input stream to construct the class.                                                                                                                 */
    public ClassBuilder(InputStream inputJson) {
      String str = MyFileReader.isToStr(inputJson);
      classMap = new HashMap<String, JSONArray>();
      classList = new ArrayList<String>();
      myJson = new JSONObject(str);
      deCode = "";
      setClassMap();
    }
  
    /*
     * This function set up the hashmap which stores all class name and its
     * JSONArray.
     */    
    private void setClassMap(){
        JSONArray myField = null;
    
        JSONArray myList = myJson.getJSONArray("classes");
    
        // handling each class separately
        for(int i=0;i<myList.length();i++){
            String key = myList.getJSONObject(i).getString("name");
            classList.add(key);
    
            // if class has fields
            if(myList.getJSONObject(i).optJSONArray("fields") != null){
                myField = myList.getJSONObject(i).getJSONArray("fields");
            }
    
            // map class name and its field(Json array)
            classMap.put(key,myField);
        }
    }

    /*
     * This function get every class names
     */    
    public Collection<String> getClassNames(){
        return classList;
    }
  
    /**                                                                                                                                                                               * This funtion get the source code for each classes.
     */
    public String getSourceCode(String className){
        String body = "";
        // if there is a class
        if(classMap.get(className) != null) {
            // pass the JSONArray to fieldExtraction to extract fields 
            FieldExtraction temp = new FieldExtraction(classMap.get(className),className);
            body = temp.getMyCode();
            deCode += temp.getDeCode();
        }
        // import libraries and enclose the code with class name
        String myCode = "import org.json.*;\n" + "import java.util.*;\n";
        myCode += "public class " + className + " {\n" + body + "\n}\n";

        return myCode;
    }
  
   /*
    * This functions actrually generate the class to the base path.
    * If there is package, it will be added to base path   
    */
    public void createAllClasses(String basePath){
        String path = "";
        // export to package
        if(!myJson.optString("package").equals("")){
            path = myJson.getString("package");
            char[] charArray = path.toCharArray();
            // change . to /
            for(int i=0;i<charArray.length;i++){
                if(charArray[i] == '.'){
                    charArray[i] = '/';
                }
            }
            String packagePath = new String(charArray);
            basePath = basePath + "/" + packagePath;
        }

        // get source code from each class
        ArrayList<String> list = (ArrayList<String>) getClassNames();
        for(String name: list){
            String code = getSourceCode(name);
            // if there is a package
            if(!path.equals("")){
                code = "package " + path + ";\n" + code;
            }

            File f =  new File(basePath,  "/" + name + ".java");
            writeMyFile(code,f);
        }

        // handle code for Deserializer
        if(!path.equals("")){
            deCode = "package " + path + ";\n"+
                    "import org.json.*;\n" +
                    "import java.util.*;\n" +
                    "public class Deserializer {\n" + deCode + "\n}\n";
        }
        else {
            deCode = "import org.json.*;\n" +
                    "import java.util.*;\n" +
                    "public class Deserializer {\n" + deCode + "\n}\n";
        }
        File f =  new File(basePath,  "/Deserializer.java");
        writeMyFile(deCode,f);

    }

   /*
    * This function write code to files.
    */
    private void writeMyFile(String code, File f){
        // if there is no such path
        if(!f.exists()){
            f.getParentFile().mkdirs();
        }
        try {
            f.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(code);
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
  
   public static void main(String [] args){
     InputStream is = MyFileReader.getMyIS(args[0]);
     String str = MyFileReader.isToStr(is);
     ClassBuilder obj2 = new ClassBuilder(str);
     obj2.createAllClasses(args[1]);
  }
}
