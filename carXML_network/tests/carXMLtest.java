import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

import java.io.UnsupportedEncodingException;
import java.io.IOException;

public class carXMLtest {
	
	
  public static void main(String[] args) {
   	System.out.println("Unit Tests mit jUnit");
  		Result result = JUnitCore.runClasses(carXMLtest.class);		
   	for (Failure failure : result.getFailures()) {
      	System.out.println(failure.toString());
   	}
		System.out.println("status: " + result.wasSuccessful());
  }
  
  @Test
  public void testAdd() {
    carXML carxml = new carXML();
    carxml.add(new car());    
    assertTrue(carxml.car.size() > 0);
  }
  
  @Test 
  public void testUpdate() {    
		car car0 = new car(); 
		car0.id = 0;
		car car1 = new car();
		car1.id = 1; 
		car1.ps = 1;
		car car2 = new car();
		car2.id = 2;  
	   carXML carxml = new carXML();
	   carxml.add(car0).add(car1).add(car2);    
	   car car3 = new car();
		car3.id = 1;    
		car3.ps = 2;    
	   carxml.add(car3);     
	   assertTrue((carxml.car.size() == 3) && (carxml.car.get(1).ps == 2));
  }
  
  @Test
  public void testSort() {  		
  		car car0 = new car(); 
		car0.id = 0;
		car car1 = new car();
		car1.id = 1; 
		car car2 = new car();
		car2.id = 2; 
		car car3 = new car();
		car3.id = 3;   
	   carXML carxml = new carXML();
	   carxml.add(car0).add(car1).add(car2).add(car3); 
	   carxml.sort_what = "id";
	   carxml.sort_order = "DESC";
	   carxml.sort();
	   assertTrue(carxml.car.get(0).id == 3);
  }
  
  @Test
   public void testXml2() { 
     	car car0 = new car(); 
		car0.name = "junit test vehicle";
		car0.ps = 1;
		car0.mileage = 2;
		car0.admission = 2;
		car0.fixture = "much";
		car0.text = "many"; 
		car car1 = new car();
		car1.id = 1;   
		carXML carxml = new carXML();
		carxml.add(car0).add(car1);
   	String xmldata = carxml.object2xml();
   	carxml.xml2object(xmldata);
   	assertTrue(carxml.car.get(0).ps == 1);
	}
	
	@Test
	public void testSave() throws UnsupportedEncodingException, IOException { 
	   car car0 = new car(); 
		car0.name = "junit test vehicle";
		car0.ps = 1;
		car0.mileage = 2;
		car0.admission = 2;
		car0.fixture = "much";
		car0.text = "many"; 
		carXML carxml = new carXML();
		carxml.id = 3;
		carxml.add(car0);
		carxml.save();		
		String absPath = new File(".").getAbsolutePath();
 		carxml.xml2object(new String(Files.readAllBytes(Paths.get(absPath.substring(0, absPath.length() - 1) + "data" + File.separator + 3 + ".xml")), "utf-8"));
		assertTrue(carxml.car.get(0).ps == 1);		
	}
}