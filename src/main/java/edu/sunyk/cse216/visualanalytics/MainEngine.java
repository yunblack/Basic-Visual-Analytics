package edu.sunyk.cse216.visualanalytics;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.*;
import java.text.*;
import java.util.Locale;
import java.util.Arrays;

import java.io.FileReader;
import com.opencsv.CSVReader;
import java.util.Scanner;

/**
 *
 * @author Dr. Pravin Pawar, SUNY Korea CS
 */
public class MainEngine {
    
    HashMap<String, List<String>> tickerMap = new HashMap<String, List<String>>(); //Creates alphabetical listing of tickers
    HashMap<String, File> fileMap = new HashMap<String, File>(); //Creates mapping of ticker and corresponding file
    
    public File files[];
     /**
     * @param args the command line arguments
     */
    public String fileName;
    public File fileRoot;
    public String startDate;
    public String endDate;
    public String user;
    
    public String Xnew_date;
    public String Xold_date;
    
    public static String[] argg;
    
    public static void main(String[] args) {
            MainEngine mainEngine = new MainEngine();
            System.out.println("Arguments are: " + args[0]);
            
            mainEngine.scanDir(args[0]);
            mainEngine.tickerMap.forEach((key, value) -> System.out.println(key));        
            //mainEngine.fileMap.forEach((key, value) -> System.out.println(key + ":" + value));
            argg=args;
            System.out.println();
            
            mainEngine.userInteraction();
            
    }
    
    public void scanDir(String folderPath){
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        files=listOfFiles;
        
        for (File file : listOfFiles) {
            if (file.isFile()) {      
                updateTickerMap(file.getName().trim());   
                updateFileMap(file);
            }
        }       
    }
    
    private void updateTickerMap(String filename){
        StringTokenizer stkz = new StringTokenizer(filename, ".");
        List<String> tickersList = new ArrayList<>();
        if (tickerMap.containsKey(filename.substring(0, 1))) {
            tickersList = tickerMap.get(filename.substring(0, 1));
            tickersList.add(stkz.nextToken());
            tickerMap.remove(filename.substring(0, 1));
            tickerMap.put(filename.substring(0, 1), tickersList);
        } else {
            tickersList.add(stkz.nextToken());
            tickerMap.put(filename.substring(0, 1), tickersList);
        }
    }
    
    
    private void updateFileMap(File file){
        StringTokenizer stkz = new StringTokenizer(file.getName(), ".");
        fileMap.put(stkz.nextToken(), file);
    }

  
    private void userInteraction() {
        
        HashMap<String, List<String>> tickerMap = new HashMap<String, List<String>>(); //Creates alphabetical listing of tickers
        HashMap<String, File> fileMap = new HashMap<String, File>(); //Creates mapping of ticker and corresponding file
        
        System.out.print("Enter ticker initial: ");
        Scanner sc1 = new Scanner(System.in);
        String input_user1 = sc1.nextLine();
        
        if(input_user1.length()!=1){
            userInteraction();
        }
        else if(input_user1.charAt(0)>='a' && input_user1.charAt(0)<='z'){
        }
        else{
             userInteraction();
        }
        

        this.tickerMap=tickerMap;
        this.fileMap=fileMap;
        
        for(File file1 : files){
            if(input_user1.charAt(0) == (file1.getName()).charAt(0)){
                String x=file1.getName();
                System.out.println(x.replace(".us.txt",""));
                updateTickerMap(file1.getName().trim());   
                updateFileMap(file1);
            }
            
        }
//   
//        this.fileMap.forEach((key, value) -> {
//            System.out.println(key);
//                });
//       
        
        enterTickerCode();
       
        
    }
    
    public void enterTickerCode(){
        
        System.out.print("Enter ticker code: ");
        Scanner sc2 = new Scanner(System.in);
        String input_user2 = sc2.nextLine();
        
        if(fileMap.containsKey(input_user2)){
            this.fileMap.forEach((key, value) -> saveData(key,value,input_user2));
            this.fileMap.forEach((key, value) -> println(key,value,input_user2));
        }
        else{
            System.out.println("Invalid ticker Code");
            enterTickerCode();
        }

    }
    
