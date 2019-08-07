import java.net.Socket;
import java.net.ServerSocket;	
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;

public class socketServer {

    private ServerSocket socketHandle;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);    

    public void serve() {        
        try { 
            this.socketHandle = new ServerSocket(1337);
            while(true) {
                try {    
                    this.threadPool.submit(new socketServerRequest(this.socketHandle.accept()));
                } catch(IOException e) {
                   System.err.print(e);
                }
            }
        } catch(IOException e) {
            System.err.print(e);
        }
    }

    public void stopServe() {
        this.threadPool.shutdownNow();
        try {
            this.socketHandle.close();
        } catch (IOException e) {
           System.err.print(e);
        }
    }
}
