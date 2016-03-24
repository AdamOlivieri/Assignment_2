package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.image.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class Main extends Application {
    private Stage window;
    private BorderPane layout;
    private TableView<File> table;
    //private TableView<File> serverTable;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("FileSharer v1.0");

        table = new TableView<>();
        table.setItems(DataSource.getAllClientTextFiles());
        table.setEditable(false);

        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(10, 10, 10, 10));
        editArea.setVgap(10);
        editArea.setHgap(10);

        TableColumn<File,String> clientColumn = null;
        clientColumn = new TableColumn<>();
        clientColumn.setMinWidth(300);
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<File, String> serverColumn = null;
        serverColumn = new TableColumn<>();
        serverColumn.setMinWidth(300);

        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Upload Pressed");
            }
        });

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Download Pressed");
            }
        });



        editArea.add(downloadButton, 0, 0);
        editArea.add(uploadButton, 1, 0);
        table.getColumns().add(clientColumn);
        table.getColumns().add(serverColumn);

        layout = new BorderPane();
        layout.setCenter(table);
        layout.setTop(editArea);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
