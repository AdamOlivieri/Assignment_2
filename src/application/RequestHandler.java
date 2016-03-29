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
                String[] textName = command[1].split("/");
                OutputStream out = new FileOutputStream(new File("servertext/" + textName[1]));
                InputStream upin = socket.getInputStream();
                copyAllBytes(upin, out);

                out.close();
                upin.close();

                //System.out.println(command[1]);
            }
            else if (command[0].equalsIgnoreCase("download")){
                String[] stextName = command[1].split("/");
                File downfile = new File("clienttext/" + stextName[1]);
                OutputStream out = socket.getOutputStream();
                InputStream doin = new FileInputStream(downfile);
                copyAllBytes(doin, out);

                out.close();
                doin.close();


                //System.out.println(command[1]);
            }
            in.close();
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

    public String upload(String filename){
        String file = "";
        return file;
    }

    public void download(String filename){

    }
}
