package edu.duke.ece651.classbuilder;

import java.util.*;

public class SerializationHandler {
    private String myCode;
    private HashMap<String,String> mp;
    private HashSet<String> set;
    private ArrayList<String> myKeys;

    public SerializationHandler(HashMap<String,String> mp,ArrayList<String> arr){
        this.mp = mp;
        myCode = "";
        myKeys = new ArrayList<String>(arr);
        String a[] = new String[] {"int","char","boolean","byte","short","long", "float","double",
                "Integer","Character","Boolean","Byte","Short","Long", "Float","Double"};

        set = new HashSet<String>(Arrays.asList(a));

        setMyCode();
    }

  /*
   * Gnerate the code for toJSON
   */
    private void genToJSON(){
        myCode += "public JSONObject toJSON() throws JSONException{\n" +
                "\tHashMap<Object,Integer> mp = new HashMap<Object,Integer>();\n" +
                "\tString ans = helper(mp);\n" +"\treturn new JSONObject(ans);\n" +
                "}\n\n";
    }

  /*
   * Gnerate the code for toJSONHelper
   */
    private void genJSONHelper(){
        myCode += "public String helper(HashMap<Object,Integer> mp){\n" +
                "\tif(mp.containsKey(this)){\n" +
                "\t\treturn \"{ \\\"ref\\\"\" + \" : \" + mp.get(this) + \"}\";\n" +
                "\t}\n" +
                "\telse{\n" +
                "\t\tmp.put(this,mp.size()+1);\n" +
                "\t\tint ID = mp.get(this);\n" +
                "\t\tString res = \"\";\n";
        // check each key
        for(int i=0;i<myKeys.size();i++) {
            String k = myKeys.get(i);
            // if it is a String
            if(mp.get(k).equals("String")){
                myCode += "\t\tres += \"{\\\""+ k +"\\\" : \\\"\" + " + k + " + \"\\\"},\";\n";
            }
            // if it is a primitive
            else if(set.contains(mp.get(k))){
                myCode += "\t\tres += \"{\\\""+ k +"\\\" : \" + " + k + " + \"},\";\n";
            }
            // if it is an array
            else if(mp.get(k).length()>10 && mp.get(k).substring(0,10).equals("ArrayList<")){
                String array = mp.get(k);
                String arrayType =array.substring(10,array.length()-1);
                // if inner is primitive
                if(set.contains(arrayType)){
                    myCode += "\t\tString array = \"\";\n" +
                            "\t\tfor(int i = 0;i<" + k +".size();i++){\n" +
                            "\t\t\tarray += " + k + ".get(i) + \",\";\n" +
                            "\t\t}\n" +
                            "\t\tarray = \"[\" + array + \"]\";\n" +
                            "\t\tres += \"{\\\"" + k +"\\\" : \" + array  + \"},\";\n";
                }
                // if inner is class
                else{
                    myCode += "\t\tString array = \"\";\n" +
                            "\t\tfor(int i = 0;i<" + k +".size();i++){\n" +
                            "\t\t\tarray += " + k + ".get(i).helper(mp) + \",\";\n" +
                            "\t\t}\n" +
                            "\t\tarray = \"[\" + array + \"]\";\n" +
                            "\t\tres += \"{\\\"" + k + "\\\" : \" + array  + \"},\";\n";
                }
            }
            // if it is a class
            else {
                myCode += "\t\tres += \"{\\\"" + k +"\\\" : \" + " +
                        k + ".helper(mp)  + \"},\";\n";
            }
        }

        myCode += "\t\tif(res.length()>1){\n" +
                "\t\t\tres = res.substring(0,res.length()-1);\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tString valueContent =  \"[\" + res + \"]\";\n" +
                "\t\treturn printHelper(valueContent,ID);\n" +
                "\t}\n" +
                "}\n\n";

    }

  /*
   * To set up the JSON format
   */
    private void genPrintHelper(){
        myCode += "public String printHelper(String valueContent, int ID){\n" +
                "\tString value = \"\\\"values\\\" : \" + valueContent;\n" +
                "\tString type = \"\\\"type\\\": \" + \"\\\"\" + this.getClass().getSimpleName() + \"\\\"\" + \", \";\n" +
                "\tString id = \"\\\"id\\\" : \" + ID + \", \";\n" +
                "\n" +
                "\treturn  \"{\" + id + type + value + \"}\";\n" +
                "}\n";
    }

    public String getMyCode(){
        return myCode;
    }

    private void setMyCode(){
        genToJSON();
        genJSONHelper();
        genPrintHelper();
    }
}
