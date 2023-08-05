package edu.upenn.cit594.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AverageMarketValueCalculator implements AverageCalculator{
    private Map<Integer, Integer> memo = new HashMap<>();
    @Override
    public int calculateAverage(int zipcode, List<Property> properties) {
        if (memo.containsKey(zipcode)){
            return memo.get(zipcode);
        }

        // if not already memoized
        double total = 0;
        int count = 0;

        for (Property p : properties){
            if (p.getZipcode() == zipcode && isNumeric(p.getMarketValue())){
                total += Integer.parseInt(p.getMarketValue());
                count++;
            }
        }

        int average = count>0 ? (int) (total/count) : 0;
        memo.put(zipcode, average);

        return average;
    }

    private boolean isNumeric(String str){
        if (str == null){
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
