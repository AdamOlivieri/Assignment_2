package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nathaniel on 28/03/16.
 */

//server side of the fileshare system. No UI, just handles requests and sends/receives files
public class FileShareServer {
    private ServerSocket serverSocket;

    //initializes the server and creates a serverSocket
    public FileShareServer(int port) {
        try{
            serverSocket = new ServerSocket(port);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //initializes the RequestHandler thread and loops continuously to allow multithreading
    public void handleRequests() throws IOException{
        System.out.println("FileShareServer Listening...");

        while(true){
            Socket clientSocket = serverSocket.accept();
            Thread handlerThread = new Thread(new RequestHandler(clientSocket));
            handlerThread.start();
        }
    }

    public static void main(String[] args){
        //creates server on specified port
        FileShareServer server = new FileShareServer(8080);
        try {
            //begins handleRequests
            server.handleRequests();
        }catch(IOException e){
            System.err.println("Error creating server");
            e.printStackTrace();
        }
    }
}
