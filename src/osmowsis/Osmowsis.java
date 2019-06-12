/*
 * PROGRAM      : OSMOWSIS - ASSIGNMENT 3
 * COURSE       : CS 6310 - SOFTWARE ARCHITECTURE AND DESIGN
 * AUTHOR       : SIDHARTHA CHAKRAVARTY
 * DATE         : 06.12.2019
 */
package osmowsis;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Osmowsis {
    // VARIABLES //
    private static ArrayList<String> strInstructions = new ArrayList();
    private static Lawn lawn;
    /**
     * ALGORITHM:
     * 1. Read CSV file stored in current directory
     * 2. Load parameters from CSV file
     * 3. Initiate Mower
     * 4. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Look up contents of current folder
        lookupFiles();
    }
    
    /**
     * Objective: Scan current folder and list all CSV files
     */
    private static void lookupFiles() {
        Scanner input = new Scanner(System.in);
        ArrayList strFilePaths = new ArrayList();        
        boolean blnCorrectFileNumber = false;
        int intFile = 1;
        File curDir = new File(System.getProperty("user.dir"));
        File[] fileList = curDir.listFiles();
        System.out.println("Please select the input file by entering the number below");
        System.out.println("Current Directory: " + curDir.getPath());
        for(File f : fileList) {
            if(f.isFile() && f.getName().contains(".csv")) {
                System.out.println("File " + intFile + ": " + f.getName());
                String strFilePath = curDir.getPath() + "\\" + f.getName();
                strFilePaths.add(strFilePath);
                intFile++;
            }
        }
        intFile--;
        System.out.println("");
        System.out.println("Total CSV Files Found - " + intFile);
        
        if(intFile == 0) {
            System.err.println("No CSV files were found. Exiting program.");
            System.exit(0);
        } else {
            do {
                try {
                    System.out.print("Please enter the file number that you would like to open: ");                    
                    int fileNumberSelected = input.nextInt();
                    if (fileNumberSelected < 1 || fileNumberSelected > intFile) {
                        System.out.println("");
                        System.err.println("Please select a valid file number. You can choose between 1 and " + intFile + ".");
                        input.nextLine();
                    } else {
                        blnCorrectFileNumber = true;
                        System.out.println("Congratulations! You won an ice-cream treat!");
                        openCSVFile(strFilePaths.get(fileNumberSelected - 1).toString());
                    }
                } catch (InputMismatchException ime) {
                    System.err.println("Please enter a valid file number between 1 and " + intFile);
                    blnCorrectFileNumber = false;
                    input.nextLine();
                }
            } while (blnCorrectFileNumber == false);
        }
    }
    
    /**
     * Objective: Opens the chosen CSV file
     * @param fileNumberSelected
     * @param strFilePaths 
     */
    private static void openCSVFile(String strFilePath) {
        System.out.println("Open File: " + strFilePath);
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        
        try {
            br = new BufferedReader(new FileReader(strFilePath));
            while ((line = br.readLine()) != null) {
                strInstructions.add(Arrays.toString(line.split(csvSplitBy)));
            }
            strInstructions.forEach(System.out::println);
            br.close();
            getLawnDimensions();
        } catch (Exception e) {
            System.err.println("Error loading input file contents: " + e.getMessage());
        }
    }
    
    /**
     * OBJECTIVE: This method checks to make sure that the dimensions provided
     * in the file is legal
     */
    private static void getLawnDimensions() {
        System.out.println("Width: " + strInstructions.get(0));
        System.out.println("Height: " + strInstructions.get(1));
        int intLawnHeight = 0;
        int intLawnWidth = 0;
        try {
            intLawnHeight = Integer.parseInt(retrieveDimension(strInstructions.get(1)));
            intLawnWidth = Integer.parseInt(retrieveDimension(strInstructions.get(0)));
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Parameters found: " + nfe.getMessage());
        }
        
        if(intLawnWidth <= 0 || intLawnHeight <= 0) {
            System.err.println("Invalid Fence Dimensions");
            System.err.println("Width: " + intLawnWidth);
            System.err.println("Height: " + intLawnHeight);
        } else {
            //String strLawn[][] = new String[intLawnWidth][intLawnHeight];
            lawn = new Lawn(intLawnWidth, intLawnHeight);
            printLawn(intLawnHeight, intLawnWidth);            
            
        }

        
    }

    /**
     * OBJECTIVE: This method prints the dimensions of the lawn
     * @param intLawnHeight
     * @param intLawnWidth 
     */
    private static void printLawn(int intLawnHeight, int intLawnWidth) {
        
        for (int intH = 0; intH < intLawnHeight; intH++) {
            for (int intW = 0; intW < intLawnWidth; intW++) {
                System.out.print("|  ");
            }
            System.out.println("|");
        }
    }

    /**
     * OBJECTIVE: This method retrieves the lawn dimensions
     * Original value - [x]
     * Return value - x
     * @param strValue
     * @return 
     */
    private static String retrieveDimension(String strValue) {
        String strTemp;
            strTemp = strValue.trim();
            strTemp = strTemp.substring(1);
            strTemp = String.valueOf(strTemp.charAt(0));
            System.out.println(strTemp);
        return strTemp;
    }
}
