import java.net.Socket;	
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
	
 // each request (xml upload) get processes in own thread
public class socketServerRequest implements Runnable {

   private Socket socket;

   public socketServerRequest(Socket connection) {
		this.socket = connection;
   }

	public void run() {
		String xmlData = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String line;
		   while ((line = br.readLine()) != null)		      	
		   	xmlData += line;
         try {
        		this.socket.close();
         } catch(IOException e) {
         	System.err.print(e);
         }
		 } catch(IOException e) {
		 	System.err.print(e);
		 }	finally {
		 	 carXML carXml = new carXML();
			 carXml.xml2object(xmlData); // TODO: check what happens if xml is shit / validate xml / do not allow shit upload
			 carXml.save();
		 }
 	}   
 }
