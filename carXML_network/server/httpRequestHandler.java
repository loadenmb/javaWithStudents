import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;  
import java.io.IOException;
import java.io.StringWriter;
import java.io.File;

import java.util.Map;
import java.util.HashMap;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

  
public class httpRequestHandler implements HttpHandler {
	
	// get params
	private Map<String, String> httpGetParams;
	
   @Override
   public void handle(HttpExchange httpExchange) throws IOException {
   	
        // get GETs
		this.httpGetParams = this.queryStringHashMap(httpExchange.getRequestURI().getQuery()); // maybe validate incoming url, but not requested 
		
		String uParam = httpGetParams.get("u");
		File folder = new File("data/");
		File file = new File(folder, uParam + ".xml");
		
		// user xml 2 html
		StreamSource in;
		if ((file.exists()) && (file.isFile())) {
			in = new StreamSource(file);
			
		// home page / no valid xml found 
		} else {						
			in = new StreamSource(); // use empty xml source, so we can use our template
		}					 
		
		StringWriter outputHTML = new StringWriter();
		try {
		  TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource("data/template.xslt");
        Transformer transformer = factory.newTransformer(xslStream);       
        StreamResult out = new StreamResult(outputHTML);     
        transformer.transform(in, out);
		} catch (TransformerConfigurationException e) {
    		 System.err.println(e);
 		} catch (TransformerException e) {
     		System.err.println(e);
		}	
	
      String response = outputHTML.toString();       
      httpExchange.sendResponseHeaders(200, response.length());
      OutputStream os = httpExchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
   }
   
   // parse HTTP GET params to hashmap
   public Map<String, String> queryStringHashMap(String queryString) {
 		Map<String, String> hashMap = new HashMap<String, String>();
 		if (queryString == null) return hashMap;
 		for (String param : queryString.split("&")) {
     		String site[] = param.split("=");
     		if (1 < site.length) {
     			hashMap.put(site[0], site[1]);
     		} else {
     			hashMap.put(site[0], "");
     		}
 		}
 		return hashMap;
	}	
}
