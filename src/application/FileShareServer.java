package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void handleRequests(){
        System.out.println("FileShareServer Listening...");

        try{
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    )
            );

            String line  = null;
            String filename = null;
            while((line = in.readLine()) != null) {
                //save the file here????
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
