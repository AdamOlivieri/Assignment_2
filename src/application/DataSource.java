package application;

/**
 * Created by adam on 22/03/16.
 */
import java.io.*;
import java.io.IOException;
import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DataSource {
    public static File clientText = new File("clienttext/");

    //public static File serverText = new File("server folder location");

    public static ObservableList<File> getAllClientTextFiles() throws IOException {
        ObservableList<File> clientTextFiles = FXCollections.observableArrayList();
        File[] textFiles = clientText.listFiles();

        for(int i = 0; i < textFiles.length; i++){
            clientTextFiles.add(new File("clienttext/" + textFiles[i].getName()));
        }
        return clientTextFiles;
    }





    public static ObservableList<ServerFile> getAllServerTextFiles() throws IOException {
        Socket socket = new Socket("localhost", 8080);
        ObservableList<ServerFile> serverTextFiles = FXCollections.observableArrayList();
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("DIR");
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            serverTextFiles.add(new ServerFile(line));
        }

        in.close();
        socket.close();

        return serverTextFiles;
    }

}
