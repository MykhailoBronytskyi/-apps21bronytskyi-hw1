package ua.edu.ucu.tempseries;


import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemperatureSeriesAnalysis {

    private double[] temperature_series= {};
    private int size;
    private int capacity;
//    Нащо тут цей конструктор?

    public TemperatureSeriesAnalysis(){

    }

    private boolean check_if_temperature_is_wrong(double temp) {

        if (temp < -273.15 && temp > Double.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    private boolean check_if_array_is_empty(){
        if (temperature_series.length == 0){
            throw new IllegalArgumentException();
        }
        return false;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        temperature_series = Arrays.copyOf(temperatureSeries, temperatureSeries.length);

        this.check_if_array_is_empty();

        for (double temp : temperature_series) {
            check_if_temperature_is_wrong(temp);
        }
        size = temperature_series.length;
        capacity = size;
    }

    public double average() {

        this.check_if_array_is_empty();

        double sum = 0;
        for (double temp: temperature_series){
            sum += temp;
        }
        double average = sum / temperature_series.length;

        return average;
    }

    public double deviation() {
        double average = average();
        double deviation = 0;

        for (double temp: temperature_series){
            deviation += Math.pow(temp - average, 2);
        }
        deviation = Math.sqrt(deviation/temperature_series.length);

        return deviation;
    }

    public double min() {
       return  findTempClosestToValue(Double.MIN_VALUE);
    }

    public double max() {
        return  findTempClosestToValue(Double.MAX_VALUE);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {

        this.check_if_array_is_empty();

        if (check_if_temperature_is_wrong(tempValue)){
            throw new IllegalArgumentException();
        };

        double pivot = tempValue;
        double closest = 0;

        BigDecimal big_differ = new BigDecimal(10).pow(1000);
        BigDecimal big_pivot = new BigDecimal(pivot);


        for (double temperature: temperature_series){

            BigDecimal big_temp = new BigDecimal(temperature);
            int big_compare = big_temp.subtract(big_pivot).abs().compareTo(big_differ);
            boolean temp_is_closer = big_compare < 0;

            if (temp_is_closer){
                closest = temperature;
            }
            else if (big_compare == 0 && temperature > closest){
                closest = temperature;
            }
        }
        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        BigDecimal pivot = new BigDecimal(tempValue);

        List<Double> list_temp = new ArrayList<Double>();

        for (double temp: temperature_series){
            BigDecimal big_temp = new BigDecimal(temp);

            if (big_temp.compareTo(pivot) < 0){
                list_temp.add(temp);
            }
        }
        double[] array_temp = new double[list_temp.size()];
        int idx = 0;
        for (double num: list_temp){
            array_temp[idx] = list_temp.get(idx);
            idx++;
        }
        return array_temp;
    }

    public double[] findTempsGreaterThen(double tempValue) {

        double[] temp_array_less = findTempsLessThen(tempValue);
        double[] temp_array_greater = new double[temp_array_less.length];

        int pointer_less = 0;
        int pointer_greater = 0;

        for (double temp: temperature_series){
            if (temp == temp_array_less[pointer_less]) {
                pointer_less++;
            }else {
                temp_array_greater[pointer_greater] = temp;
                pointer_greater++;
            }
        }
        return temp_array_greater;
    }

    public TempSummaryStatistics summaryStatistics() {
        this.check_if_array_is_empty();
        return new TempSummaryStatistics( this.average(), this.deviation(),this.min(),this.max());
    }

    public int addTemps(double... temps) {

        for (double temp: temps){
            check_if_temperature_is_wrong(temp);
        }

        while (capacity > size + temps.length){
            capacity = capacity*2 + 1;
        }
        size = size + temps.length;


        double[] new_temp_array = new double[capacity];
        int idx = 0;

        for (double temp: temperature_series){
            new_temp_array[idx] = temp;
            idx++;
        }
        for (double temp: temps){
            new_temp_array[idx] = temp;
            idx++;
        }
        return size;
    }

    @Override
    public String toString() {
        return "TemperatureSeriesAnalysis{" +
                "temperature_series=" + Arrays.toString(temperature_series) +
                '}';
    }

    public static void main(String[] args) {
        int arr[] = {1,2,3};
        double value = Math.pow(10,15);
        double val_2 = Math.pow(10,10);
//        System.out.println(new BigDecimal(value + val_2));
//        System.out.println(BigDecimal.valueOf(value).add(BigDecimal.valueOf(-91)).compareTo(BigDecimal.valueOf(value)));
////        System.out.println(BigDecimal.valueOf(value).compareTo(BigDecimal.valueOf(value)) ==0);
////        System.out.println(value == value);
////        System.out.println((double) 1.0);
////        System.out.println( BigDecimal.valueOf(10).pow(308));
//        System.out.println(BigDecimal.valueOf(Double.MAX_VALUE).compareTo(BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.valueOf(-1))));
    }
}
