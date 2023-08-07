package edu.upenn.cit594.util;

public class Tallies {
    private final int key;
    private final String value;

    public Tallies(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
}
