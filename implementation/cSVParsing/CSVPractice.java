package cSVParsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVPractice {

	public static void main(String[] args) {

        String csvFile = "";// paste wherever your CSV is here
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int i = 1;
            while ((line = br.readLine()) != null && i < 11) {

                // use comma as separator
                //String[] country = line.split(cvsSplitBy);
            	String[] stuff = line.split(cvsSplitBy);
            	
            	System.out.print(i + ": ");
            	for (String str : stuff)
            		System.out.print(str + " ");
            	i++;
            	System.out.println("");
                //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}