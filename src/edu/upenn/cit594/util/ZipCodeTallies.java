package edu.upenn.cit594.util;

public class ZipCodeTallies {
    private final int zipcode;
    private final double value;

    public ZipCodeTallies(int zipcode, double value) {
        this.zipcode = zipcode;
        this.value = value;
    }

    public int getZipcode(){
        return zipcode;
    }

    public double getValue(){
        return value;
    }
}
