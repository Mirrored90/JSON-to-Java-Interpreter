package edu.duke.ece651.classbuilder;

import java.io.*;

public class MyFileReader {
  // get input stream form input path
    public static InputStream getMyIS(String inputPath){
        InputStream r = null;
        try {
            r = new FileInputStream(inputPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return r;
    }

  // convert input stream to string
    public static String isToStr(InputStream is){
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[is.available()];
            is.read(bytes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(bytes);
        return str;
    }
}