    public void saveData(String key, File value, String input_user2){
        this.fileName=key;
        //this.fileRoot=value;
        this.user=input_user2;
        
        if(user.equals(key)){
            this.fileRoot=value;
        }
    }
    public int countLine;
    private void readCSVFile(String tickerCode, File file) {

        int k=0;
        System.out.println();
        System.out.println("Reading file");
       
        String x="";
        String y="";
        
        String path=file.getAbsolutePath();
        System.out.println(path);
        try{
            int line_read = 0;
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] title = reader.readNext();
            String[] contents;
            
            String old_date="";
            String new_date="";
            
            while((contents=reader.readNext())!=null){
                line_read++;
                for(int i=0;i<title.length;i++){
                    if(line_read==1){;
                        old_date=contents[0];
                        x=old_date;
                        Xold_date=x;
                    }
                    else{
                        new_date=contents[0];
                        y=new_date;
                        Xnew_date=y;
                    }
                    //System.out.print(title[i] + " : " + contents[i] + " ");
                } 
                k++;
            }       
            System.out.println();
            System.out.println("StartDate = " + old_date + " EndDate = " + new_date);
            countLine=k;
        }
        catch(Exception ex){      
        }
        
        enterStartDate(x, y);
        enterEndDate(x, y);
        
        System.out.println();
        System.out.println("StartDate = " + startDate+" EndDate = "+endDate);  
        input_Graph();
    }
    
    public void input_Graph(){
        System.out.println("Enter chart type number from the following: ");
        System.out.println("1. High-Low Chart");
        System.out.println("2. Open-High-Low-Close chart");
        System.out.println("3. Volume Chart");
        
        Scanner graph_num = new Scanner(System.in);
        String z=graph_num.nextLine();
        
        if(z.length()==1){
            if(z.charAt(0)>='a' && z.charAt(0)>='z'){
                input_Graph();
            }
            else if(z.charAt(0)>='A' && z.charAt(0)>='Z'){
                input_Graph();
            }
            else if(Integer.parseInt(z)==1 || Integer.parseInt(z)==2 || Integer.parseInt(z)==3){
                openGraph(Integer.parseInt(z));
            }
            else{
                input_Graph();
            }
        }
        else{
            input_Graph();
        }
        
    }
    
    public void enterStartDate(String old_d, String new_d){
        
        System.out.println("Enter valid startdate in the format YYYY-MM-DD: ");
        Scanner sc = new Scanner(System.in);
        String input_user = sc.nextLine();
        
        
        
        if(input_user.contains("a") || input_user.contains("b") || input_user.contains("c") || input_user.contains("d") || input_user.contains("e") || input_user.contains("f") || input_user.contains("g")
                 || input_user.contains("h") || input_user.contains("i") || input_user.contains("j") || input_user.contains("k") || input_user.contains("l") || input_user.contains("m") || input_user.contains("n")
                 || input_user.contains("o") || input_user.contains("p") || input_user.contains("q") || input_user.contains("r") || input_user.contains("s") || input_user.contains("t") || input_user.contains("u")
                 || input_user.contains("v") || input_user.contains("w") || input_user.contains("x") || input_user.contains("y") || input_user.contains("z")){
                enterStartDate(old_d, new_d);
        }
        
        else if(input_user.length()!=10){
            enterStartDate(old_d,new_d);
        }
        else if(!input_user.contains("-")){
            enterStartDate(old_d,new_d);
        }

        else{
            
            String year=input_user.split("-")[0];
            String month=input_user.split("-")[1];
            String day=input_user.split("-")[2];
            
            String oldYear=old_d.split("-")[0];
            String oldMon=old_d.split("-")[1];
            String oldDay=old_d.split("-")[2];
            
            String newYear=new_d.split("-")[0];
            String newMon=new_d.split("-")[1];
            String newDay=new_d.split("-")[2];
            
//            System.out.println(year + " "+ month + " " + day);
//            System.out.println(oldYear + " "+ oldMon + " " + oldDay);
//            System.out.println(newYear + " "+ newMon + " " + newDay);
            
            if(Integer.parseInt(month)>12 || Integer.parseInt(month)<1){
                //System.out.println("Invalid Month Value");
                enterStartDate(old_d, new_d);
            }
            else if(Integer.parseInt(day)>31 || Integer.parseInt(day)<1){
                //System.out.println("Invalid Day Value");
                enterStartDate(old_d, new_d);
            }
            else{
                if(Integer.parseInt(year)<Integer.parseInt(oldYear)){
                    //System.out.println("Invalid Year Value (Error 01)");
                    enterStartDate(old_d, new_d);
                }
                else if(Integer.parseInt(year)==Integer.parseInt(oldYear)){
                    
                    if(Integer.parseInt(month)<Integer.parseInt(oldMon)){
                        //System.out.println("Invalid Month Value (Error 02)");
                        enterStartDate(old_d, new_d);
                    }
                    else if(Integer.parseInt(month)==Integer.parseInt(oldMon)){
                        if(Integer.parseInt(day)<Integer.parseInt(oldDay)){
                            //System.out.println("Invalid Day Value (Error 03)");
                            enterStartDate(old_d, new_d);
                        }
                        else{         
                            String startDate=dateCal(input_user);
                            this.startDate=input_user;
                        }
                    }
                    else{
                        String startDate=dateCal(input_user);
                        this.startDate=input_user;
                    }
                    
                }
                else{
                    
                    if(Integer.parseInt(year)>Integer.parseInt(newYear)){
                                enterStartDate(old_d, new_d);
                            }
                    else if(Integer.parseInt(year)==Integer.parseInt(newYear)){
                            if(Integer.parseInt(month)>Integer.parseInt(newMon)){
                               enterStartDate(old_d, new_d);
                            }
                                else if(Integer.parseInt(month)==Integer.parseInt(newMon)){
                                    if(Integer.parseInt(day)>Integer.parseInt(newDay)){
                                        enterStartDate(old_d, new_d);
                                    }
                                    else{
                                        String startDate=dateCal(input_user);
                                        this.startDate=input_user;
                                    }                    
                                }
                                else{
                                    String startDate=dateCal(input_user);
                                        this.startDate=input_user;
                                }
                            } 
                    else{
                        String startDate=dateCal(input_user);
                        this.startDate=input_user;
                    }
                }
            }
        }

    }
    public void enterEndDate(String old_d, String new_d){
        System.out.println("Enter valid enddate in the format YYYY-MM-DD: ");
        Scanner scw = new Scanner(System.in);
        String input_user = scw.nextLine();
        //System.out.println(dateCal(input_user2));
        
        if(input_user.contains("a") || input_user.contains("b") || input_user.contains("c") || input_user.contains("d") || input_user.contains("e") || input_user.contains("f") || input_user.contains("g")
                 || input_user.contains("h") || input_user.contains("i") || input_user.contains("j") || input_user.contains("k") || input_user.contains("l") || input_user.contains("m") || input_user.contains("n")
                 || input_user.contains("o") || input_user.contains("p") || input_user.contains("q") || input_user.contains("r") || input_user.contains("s") || input_user.contains("t") || input_user.contains("u")
                 || input_user.contains("v") || input_user.contains("w") || input_user.contains("x") || input_user.contains("y") || input_user.contains("z")){
                enterEndDate(old_d, new_d);
        }
        
         else if(input_user.length()!=10){
            enterEndDate(old_d,new_d);
        }
        else if(!input_user.contains("-")){
            enterEndDate(old_d,new_d);
        }
        
        else{
            
            String year=input_user.split("-")[0];
            String month=input_user.split("-")[1];
            String day=input_user.split("-")[2];
            
            String oldYear=old_d.split("-")[0];
            String oldMon=old_d.split("-")[1];
            String oldDay=old_d.split("-")[2];
            
            String newYear=new_d.split("-")[0];
            String newMon=new_d.split("-")[1];
            String newDay=new_d.split("-")[2];
            
//            System.out.println(year + " "+ month + " " + day);
//            System.out.println(oldYear + " "+ oldMon + " " + oldDay);
//            System.out.println(newYear + " "+ newMon + " " + newDay);
            
            if(Integer.parseInt(month)>12 || Integer.parseInt(month)<1){
                //System.out.println("Invalid Month Value");
                enterEndDate(old_d, new_d);
            }
            else if(Integer.parseInt(day)>31 || Integer.parseInt(day)<1){
                //System.out.println("Invalid Day Value");
                enterEndDate(old_d, new_d);
            }
            else{
                if(Integer.parseInt(year)>Integer.parseInt(newYear)){
                    //System.out.println("Invalid Year Value (Error 04)");
                    enterEndDate(old_d, new_d);
                }
                else if(Integer.parseInt(year)==Integer.parseInt(newYear)){
                    
                    if(Integer.parseInt(month)>Integer.parseInt(newMon)){
                        //System.out.println("Invalid Month Value (Error 05)");
                        enterEndDate(old_d, new_d);
                    }
                    else if(Integer.parseInt(month)==Integer.parseInt(newMon)){
                        if(Integer.parseInt(day)>Integer.parseInt(newDay)){
                            //System.out.println("Invalid Day Value (Error 06)");
                            enterEndDate(old_d, new_d);
                        }
                        else{
                            String endDate=dateCal(input_user);
                            this.endDate=input_user;
                        }
                    }
                    else{
                        String endDate=dateCal(input_user);
                        this.endDate=input_user;
                    }
                    
                }
                else{    
                    if(Integer.parseInt(dateCal2(input_user))<Integer.parseInt(dateCal2(this.startDate))){
                        enterEndDate(old_d, new_d);
                    }
                    
                    else if(Integer.parseInt(year)<Integer.parseInt(oldYear)){
                                 enterEndDate(old_d, new_d);
                            }
                    else if(Integer.parseInt(year)==Integer.parseInt(oldYear)){
                                if(Integer.parseInt(month)<Integer.parseInt(oldMon)){
                                     enterEndDate(old_d, new_d);
                                }
                                else if(Integer.parseInt(month)==Integer.parseInt(oldMon)){
                                    if(Integer.parseInt(day)<Integer.parseInt(oldDay)){
                                         enterEndDate(old_d, new_d);
                                    }
                                    else{
                                        String endDate=dateCal(input_user);
                                        this.endDate=input_user;
                                    }                    
                                }
                                else{
                                    String endDate=dateCal(input_user);
                                    this.endDate=input_user;
                                }
                            }   
                    else{
                        String endDate=dateCal(input_user);
                        this.endDate=input_user;
                    }
                }
            }
        }

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
    
    private void println(String key, File value ,String input){
        if(input.equals(key)){
            //System.out.println(key);
            readCSVFile(key,value);
        }
    }
    
    private String dateCal(String input_user){
        
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
    
    public void openGraph(int number){
        if(number==1){
            HighLowChart HL = new HighLowChart(argg[0]);
            
            HL.fileName=this.fileName;
            HL.user=this.user;
            HL.fileRoot=this.fileRoot;
            HL.startDate=this.startDate;
            HL.endDate=this.endDate;
            
            HL.main(argg);
        }
        else if(number==2){
            OHLCChart OH = new OHLCChart(argg[0]);
            
            OH.fileName=this.fileName;
            OH.user=this.user;
            OH.fileRoot=this.fileRoot;
            OH.startDate=this.startDate;
            OH.endDate=this.endDate;
            OH.countLine=countLine;
            OH.main(argg);
        }
        else if(number==3){
            VolumeChart VC = new VolumeChart(argg[0]);
            
            VC.fileName=this.fileName;
            VC.user=this.user;
            VC.fileRoot=this.fileRoot;
            VC.startDate=this.startDate;
            VC.endDate=this.endDate;
            VC.main(argg);
        }
        
        //System.out.println(startDate);
        //System.out.println(endDate);
        

        FinalAns();
    }
    public void FinalAns(){
        
        System.out.println("Continue? (Y/N)");
        Scanner XOR = new Scanner(System.in);
        String comment=XOR.nextLine();
        
        if(comment.charAt(0)=='Y' || comment.charAt(0)=='y'){
            main(argg);
        }
        else if(comment.charAt(0)=='N' || comment.charAt(0)=='n'){
            System.exit(0);
        }
        else{
            System.out.println("Invalid Input");
            FinalAns();
        }
    }
}
