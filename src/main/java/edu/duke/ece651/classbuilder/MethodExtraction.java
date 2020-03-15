package edu.duke.ece651.classbuilder;

import org.json.JSONObject;

public class MethodExtraction {
    private String name;
    private String className;
    private String capName;
    private Object type;
    private String myCode;
    private String myType;

    public MethodExtraction(String key, Object value, String inputClassName){
        name = key;
        className = inputClassName;
        setCapName();
        type = value;
        myCode = "";
        myType = "";
        setMyCode();
    }

  /*
   * Convert normal type to primitive
   */
    private String getPrimitiveType(String before){
        String after = "";
        switch (before){
            case "int": after = "Integer";
                break;
            case "char": after = "Character";
                break;
            case "boolean": after = "Boolean";
                break;
            case "byte": after = "Byte";
                break;
            case "short": after = "Short";
                break;
            case "long": after = "Long";
                break;
            case "float": after = "Float";
                break;
            case "double": after = "Double";
                break;
            default:
                after = before;
        }
        return after;
    }

  /*
   * To handle multi-dimentional array
   */
    private String setMyTypeHelper(JSONObject obj){
        if(obj.optJSONObject("e") == null){
            return "ArrayList<" + getPrimitiveType(obj.getString("e")) + ">";
        }
        else{
            String content = setMyTypeHelper(obj.getJSONObject("e"));
            return "ArrayList<" + content + ">";
        }
    }

  /*
   * Generate source code
   */
    private void setMyType() {
        // if type:value
        if(type instanceof String){
            myType = (String)type;
            setSetX();
            setGetX();
        }
        // if type:{e:value}
        else if(type instanceof JSONObject) {
            String content;
            Object temp = ((JSONObject) type).get("e");
            if(temp instanceof String){
                content = temp.toString();
            }
            else{
                content = setMyTypeHelper((JSONObject) temp);
            }

            String newContent = "ArrayList<" + getPrimitiveType(content) + ">";

            myType = setMyTypeHelper((JSONObject) type);
            setConstructor(newContent);
            setSetArrX(content);
            setGetArrayX(content);
            setNumX();
            setAddX(content);
        }
    }

    private void setConstructor(String newContent){
        myCode += "public " + className + "(){\n\t"
                + name + " = new " + newContent + "();\n" + "}\n";
    }

    private void setGetX(){
        myCode += "public " + myType + " get" + capName + "(){"
                + "\n" + "\treturn " + name + ";" + "\n}\n";
    }

    private void setSetX(){
        myCode += "public void set" + capName + "( " + myType + " toSet"
                +" ){" + "\n" + "\t" + name + " " + "= toSet;" + "\n}\n";
    }

    private void setGetArrayX( String content ){
        myCode += "public " + content + " get" + capName + "( int i ){"
                + "\n" + "\treturn " + name + ".get(i);" + "\n}\n";
    }

    private void setSetArrX( String content ){
        myCode += "public void set" + capName + "( int index, " + content + " toSet"
                +" ){" + "\n" + "\t" + name + ".set(index,toSet);" + "\n}\n";
    }

    private void setNumX(){
        myCode += "public int num" + capName + "(){\n" + "\treturn " + name +".size();\n" + "}\n";
    }

    private void setAddX( String content ){
        myCode += "public void add" + capName + "( " + content + " toAdd"
                +" ){\n" + "\t" + name + ".add(toAdd);" + "\n}\n";
    }

    private void setCapName(){
        capName = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private void setMyCode(){
        setMyType();
        myCode += "\nprivate" + " " + myType + " " + name + ";\n\n";
    }

    public String getMyCode() {
        return myCode;
    }

    public String getName() {
        return name;
    }

    public String getMyType() {
        return myType;
    }
}
