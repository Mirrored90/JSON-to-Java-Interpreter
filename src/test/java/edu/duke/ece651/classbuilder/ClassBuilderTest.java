package edu.duke.ece651.classbuilder;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class ClassBuilderTest {
  @Test
  public void test_ClassBuilder() {
    InputStream is = MyFileReader.getMyIS("src/test/resources/inputs/array.json");
    System.out.println(is);
    String str = MyFileReader.isToStr(is);
    //    System.out.println(str);
    
    ClassBuilder obj1 = new ClassBuilder(str);
    obj1.createAllClasses("src/test/resources/outputs");
    /*
    ClassBuilder obj2 = new ClassBuilder(is);
    obj2.createAllClasses("src/test/resources/outputs");
    */
  }

  @Test
  public void test_ClassBuilder_with_prims() {
    InputStream is = MyFileReader.getMyIS("src/test/resources/inputs/prims.json");
    System.out.println(is);
    String str = MyFileReader.isToStr(is);
    //    System.out.println(str);
    
    ClassBuilder obj1 = new ClassBuilder(str);
    obj1.createAllClasses("src/test/resources/outputs");
    /*
    ClassBuilder obj2 = new ClassBuilder(is);
    obj2.createAllClasses("src/test/resources/outputs");
    */
  }

}
