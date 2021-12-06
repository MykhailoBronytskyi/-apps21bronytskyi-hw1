package ua.edu.ucu.tempseries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import java.util.Arrays;


public class TemperatureSeriesAnalysisTest {

    private double[] temperature_series = new double[]{-100, -90, -80, -70, -60, -50, -40.5, -30, -20, -10, 0,
            0, 10, 20, 30, 40.5, 50, 60, 70, 80, 100.0, 1000, -200, -90, 125};
    private TemperatureSeriesAnalysis statistics = new TemperatureSeriesAnalysis(temperature_series);

    private TemperatureSeriesAnalysis empty_statistics = new TemperatureSeriesAnalysis();

    @Test
    public void testFindClosestToZero() {
        // setup input data and expected result
        double[] temperatureSeries = {-0.2, 0.2, -0.2};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 0.2;

        // call tested method
        double actualResult = seriesAnalysis.findTempClosestToZero();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testAverageWithOneElementArray() {

        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }


    
    @Test
    public void testGetCapacity(){
        assertEquals(25, statistics.getCapacity());
    }

    @Test
    public void testGetSize() {
        assertEquals(25, statistics.getSize());
    }



    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testTestAverage() {
        assertEquals(29.8, statistics.average(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAverage() {
        empty_statistics.average();
    }



    @Test
    public void testDeviation() {
        assertEquals(210.76095463, statistics.deviation(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyDeviation() {
        empty_statistics.deviation();
    }



    @Test
    public void testMin() {
        assertEquals(-200, statistics.min(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMin() {
        empty_statistics.min();
    }



    @Test
    public void testMax() {
        assertEquals(1000, statistics.max(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMax() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.max();
    }



    @Test
    public void testFindTempClosestToZero() {
        assertEquals(0, statistics.findTempClosestToZero(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyClosestToZero() {
        empty_statistics.findTempClosestToZero();
    }



    @Test
    public void testFindTempClosestToValue() {
        assertEquals(-200, statistics.findTempClosestToValue(-210), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValueWithWrongValue() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.findTempClosestToValue(-1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFindTempClosestToValue() {
        empty_statistics.findTempClosestToValue(100);
    }



    @Test
    public void testFindCompared() {
        double[] result = new double[]{-100.0, -90.0, -80.0, -200.0, -90.0};
        assertArrayEquals(result, statistics.findCompared(-70, TemperatureSeriesAnalysis.Operator.LESS), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFindCompared() {
        empty_statistics.findCompared(100, TemperatureSeriesAnalysis.Operator.LESS);
    }



    @Test
    public void testFindTempsLessThen() {
        double[] result = new double[]{-100.0, -90.0, -80.0, -200.0, -90.0};
        assertArrayEquals(result, statistics.findTempsLessThen(-70), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFindTempsLessThen() {
        empty_statistics.findTempsLessThen(100);
    }



    @Test
    public void testFindTempsGreaterThen() {
        statistics.findTempsGreaterThen(100);
        System.out.println(Arrays.toString(statistics.findTempsGreaterThen(100)));
        assertArrayEquals(new double[]{1000.0, 125.0}, statistics.findTempsGreaterThen(100), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFindTempsGreaterThen() {
        empty_statistics.findTempsGreaterThen(100);
    }



    @Test
    public void testSummaryStatistics() {
        assertEquals("TempSummaryStatistics{avgTemp=29.8, devTemp=210.7609546381872, minTemp=-200.0, maxTemp=1000.0}", statistics.summaryStatistics().toString());
    }


    @Test
    public void testAddTemps() {

        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        int previous_size = seriesAnalysis.getSize();
        for (int i = 10; i < 20; i++) {
            seriesAnalysis.addTemps(i);
        }
        int new_size = seriesAnalysis.getSize();

        assertEquals(previous_size + 10, new_size);
    }



    @Test
    public void testTestToString() {
        double[] array = new double[]{1, 2, 3, 4, 5};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(array);
        assertEquals("TemperatureSeriesAnalysis{temperature_series=[1.0, 2.0, 3.0, 4.0, 5.0]}",
                seriesAnalysis.toString());
    }
}
