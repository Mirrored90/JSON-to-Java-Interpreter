package edu.duke.ece651.classbuilder;

import java.util.*;

public class DeserializationHandler {
    private String myCode;
    private String className;
    private ArrayList<String> myKeys;
    private HashMap<String,String> mp;
    private HashSet<String> set;

    public DeserializationHandler(HashMap<String,String> mp, String className, ArrayList<String> arr){
        this.mp = mp;
        this.className = className;
        myKeys = new ArrayList<String>(arr);
        myCode = "";
        String a[] = new String[] {"int","char","boolean","byte","short","long", "float","double",
                "Integer","Character","Boolean","Byte","Short","Long", "Float","Double","String"};

        set = new HashSet<String>(Arrays.asList(a));

        setMyCode();
    }

  /*
   * Generate code for ReadX
   */
    private void genRead(){
        myCode += "public static " + className + " " + "read" + className + "(JSONObject js) throws JSONException{\n" +
                "\tHashMap<Integer,Object> mp = new HashMap<Integer,Object>();\n" +
                "\treturn read" + className + "Helper(mp, js);\n" +
                "}\n\n";
    }

  /*
   * Generate code for read helper
   */
    private void genReadHelper(){
        myCode += "private static " + className + " read" + className + "Helper(HashMap<Integer, Object> mp, JSONObject js){\n" +
                "\tif(js.optString(\"ref\")!=\"\"){\n" +
                "\t\tint id = js.getInt(\"ref\");\n" +
                "\t\treturn (" + className + ") mp.get(id);\n" +
                "\t}\n" +
                "\telse{\n" +
                "\t\t" + className + " obj = new " + className + "();\n" +
                "\t\tint id = js.getInt(\"id\");\n" +
                "\t\tmp.put(id,obj);\n"+
                "\t\tJSONArray values = js.getJSONArray(\"values\");\n\n";
        // for each key, check its type
        for(int i=0;i<myKeys.size();i++){
            String key = myKeys.get(i);
            String capKey = key.substring(0, 1).toUpperCase() + key.substring(1);
            String type = mp.get(key);
            String capType = type.substring(0, 1).toUpperCase() + type.substring(1);

            myCode += "\t\tJSONObject " + key + " = values.getJSONObject(" + i + ");\n";
            // if it is a primitive type
            if(set.contains(type)){
              if(type.equals("char")) {
                    myCode += "\t\tobj.set" + capKey + "(" + key +
                            ".getString(\"" + key + "\").charAt(0));\n\n";
                }
                else if(type.equals("short")){
                    myCode += "\t\tobj.set" + capKey + "((short)" + key + ".getInt(\"" + key + "\"));\n\n";
                }
                else if(type.equals("byte")){
                    myCode += "\t\tobj.set" + capKey + "((byte)" + key + ".getInt(\"" + key + "\"));\n\n";
                }
                else {
                    myCode += "\t\tobj.set" + capKey + "(" + key + ".get" + capType + "(\"" + key + "\"));\n\n";
                }
            }
            // if it is an array
            else if(type.length()>10 && type.substring(0,10).equals("ArrayList<")){
                String arrayType =type.substring(10,type.length()-1);
                // if inner is primitive
                if(set.contains(arrayType)){
                    myCode += "\t\tfor (int i = 0; i < x.getJSONArray(\"x\").length(); ++i) {\n" +
                            "\t\t\tobj.add" + capKey + "(" + key + ".getJSONArray(\""
                            + key + "\").get(i));\n" +
                            "\t\t}\n\n";
                }
                // if inner is class
                else {
                    myCode += "\t\tfor (int i = 0; i < " + key + ".getJSONArray(\"" + key + "\").length(); ++i) {\n" +
                            "\t\t\tobj.add" + capKey + "(read" + arrayType +
                            "Helper(mp, " + key + ".getJSONArray(\"" + key + "\").getJSONObject(i)));\n" +
                            "\t\t}\n\n";
                }
            }
            // if it is a class
            else{
                myCode += "\t\tobj.set" + capKey + "(read" + type +
                        "Helper(mp, " + key + ".getJSONObject(\"" + key + "\")));\n\n";
            }
        }
        myCode += "\t\treturn obj;\n" + "\t}\n" + "}\n\n";
    }

    public String getMyCode(){
        return myCode;
    }

    private void setMyCode() {
      genRead();
      genReadHelper();
    }

}
