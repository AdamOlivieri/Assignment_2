package application;

import java.io.*;
import java.net.Socket;

/**
 * Created by nathaniel on 28/03/16.
 */
public class RequestHandler implements Runnable{
    private Socket socket;
    private BufferedReader requestInput;
    private DataOutputStream requestOutput;

    public RequestHandler(Socket socket){

        //request handler multithread
        this.socket = socket;

        try{
            requestInput = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            requestOutput = new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
            System.err.println("Server Error while processing request");
            e.printStackTrace();
        }
    }
    //run function which holds sockets for the buttons
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            String[] command = in.readLine().split(" ", 2);

            //server sockets for DIR refresh
            if (command[0].equals("DIR")){
                String serverTexts[] = (new File("servertext/")).list();
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                for (int i = 0; i < serverTexts.length; i++) {
                    out.println(serverTexts[i]);
                }
                out.close();
            }
            //server sockets for Uploading a file to server and saving it
            else if (command[0].equalsIgnoreCase("upload")){
                String[] textSplit = command[1].split("/");
                OutputStream out = new FileOutputStream(new File("servertext/" + textSplit[1]));
                InputStream upin = socket.getInputStream();
                copyAllBytes(upin, out);

                out.close();
                upin.close();
            }
            //server sockets for Downloading a file from server to client folder
            else if (command[0].equalsIgnoreCase("download")){
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
    private void copyAllBytes(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int numBytes = -1;
        while ((numBytes = in.read(buffer)) > 0) {
            out.write(buffer);
        }
    }
}
