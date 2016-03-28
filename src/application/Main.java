package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.*;
import java.io.File;

public class Main extends Application {
    private BorderPane layout;
    private TableView<File> clientTable;
    private TableView<File> serverTable;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("FileSharer v1.0");

        clientTable = new TableView<>();
        clientTable.setItems(DataSource.getAllClientTextFiles());

        serverTable = new TableView<>();
        serverTable.setItems(DataSource.getAllServerTextFiles());

        GridPane buttonArea = new GridPane();
        buttonArea.setPadding(new Insets(10, 10, 10, 10));
        buttonArea.setVgap(10);
        buttonArea.setHgap(10);

        TableColumn<File,String> localColumn;
        localColumn = new TableColumn<>("Local");
        localColumn.setMinWidth(300);
        localColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<File,String> serverColumn;
        serverColumn = new TableColumn<>("Server");
        serverColumn.setMinWidth(300);
        serverColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

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



        buttonArea.add(downloadButton, 0, 0);
        buttonArea.add(uploadButton, 1, 0);
        clientTable.getColumns().add(localColumn);
        serverTable.getColumns().add(serverColumn);

        layout = new BorderPane();
        layout.setLeft(clientTable);

        layout.setRight(serverTable);
        layout.setTop(buttonArea);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
