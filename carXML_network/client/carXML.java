// collections
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// marshalling
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

// xml annotation
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// stream 2 string and back
import java.io.StringWriter;
import java.io.StringReader;

// save file
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// dynamic attribute call
import java.lang.reflect.Field;

@XmlRootElement
public class carXML { // could be great to extend this with java file or array list class, check docs
	
	@XmlElement
	public int id;
	
	@XmlElement
   public String sort_what; // we do not require this information on server because client already sort our xml
   
   @XmlElement
   public String sort_order; // not required at server, same as above, but great to have also
  
   @XmlElement
   public String css_fontheader;
	
	@XmlElement  
   public String css_fontsizeheader;
   
   @XmlElement
   public String css_fontcontent;
   
   @XmlElement
   public String css_fontsizecontent;
   
   @XmlElement
   public String css_backgroundcolor1;
   
   @XmlElement
   public String css_backgroundcolor2;
	
	@XmlElement
	public ArrayList<car> car = new ArrayList<car>();
	   
   public carXML add(car newCar) {
   	
   	// update car if id already exist in car list
		boolean updated = false;   	
   	for (int i = 0; i < this.car.size(); i++) {
			if (this.car.get(i).id == newCar.id) {
				this.car.set(i, newCar);
				updated = true;
			}
		}
		
		// add car if not exist
   	if (updated == false)
			this.car.add(newCar); 
		
		return this;
   }
 
   public void sort() {
		Collections.sort(this.car, new Comparator<car>() {	
	        @Override
	        public int compare(car car1, car car2) {   
	        		int car1value;
	        		int car2value;       
					try {	        		
						 Class<?> currClass = car1.getClass();						 
						 Field attribute = currClass.getDeclaredField(sort_what);  // get attribute dynamically
						 attribute.setAccessible(true);
						 car1value = (int) attribute.get(car1);						 
						 car2value = (int) attribute.get(car2);
					} catch(NoSuchFieldException e) {
						System.err.print(e);
						return 0;
					} catch(IllegalAccessException e) {
						System.err.print(e);
						return 0;
					}   	
					if (sort_order == "ASC") {
		         	if (car1value > car2value) {
		            	return 1;
		            } else if (car1value < car2value) {
		            	return -1;
		            }
						return 0;				            			            
		         } else {
		         	if (car1value < car2value) {
		            	return 1;
		            } else if (car1value > car2value) {
		            	return -1;
		            }
						return 0;				            
		         }
  			  }
    	});
   }   
   
   public String object2xml() {
   	
   	// the fucking magic! we use with XmlElement marked class structure of this and sub class (car) to create xml structure. deutsch: es ist sehr geil
		try {
			StringWriter stringWriter = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(carXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();	
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);	
			jaxbMarshaller.marshal(this, stringWriter);
			return stringWriter.toString(); // maybe greater to return stream here	     
      } catch (JAXBException e) {
			System.err.print(e);
      }
      return "";
   }
   
   public void xml2object(String xml) {
   	
   	// the other magic. same as above but backwards
		try {
			StringReader stringReader = new StringReader(xml);
			JAXBContext jaxbContext = JAXBContext.newInstance(carXML.class);			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			carXML carXml = (carXML) jaxbUnmarshaller.unmarshal(stringReader); // cannot write object to this, need an unmagic remap
			this.id = carXml.id; 
			this.sort_what = carXml.sort_what;	
			this.sort_order = carXml.sort_order;			
			this.css_fontheader = carXml.css_fontheader;
   		this.css_fontsizeheader = carXml.css_fontsizeheader;
  			this.css_fontcontent = carXml.css_fontcontent;
   		this.css_fontsizecontent = carXml.css_fontsizecontent;
   		this.css_backgroundcolor1 = carXml.css_backgroundcolor1;
   		this.css_backgroundcolor2 = carXml.css_backgroundcolor2;
			this.car = carXml.car;			
		} catch (JAXBException e) {
			System.err.print(e);
		}
   }
   
   public void save() {
		 String absPath = new File(".").getAbsolutePath();
		 absPath = absPath.substring(0, absPath.length() - 1); // remove . at path ending			 
		 try {
			File file = new File(absPath + "data" + File.separator + this.id + ".xml");
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);
			writer.write(this.object2xml());
			writer.flush();
			writer.close();			
		 } catch(IOException e) {
		 	System.err.print(e);
		 }
   }

}
