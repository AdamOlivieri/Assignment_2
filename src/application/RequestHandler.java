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

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            String[] command = in.readLine().split(" ", 2);
            System.out.print("\n"+command[0]+" "); //              remove this after


            if (command[0].equals("DIR")){
                String serverTexts[] = (new File("servertext/")).list();
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                for (int i = 0; i < serverTexts.length; i++) {
                    out.println(serverTexts[i]);
                }
                out.close();

                socket.close();
            }
            else if (command[0].equalsIgnoreCase("upload")){
                System.out.println(command[1]);
            }
            else if (command[0].equalsIgnoreCase("download")){
                System.out.println(command[1]);
            }
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public String upload(String filename){
        String file = "";
        return file;
    }

    public void download(String filename){

    }
}
