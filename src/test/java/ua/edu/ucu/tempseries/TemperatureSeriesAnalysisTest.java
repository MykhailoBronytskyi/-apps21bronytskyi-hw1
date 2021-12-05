package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;


public class TemperatureSeriesAnalysisTest {

    private double[] temperature_series = new double[]{-100,-90,-80,-70,-60,-50,-40.5,-30,-20,-10,0,
                                                        0,10,20,30,40.5,50,60,70,80,100,1000,-200,-90,125};
    private TemperatureSeriesAnalysis statistics = new TemperatureSeriesAnalysis(temperature_series);

    private TemperatureSeriesAnalysis empty_statistics;

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
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }


    @Test
    public void testTestAverage() {
        assertEquals(29.8,  statistics.average(),0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAverage() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.average();
    }


    @Test
    public void testDeviation() {
        assertEquals(210.76095463,  statistics.deviation(),0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyDeviation() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();

        seriesAnalysis.deviation();
    }


    @Test
    public void testMin() {
        assertEquals(-200,  statistics.min(),0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMin() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();

        seriesAnalysis.min();
    }


    @Test
    public void testMax() {
        assertEquals(1000,  statistics.max(),0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMax() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();

        seriesAnalysis.max();
    }


    @Test
    public void testFindTempClosestToZero() {
        assertEquals(0,  statistics.findTempClosestToZero(),0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyClosestToZero() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.findTempClosestToZero();
    }


    @Test
    public void testFindTempClosestToValue() {
        assertEquals(100,  statistics.findTempClosestToValue(100),0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFindTempClosestToValue() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.findTempClosestToValue(100);
    }

//    @Test
//    public void testFindTempsLessThen() {
//        System.out.println( statistics.findTempsLessThen(-273));
//        assertArrayEquals(new double[]{1,2,3,4},  statistics.findTempsLessThen(100),0.00001);
//    }
//    @Test(expected = IllegalArgumentException.class)
//    public void testEmptyFindTempsLessThen() {
//        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
//        seriesAnalysis.findTempsLessThen(100);
//    }

//
//    @Test
//    public void testFindTempsGreaterThen() {
//        System.out.println(statistics.findTempsGreaterThen(100));
//        assertArrayEquals(new double[]{1,2,3,4},  statistics.findTempsGreaterThen(100),0.00001);
//    }
//    @Test
//    public void testEmptyFindTempsGreaterThen() {
//        statistics.findTempsGreaterThen(100);
//    }



    @Test
    public void testSummaryStatistics() {
        assertEquals("TempSummaryStatistics{avgTemp=29.8, devTemp=210.7609546381872, minTemp=-200.0, maxTemp=1000.0}",statistics.summaryStatistics().toString());
    }


    @Test
    public void testAddTemps() {

        // setup input data
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        int previous_size = seriesAnalysis.getSize();

        seriesAnalysis.addTemps();
        int new_size = seriesAnalysis.getSize();

        assertEquals(previous_size, new_size);

    }
    @Test
    public void testTestToString() {

        assertEquals("TemperatureSeriesAnalysis{temperature_series=[-100.0, -90.0, -80.0, -70.0, -60.0, -50.0, -40.5, -30.0, -20.0, -10.0, 0.0, 0.0, 10.0, 20.0, 30.0, 40.5, 50.0, 60.0, 70.0, 80.0, 100.0, 1000.0, -200.0, -90.0, 125.0]}",  statistics.toString());
    }
}
