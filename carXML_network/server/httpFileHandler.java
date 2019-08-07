import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;

public class httpFileHandler implements HttpHandler {
	

   @Override
   public void handle(HttpExchange httpExchange) throws IOException {
   	
   	String targetPath = httpExchange.getRequestURI().getPath();
   	File fileFolder = new File(".");
		File targetFile = new File(fileFolder, targetPath.replace('/', File.separatorChar));

		if ((targetFile.exists()) && (targetFile.isFile())) {
			
			Headers responseHeaders = httpExchange.getResponseHeaders();
			String mmeType;
			if (targetFile.getName().endsWith(".html") || targetFile.getName().endsWith(".htm")) {
				mmeType = "text/html; charset=UTF-8";
			} else {
				mmeType = "text/plain; charset=UTF-8";
			}
			responseHeaders.set("Content-Type", mmeType);
	
			httpExchange.sendResponseHeaders(200, targetFile.length());
			FileInputStream fileIn = new FileInputStream(targetFile);
			OutputStream out = httpExchange.getResponseBody();

			int bufLen = 10000 * 1024;
			byte[] buf = new byte[bufLen];
			int len = 0;
			while ((len = fileIn.read(buf, 0, bufLen)) != -1) {
				out.write(buf, 0, len);
			}
			out.close();
			fileIn.close();

		} else {

			String message = "404 Not Found " + targetFile.getAbsolutePath();
			httpExchange.sendResponseHeaders(404, 0);
			OutputStream out = httpExchange.getResponseBody();
			out.write(message.getBytes());
			out.close();
		}
   }
}