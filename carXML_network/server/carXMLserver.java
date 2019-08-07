import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class carXMLserver {

     public static void main(String[] args) throws Exception {
     	
		   System.out.println("good morning carXMLserver");
     	
     	  // accept HTTP connections to display website
         HttpServer server = HttpServer.create(new InetSocketAddress(7331), 0);
         server.createContext("/", new httpRequestHandler());
         server.createContext("/public/", new httpFileHandler());
         server.setExecutor(null); 
         server.start();
         
			// accept connections to receive xml
		 	socketServer socketserver = new socketServer();
        	socketserver.serve();
     }
}

