package application;

import java.io.*;
import java.net.Socket;

/**
 * Created by nathaniel on 28/03/16.
 */
public class RequestHandler implements Runnable{
    private Socket socket;

    //handles commands sent through sockets
    public RequestHandler(Socket socket){

        //sets socket to accepted socket
        this.socket = socket;
    }

    //run function which holds sockets for the buttons
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            String[] command = in.readLine().split(" ", 2);

            //handles DIR command to return server file names
            if (command[0].equals("DIR")){
                String serverTexts[] = (new File("servertext/")).list();
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                for (String texts : serverTexts) {
                    out.println(texts);
                }
                out.close();
            }
            //handles UPLOAD command to move a file through sockets from client to server
            else if (command[0].equals("UPLOAD")){
                String[] textSplit = command[1].split("/");
                OutputStream out = new FileOutputStream(new File("servertext/" + textSplit[1]));
                InputStream upin = socket.getInputStream();
                copyAllBytes(upin, out);

                out.close();
                upin.close();
            }
            //handles DOWNLOAD command to move a file through sockets from server to client
            else if (command[0].equals("DOWNLOAD")){
                File downfile = new File("servertext/" + command[1]);
                InputStream doin = new FileInputStream(downfile);
                OutputStream out = socket.getOutputStream();
                copyAllBytes(doin, out);

                out.close();
                doin.close();
            }
            in.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    //transfers a file through a socket
    private void copyAllBytes(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int numBytes = -1;
        while ((numBytes = in.read(buffer)) > 0) {
            out.write(buffer);
        }
    }
}
