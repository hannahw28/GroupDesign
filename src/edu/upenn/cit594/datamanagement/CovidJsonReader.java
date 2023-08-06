package edu.upenn.cit594.datamanagement;

public class CovidJsonReader extends CovidFileReader{
    private String fileName;

    public CovidJsonReader(String fileName) {
        this.fileName = fileName;
    }
}
