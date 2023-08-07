package edu.upenn.cit594.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AverageLivableAreaCalculator implements AverageCalculator{
    private Map<Integer, Integer> memo = new HashMap<>();

    @Override
    public int calculateAverage(int zipcode, List<Property> properties) {
        if (memo.containsKey(zipcode)){
            return memo.get(zipcode);
        }

        // if not memoized, proceed
        double total = 0;
        int count = 0;

        for (Property p : properties){
            if (p.getZipcode() == zipcode && isNumeric(p.getTotalLivableArea())){
                total += Double.parseDouble(p.getTotalLivableArea());
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
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
