package application;

/**
 * Created by nathaniel on 29/03/16.
 */
public class ServerFile {
    String name;

    ServerFile(String filename){
        this.name = filename;
    }

    public String getName(){
        return name;
    }
}
