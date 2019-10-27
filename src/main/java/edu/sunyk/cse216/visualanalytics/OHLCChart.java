
package edu.sunyk.cse216.visualanalytics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import com.opencsv.CSVReader;
import static edu.sunyk.cse216.visualanalytics.VolumeChart.compareDate;
import static edu.sunyk.cse216.visualanalytics.VolumeChart.endDate;
import static edu.sunyk.cse216.visualanalytics.VolumeChart.fileRoot;
import static edu.sunyk.cse216.visualanalytics.VolumeChart.startDate;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Locale;
import org.jfree.data.time.Day;
/**
 * @author 
 */
public class OHLCChart extends JFrame {
  
    private static final long serialVersionUID = 6294689542092367723L;
    
    public static String fileName;
    public static File fileRoot;
    public static String startDate;
    public static String endDate;
    public static String user;
    public static int countLine;
  
  public OHLCChart(String title) {
    super(title);

    // Create dataset
    OHLCDataset dataset = createDataset();

    // Create chart
    JFreeChart chart = ChartFactory.createHighLowChart(
        "OHLC Stock chart | "+user, 
        "Date", "Price", dataset, true);
    
    XYPlot plot = (XYPlot) chart.getPlot();
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setAutoRangeIncludesZero(false);

    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  private OHLCDataset createDataset() {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    int j=0;       
    int k=0;
    try{            
            CSVReader reader = new CSVReader(new FileReader(fileRoot));           
            String title2[] = reader.readNext();
            String contents[];
            
            while((contents = reader.readNext())!=null){
                for(int i=0;i<title2.length;i++){     
                    if(i==0){
                        if(compareDate(contents[0])==1){
                            k++;
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

    OHLCDataItem dataItem[] = new OHLCDataItem[k];
    
    try{            
            CSVReader reader = new CSVReader(new FileReader(fileRoot));           
            String title2[] = reader.readNext();
            String contents[];
            
            while((contents = reader.readNext())!=null){
                for(int i=0;i<title2.length;i++){     
                    if(i==0){
                        if(compareDate(contents[0])==1){
                            
                            String a=contents[0];
                            double b=Double.parseDouble(contents[1]);
                            double c=Double.parseDouble(contents[2]);
                            double d=Double.parseDouble(contents[3]);
                            double e=Double.parseDouble(contents[4]);
                            double f=Double.parseDouble(contents[5]);
                            
                            //System.out.println(a+" "+b+" "+c+" "+d+" "+e+" "+f + " " +k +" " + j);    
                            dataItem[j] = new OHLCDataItem(format.parse(a),b,c,d,e,f);
                            j++;
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

    OHLCDataset dataset = new DefaultOHLCDataset(user+"", dataItem);
    return dataset;
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

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      OHLCChart example = new OHLCChart(
          "OHLC Chart | "+user);
      example.setSize(800, 400);
      example.setLocationRelativeTo(null);
      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      example.setVisible(true);
    });
  }
}