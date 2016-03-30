package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.*;

import java.io.*;
import java.net.Socket;

public class Main extends Application {
    private BorderPane layout;
    private TableView<File> clientTable;
    private TableView<ServerFile> serverTable;

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

        TableColumn<ServerFile, String> serverColumn;
        serverColumn = new TableColumn<>("Server");
        serverColumn.setMinWidth(300);
        serverColumn.setCellValueFactory(new PropertyValueFactory<>("name"));



        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String clientTextName = clientTable.getSelectionModel(
                            ).getSelectedItem().toString();
                    Socket socket = new Socket("localhost", 8080);
                    System.out.println(clientTextName);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());

                    out.println("upload " + clientTextName);
                    out.flush();

                    File file = new File(clientTextName);
                    InputStream in = new FileInputStream(file);
                    OutputStream uout = socket.getOutputStream();
                    copyAllBytes(in, uout);

                    in.close();
                    uout.close();
                    socket.close();

                }catch(IOException e) {
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    System.err.println("Select from the client list to upload");
                }
            }
        });

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String serverTextName = serverTable.getSelectionModel(
                            ).getSelectedItem().name;
                    Socket socket = new Socket("localhost", 8080);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());

                    //download foldername textfilename
                    out.println("download " + serverTextName);
                    out.flush();

                    OutputStream dout = new FileOutputStream(new File("clienttext/" + serverTextName));
                    InputStream in = socket.getInputStream();
                    copyAllBytes(in, dout);

                    dout.close();
                    out.close();
                    socket.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    System.err.println("Select from the server list to download");
                }
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

    private void copyAllBytes(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int numBytes = -1;
        while ((numBytes = in.read(buffer)) > 0) {
            out.write(buffer);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

