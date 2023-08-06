package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private PrintWriter out;

    private boolean isDestinationSet = false;

    private Logger(){
        out = new PrintWriter(System.err);
    }

    private static Logger instance;

    public static Logger getInstance(){
        if (instance == null){
            instance = new Logger();
        }
        return instance;
    }

    public void setDestination(String filename) throws IOException {
        if (filename == null || filename.isEmpty()){
            throw new IOException();
        }
        if (isDestinationSet && out != null){
            out.close();
        }
        FileWriter fw = new FileWriter(filename, true);
        out = new PrintWriter(fw, true);
        isDestinationSet = true;
    }

    public void log(String msg){
        out.println(System.currentTimeMillis() + " " + msg);
        out.flush();
    }

    public void close(){
        if (out != null){
            out.close();
        }
    }
}
