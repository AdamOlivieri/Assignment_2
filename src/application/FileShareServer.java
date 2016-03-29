package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nathaniel on 28/03/16.
 */
public class FileShareServer {
    private ServerSocket serverSocket;

    public FileShareServer(int port) {
        try{
            serverSocket = new ServerSocket(port);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void handleRequests() throws IOException{
        System.out.println("FileShareServer Listening...");

        while(true){
            Socket clientSocket = serverSocket.accept();
            Thread handlerThread = new Thread(new RequestHandler(clientSocket));
            handlerThread.start();
        }
    }

    public static void main(String[] args){
        FileShareServer server = new FileShareServer(8080);
        try {
            server.handleRequests();
        }catch(IOException e){
            System.err.println("Error creating server");
            e.printStackTrace();
        }
    }
}
