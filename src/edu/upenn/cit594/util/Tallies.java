package edu.upenn.cit594.util;

public class Tallies {
    private final int key;
    private final double value;

    public Tallies(int key, double value) {
        this.key = key;
        this.value = value;
    }

    public int getKey(){
        return key;
    }

    public double getValue(){
        return value;
    }
}
