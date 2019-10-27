/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sunyk.cse216.visualanalytics;


import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.time.Day;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import com.opencsv.CSVReader;
import static edu.sunyk.cse216.visualanalytics.HighLowChart.compareDate;
import static edu.sunyk.cse216.visualanalytics.HighLowChart.endDate;
import static edu.sunyk.cse216.visualanalytics.HighLowChart.fileRoot;
import static edu.sunyk.cse216.visualanalytics.HighLowChart.startDate;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;

public class VolumeChart extends ApplicationFrame {

    /**
     * A chart mockup based on JFreeChart's TimeSeriesDemo1 demonstation.
     *
     * @param title - the frame title.
     */
    
    public static String fileName;
    public static File fileRoot;
    public static String startDate;
    public static String endDate;
    public static String user;
    
    public VolumeChart(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }
    
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new java.awt.Dimension(900, 600));
        TimeSeriesCollection dataset = createDataset("Volume Chart | "+user);
        JFreeChart chart2 = createChart2("Volume Chart | "+user, dataset);
        ChartPanel chartPanel2 = new ChartPanel(chart2);
        panel.add(chartPanel2);
        return panel;
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    private static TimeSeriesCollection createDataset(String title) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries s1 = new TimeSeries(title, Day.class);
        
        try{            
            CSVReader reader = new CSVReader(new FileReader(fileRoot));           
            String title2[] = reader.readNext();
            String contents[];
            
            while((contents = reader.readNext())!=null){
                for(int i=0;i<title2.length;i++){     
                    if(i==0){
                        if(compareDate(contents[0])==1){
                            
                            int x=Integer.parseInt(dateCal_month(contents[0]));
                            int y=Integer.parseInt(dateCal_day(contents[0]));
                            int z=Integer.parseInt(dateCal_year(contents[0]));
                            double dataValue=Double.parseDouble(contents[5]);
                            //System.out.println(x + " " + y + " " + z + " : " + dataValue);
                            //System.out.println(contents[0] +" " +dateCal(contents[0]) + " " +Double.parseDouble(contents[5]));          
                            s1.addOrUpdate(new Day(y,x,z), dataValue);

                          }
                        else{
                            //System.out.println("Without : "+contents[0]);
                            
                        }
                    }
                }
            }
        }
        
        catch(Exception ex){           
        }
        //s1.add(new Year(In), Double.parseDouble(contents[5]));
        
        dataset.addSeries(s1);
        dataset.setDomainIsPointsInTime(false);

        return dataset;

    }
    
    public static int compareDate (String date){
        
        String testValue=dateCal2(startDate);
        String inputValue=dateCal2(date);
        String endtestValue=dateCal2(endDate);
        
        int testValueNum=Integer.parseInt(testValue);
        int inputValueNum=Integer.parseInt(inputValue);
        int endValue=Integer.parseInt(endtestValue);
        
        for(int i=0;i<testValue.length();i++){
            if(testValueNum<=inputValueNum && inputValueNum<=endValue){
                return 1;
            }
            else{
                return 0;
            }
        }
        return 0;
    }
    
    private static String dateCal_day(String input_user){
        
        String date=input_user;
        String day="";
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_format = new SimpleDateFormat("dd");

        try {
            Date original_date = original_format.parse(date);        
            String new_date = new_format.format(original_date);
            day=new_date;
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }       
        return day;
    }
    
    private static String dateCal(String input_user){
        
        String date=input_user;
        String day="";
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyyMMdd");

        try {
            Date original_date = original_format.parse(date);        
            String new_date = new_format.format(original_date);
            day=new_date;
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }       
        return day;
    }
    
    
    private static String dateCal_month(String input_user){
        
        String date=input_user;
        String day="";
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_format = new SimpleDateFormat("MM");

        try {
            Date original_date = original_format.parse(date);        
            String new_date = new_format.format(original_date);
            day=new_date;
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }       
        return day;
    }
    
    private static String dateCal_year(String input_user){
        
        String date=input_user;
        String day="";
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy");

        try {
            Date original_date = original_format.parse(date);        
            String new_date = new_format.format(original_date);
            day=new_date;
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }       
        return day;
    }
    
    private static String dateCal2(String input_user){
        
        String date=input_user;
        String day="";
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyyMMdd");

        try {
            Date original_date = original_format.parse(date);        
            String new_date = new_format.format(original_date);
            day=new_date;
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }       
        return day;
    }


    /**
     * Creates a chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return A chart.
     */
    private static JFreeChart createChart2(String title, IntervalXYDataset dataset) {

        //IntervalXYDataset idataset = new XYBarDataset(dataset, 0.5);
        JFreeChart chart = ChartFactory.createXYBarChart(
                title,              // title
                "Month",             // x-axis label
                true,               // date axis?
                "Volume",           // y-axis label
                dataset,            // data
                PlotOrientation.VERTICAL,       // orientation
                true,               // create legend?
                true,               // generate tooltips?
                false               // generate URLs?
        );

        // Set chart styles
        chart.setBackgroundPaint(Color.white);

        // Set plot styles
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        // Set date axis style
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateTickUnit unit = new DateTickUnit(DateTickUnit.DAY, 5, formatter);
        axis.setTickUnit(unit);
        axis.setVerticalTickLabels(true);
        
        
        return chart;

    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

        VolumeChart demo = new VolumeChart("Volume Chart");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}