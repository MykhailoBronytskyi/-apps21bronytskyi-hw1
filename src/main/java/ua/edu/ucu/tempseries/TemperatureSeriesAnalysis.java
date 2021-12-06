package ua.edu.ucu.tempseries;


import lombok.Getter;
import java.math.BigDecimal;
import java.util.Arrays;


public class TemperatureSeriesAnalysis {
    private double[] temperature_series = {};
    @Getter
    private int size = 0;
    @Getter
    private int capacity = 0;

    public double[] getTemperature_series() {
        return Arrays.copyOf(temperature_series, this.capacity);
    }

    public TemperatureSeriesAnalysis() {
    }

    private boolean check_if_temperature_is_wrong(double temp) {

        if (temp < -273.15 || temp >= Double.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    private boolean check_if_array_is_empty() {
        if (temperature_series.length == 0) {
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
        for (double temp : temperature_series) {
            sum += temp;
        }
        double average = sum / temperature_series.length;

        return average;
    }

    public double deviation() {
        double average = average();
        double deviation = 0;

        for (double temp : temperature_series) {
            deviation += Math.pow(temp - average, 2);
        }
        deviation = Math.sqrt(deviation / temperature_series.length);

        return deviation;
    }

    public double min() {
        return findTempClosestToValue(-273.15);
    }

    public double max() {
        return findTempClosestToValue(Double.MAX_VALUE);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {

        this.check_if_array_is_empty();

        if (check_if_temperature_is_wrong(tempValue)) {
            throw new IllegalArgumentException();
        }

        double closest = temperature_series[0];

        BigDecimal big_differ = new BigDecimal(10).pow(1000);
        BigDecimal big_pivot = new BigDecimal(tempValue);

        for (double temperature : temperature_series) {

            BigDecimal big_temp = new BigDecimal(temperature);
            int big_compare = big_temp.subtract(big_pivot).abs().compareTo(big_differ);
            boolean temp_is_closer = big_compare < 0;

            if (temp_is_closer) {
                closest = temperature;
                big_differ = big_temp.subtract(big_pivot).abs();
            } else if (big_compare == 0 && temperature > closest) {
                closest = temperature;
            }
        }
        return closest;
    }

    public enum Operator {

        LESS() {
            public boolean apply(double x1, double x2) {
                return x1 < x2;
            }
        },
        GREATER() {
            public boolean apply(double x1, double x2) {
                return x1 > x2;
            }
        };

        public abstract boolean apply(double x1, double x2);
    }

    public double[] findCompared(double tempValue, Operator operator) {
        check_if_array_is_empty();

        BigDecimal pivot = new BigDecimal(tempValue);
        TemperatureSeriesAnalysis new_array = new TemperatureSeriesAnalysis();

        for (double temperature : temperature_series) {
            BigDecimal big_temp = new BigDecimal(temperature);

            if (operator.apply(big_temp.compareTo(pivot), 0)) {
                new_array.addTemps(temperature);
            }
        }
        //Create new array that will be fully filled
        double[] result_array = new double[new_array.size];


        for (int idx = 0; idx < new_array.size; idx++) {
            result_array[idx] = new_array.temperature_series[idx];
        }
        return result_array;
    }

    public double[] findTempsLessThen(double tempValue) {
        return findCompared(tempValue, Operator.LESS);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        return findCompared(tempValue, Operator.GREATER);
    }

    public TempSummaryStatistics summaryStatistics() {
        this.check_if_array_is_empty();
        return new TempSummaryStatistics(this.average(), this.deviation(), this.min(), this.max());
    }

    public int addTemps(double... temps) {

        for (double temp : temps) {
            check_if_temperature_is_wrong(temp);
        }

        while (capacity < size + temps.length) {
            capacity = capacity * 2 + 1;
        }

        double[] new_temp_array = new double[capacity];
        int idx = 0;


        for (int size_idx = 0; size_idx < this.size; size_idx++) {
            new_temp_array[idx] = temperature_series[size_idx];
            idx++;

        }

        for (double temp : temps) {
            new_temp_array[idx] = temp;
            idx++;
        }

        temperature_series = new_temp_array;

        size = size + temps.length;
        return size;
    }

    @Override
    public String toString() {
        return "TemperatureSeriesAnalysis{" +
                "temperature_series=" + Arrays.toString(temperature_series) +
                '}';
    }

}
