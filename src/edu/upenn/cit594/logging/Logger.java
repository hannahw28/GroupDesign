package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private PrintWriter out;

    private boolean isDestinationSet = false;
    private boolean isUsingSystemErr = false;

    private Logger(){}

    private static Logger instance;

    public static Logger getInstance(){
        if (instance == null){
            instance = new Logger();
        }
        return instance;
    }

    public void setDestination(String filename) throws IOException {
        if (filename == null || filename.isEmpty()){
            isUsingSystemErr = true;
        }
        if (out != null && !isUsingSystemErr){
            out.close();
        }

        if (isUsingSystemErr){
            out = new PrintWriter(System.err);
        } else{
            FileWriter fw = new FileWriter(filename, true);
            out = new PrintWriter(fw, true);
            isDestinationSet = true;
            isUsingSystemErr = false;
        }
    }

    public void log(String msg){
        if (isDestinationSet){
            out.println(System.currentTimeMillis() + " " + msg);
            out.flush();
        } else{
            System.err.println("Error: destination has not been set for Logger");
            System.exit(1);
        }
    }
}
