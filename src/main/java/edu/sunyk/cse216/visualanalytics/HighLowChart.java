/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sunyk.cse216.visualanalytics;

/* ----------------------------
 * MinMaxCategoryPlotDemo1.java
 * ----------------------------
 * (C) Copyright 2002-2009, by Object Refinery Limited.
 *
 */

import javax.swing.JPanel;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;




/**
 * A simple demonstration application showing how to create a min/max category
 * plot.
 */
public class HighLowChart extends ApplicationFrame {

    public static String fileName;
    public static File fileRoot;
    public static String startDate;
    public static String endDate;
    public static String user;
    
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public HighLowChart(String title) {
        super(title);
        
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 600));
        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset for the demo.
     *
     * @return A dataset.
     */
    
    public static CategoryDataset createDataset() {
        
        //System.out.println(fileRoot+"");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();      
        try{            
            CSVReader reader = new CSVReader(new FileReader(fileRoot));
            String title[] = reader.readNext();
            String contents[];
            
            
            while((contents = reader.readNext())!=null){
                for(int i=0;i<title.length;i++){                   
                    
                    if(compareDate(contents[0])==1){
                       if(i==0){
                           //System.out.println(contents[0] +" " +contents[2] + " " +contents[3]);
                           dataset.addValue(Double.parseDouble(contents[2]), "High", dateCal(contents[0]));
                           dataset.addValue(Double.parseDouble(contents[3]), "Low", dateCal(contents[0]));     
                       }
                                         
                       //System.out.println(contents[0] + " " + contents[2] + " " +contents[3]);
                       
                    }            
                }
            }
        }
        catch(Exception ex){           
        }
        
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
    
    private static String dateCal(String input_user){
        
        String date=input_user;
        String day="";
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", new Locale("en", "US"));

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
     * Creates the chart to display for the demo.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    public static JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart(
            "High Low Chart | "+user,  // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRangePannable(true);
        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
        renderer.setDrawLines(false);
        plot.setRenderer(renderer);
        
        CategoryAxis  axis2 = plot.getDomainAxis();
        axis2.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/2));

         
        ChartUtilities.applyCurrentTheme(chart);
        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

   /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        HighLowChart demo = new HighLowChart(
                "High Low Chart | "+user);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}