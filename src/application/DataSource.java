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


    //ObservableList for client files using file type
    public static ObservableList<File> getAllClientTextFiles() throws IOException {
        ObservableList<File> clientTextFiles = FXCollections.observableArrayList();
        File[] textFiles = clientText.listFiles();

        //goes through client files and adds them to ObservableList for TableView
        for(File text : textFiles){
            clientTextFiles.add(new File("clienttext/" + text.getName()));
        }
        return clientTextFiles;
    }




    //ObservableList for server files using string type
    public static ObservableList<ServerFile> getAllServerTextFiles() throws IOException {
        //connects to server
        Socket socket = new Socket("localhost", 8080);
        ObservableList<ServerFile> serverTextFiles = FXCollections.observableArrayList();

        //sends command to server
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("DIR");
        out.flush();

        //reads from server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            serverTextFiles.add(new ServerFile(line));
        }

        //closes everything
        out.close();
        in.close();
        socket.close();

        return serverTextFiles;
    }

}
