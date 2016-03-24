package sample;

/**
 * Created by adam on 22/03/16.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.io.IOException;

public class DataSource {
    public static File clientText = new File("clienttext/");

    public static ObservableList<File> getAllClientTextFiles() throws IOException {
        ObservableList<File> clientTextFiles = FXCollections.observableArrayList();
        File[] textFiles = clientText.listFiles();

        for(int i = 0; i < textFiles.length; i++){
            clientTextFiles.add(new File("clienttext/" + textFiles[i].getName()));
        }
        return clientTextFiles;
    }

}
