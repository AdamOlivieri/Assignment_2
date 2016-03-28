package application;

/**
 * Created by adam on 22/03/16.
 */
import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;

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

    public static ObservableList<File> getAllServerTextFiles() throws IOException {
        /*ServerSocket serverSocket = new ServerSocket(8080);
        ObservableList<File> serverTextFiles = FXCollections.observableArrayList();
        File[] textFiles = serverText.listFiles();

        for(int i = 0; i < textFiles.length; i++){
            serverTextFiles.add(new File("clienttext/" + textFiles[i].getName()));
        }*/
        return null;
    }

}
