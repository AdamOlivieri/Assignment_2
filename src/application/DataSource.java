package application;

/**
 * Created by adam on 22/03/16.
 */
import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DataSource {
    public static File clientText = new File("clienttext/");
    public static File serverText = new File("servertext/");

    //public static File serverText = new File("server folder location");

    public static ObservableList<File> getAllClientTextFiles() throws IOException {
        ObservableList<File> clientTextFiles = FXCollections.observableArrayList();
        File[] textFiles = clientText.listFiles();

        for(int i = 0; i < textFiles.length; i++){
            clientTextFiles.add(new File("clienttext/" + textFiles[i].getName()));
        }
        return clientTextFiles;
    }

    public static ObservableList<String> getAllServerTextFiles() throws IOException {
        Socket socket = new Socket("localhost", 8080);
        ObservableList<String> serverTextFiles = FXCollections.observableArrayList();
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.print("DIR");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        /*String input;
        while ((input = in.readLine())!= null)
            System.out.println(input);*/
        //in.close();

        socket.close();

        return serverTextFiles;
    }

}
