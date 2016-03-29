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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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
                try{
                    FileShareServer server = new FileShareServer(8080);
                    String clientTextName = clientTable.getSelectionModel().getSelectedItem().toString();
                    Socket socket = new Socket("localhost", 8080);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    server.handleRequests();

                    //upload foldername textfilename
                    out.print("upload clienttext " + clientTextName);
                    out.flush();

                    socket.close();

                }catch(IOException e) {e.printStackTrace();}
            }
        });

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                FileShareServer server = new FileShareServer(8080);
                String serverTextName = serverTable.getSelectionModel().getSelectedItem().toString();
                Socket socket = new Socket("localhost", 8080);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                server.handleRequests();

                //download foldername textfilename
                out.print("download servertext " + serverTextName);
                out.flush();

                socket.close();
                }catch(IOException e) {e.printStackTrace();}
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

    public static void setup() throws IOException {

    }

    public static void main(String[] args) {
        launch(args);
    }
}

