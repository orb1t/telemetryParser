package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class ReadCVS {

    private static ReadCVS instance = new ReadCVS("");

    //Input file which needs to be parsed
    private String fileToParse = "";
    //private BufferedReader fileReader = null;

    private static int csvRowsCount = 0;
    private static int csvColsCount = 0;

    private static ArrayList<ArrayList<String>> csvDataa;// = new ArrayList<ArrayList<Integer>>();
    private static ArrayList<String> csvHeaderItems;// = new ArrayList<Integer>();

    //public String[] csvHeaderItms = null;
    //public String[][] csvData = null;

    public static ArrayList<ArrayList<String>> getData() {
        return csvDataa;
    }

    public static ArrayList<String> getHeaderItems(){
        return csvHeaderItems;
    }


    public static int getRowsCount() {
        if ( csvDataa != null )
            return  csvDataa.size();
        else return csvRowsCount;
    }

    public static  int getColumnCount(){
        if ( csvHeaderItems != null )
            return csvHeaderItems.size();
        else return csvColsCount;
    }

    public static ReadCVS getInstance(){
        return instance;
    }

    private ReadCVS(String path) {
        fileToParse = path;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder csvb = new StringBuilder();

        for (String st : csvHeaderItems) {
            sb.append(st + ", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n");
        csvb.append ( sb );

        for (ArrayList<String>  csv : csvDataa) {
            sb = new StringBuilder();
            for (String ci : csv) {
                sb.append(ci + ", ");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("\n");
            csvb.append ( sb );
        }
        return csvb.toString();
    }

     private void initCsv(String path){
        fileToParse = path;
        csvRowsCount = 0;
        csvColsCount = 0;
        //Create the file reader
            try (BufferedReader fileReader = new BufferedReader(new FileReader(fileToParse)) ) {

                String line = "";
                if ((line = fileReader.readLine()) != null) {
                    csvHeaderItems = new ArrayList<String> ();
                    csvHeaderItems.add( "Num " );
                    Collections.addAll(csvHeaderItems, line.split(","));
                    csvColsCount = csvHeaderItems.size();
                }

                while ((line = fileReader.readLine()) != null) {
                    if (!line.equals("")) {
                        csvRowsCount++;
                    }
                }
                csvRowsCount--;// += 5;
        }catch ( Exception e){
            e.printStackTrace();
        } finally {
            System.out.print ( "\n\nCols : " + csvColsCount + " : " + getColumnCount() + "\n" );
            System.out.print ( "\nRows : " + csvRowsCount + " : " + getRowsCount() + "\n\n" );
        }
    }

    public void parse(String path) {
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        initCsv(path);

        csvDataa = new ArrayList<ArrayList<String>> ( getRowsCount() );

        //Create the file reader
        try ( BufferedReader fileReader = new BufferedReader(new FileReader(fileToParse)) ) {
            String line = "";
            int lineNo = 0;
            int dataLineNo = 1;
            //Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                if ( !line.equals("")) {
                    ArrayList<String> dataLineItems = new ArrayList<String>(getColumnCount());
                    dataLineItems.add ( "" + ( dataLineNo - 1 ));
                    Collections.addAll(dataLineItems, line.split(","));
                    csvDataa.add ( dataLineNo - 1, dataLineItems);
                    if ( lineNo != 0 ) dataLineNo++;
                    lineNo++;
                }
            }
            csvDataa.remove(csvDataa.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}