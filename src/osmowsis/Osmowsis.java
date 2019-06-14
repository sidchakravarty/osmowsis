/*
 * PROGRAM      : OSMOWSIS - ASSIGNMENT 3
 * COURSE       : CS 6310 - SOFTWARE ARCHITECTURE AND DESIGN
 * AUTHOR       : SIDHARTHA CHAKRAVARTY
 * DATE         : 06.12.2019
 */
package osmowsis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Osmowsis extends Move{
    // VARIABLES //
    private static int intMovesCount;
    private static int intScanCount;
    private static ArrayList<String> strInstructions = new ArrayList();
    private static Lawn lawn;
    private static Mower mower;
    /**
     * ALGORITHM:
     * 1. Read CSV file stored in current directory
     * 2. Load parameters from CSV file
     * 3. Initiate Mower
     * 4. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Inform user about the program
        System.out.println("*** CS 6310 - Software Architecture & Design ***");
        System.out.println("------------------------------------------------");
        System.out.println("");
        System.out.println("Objective: To design and implement lawn mower program");
        System.out.println("Author: Sidhartha Chakravarty");
        System.out.println("------------------------------------------------");
        System.out.println("");
        System.out.println("Instructions: ");
        System.out.println(" 1. The program will present a list of CSV files that are stored in the current directory.");
        System.out.println(" 2. Select the file, by entering the number next to the file name. ");
        System.out.println(" 3. By design, the program adds extra rows and columns to indicate a fence.");
        System.out.println(" 4. For instance, if the input file provides the dimensions - 4 x 4, the program will create a 6 x 6 grid, with fence all over");
        System.out.println("");
        System.out.println("------------------------------------------------");        
        System.out.println("");
        System.out.println("");        
        
        // Look up contents of current folder
        lookupFiles();
        addDecorations("Mower");
        addDecorations("Crater");
        lawn.addGrass();
        printLawn(lawn.getIntLawnWidth(), lawn.getIntLawnHeight());
        
        // Initial Scan
        scanSurroundings(mower.getIntX(), mower.getIntY());
        // Play Game
        intMovesCount = 0;
        intScanCount = 0;
        playGame();
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
            System.exit(0);
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
            lawn = new Lawn(intLawnWidth + 2, intLawnHeight + 2);
        }

        
    }

    /**
     * OBJECTIVE: This method prints the dimensions of the lawn
     * @param intLawnHeight
     * @param intLawnWidth 
     */
    private static void printLawn(int intLawnWidth, int intLawnHeight) {
        String strLastDecoration = "";
        for (int intH = 0; intH < intLawnHeight; intH++) {
            for (int intW = 0; intW < intLawnWidth; intW++) {
                if(strLastDecoration.contains("crater")) {
                    System.out.print("  |   ");                                        
                } else {
                    System.out.print("   |   ");                    
                }

                strLastDecoration = lawn.whatsOnTheLawn(intW, intH);
                System.out.print(strLastDecoration);
                
            }
            System.out.println("   |");
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

    /**
     * OBJECTIVE: Add Craters and Mowers to the lawn
     * @param strDecoration 
     */
    private static void addDecorations(String strDecoration) {
        int intMowerX;
        int intMowerY;
        int intCraterX;
        int intCraterY;
        String strMowerDirection; 
        int numberOfCraters = 0;
        switch (strDecoration) {
            
            case "Mower":
                intMowerX = mowerXY (strInstructions.get(3), "Width");
                intMowerY = mowerXY (strInstructions.get(3), "Height");
                strMowerDirection = mowerDirection (strInstructions.get(3));
                mower = new Mower(intMowerX + 1, intMowerY + 1, "Active", strMowerDirection);
                lawn.addMower(intMowerX + 1, intMowerY + 1);
                break;
                
            case "Crater":
                numberOfCraters = Integer.parseInt(retrieveDimension(strInstructions.get(4)));
                if(numberOfCraters > 0) {
                    for (int i = 0; i < numberOfCraters; i++) {
                        //System.out.println("Crater: " + strInstructions.get(i+5));
                        intCraterX = CraterXY (strInstructions.get(i+5), "X");
                        intCraterY = CraterXY (strInstructions.get(i+5), "Y");
                        lawn.addCrater(intCraterX + 1, intCraterY + 1);
                    }                    
                }
                break;
        }
    }
    
    private static int CraterXY(String strValue, String strDim) {
        int intValue = 0;
        String strTemp = "";      
        try {
            if (strDim.contains("X")) {
                strTemp = strValue.trim();
                strTemp = strTemp.substring(1, strTemp.indexOf(","));
            } else if (strDim.contains("Y")) {
                strTemp = strValue.trim();
                int start = strTemp.indexOf(",") + 1;
                strTemp = strTemp.substring(start);
                strTemp = strTemp.substring(0,strTemp.length() - 1);
                strTemp = strTemp.trim();
            }
        } catch (Exception e) {
            System.err.println("Error Parsing Mower Location: " + e.getMessage());
            System.exit(0);
        }
        intValue = Integer.parseInt(strTemp);
        return intValue;        
        
    }
    
    private static int mowerXY(String strValue, String strDim) {
        int intValue = 0;
        String strTemp = "";
        try {
            if (strDim.contains("Width")) {
                strTemp = strValue.trim();
                strTemp = strTemp.substring(1, strTemp.indexOf(","));
            } else if (strDim.contains("Height")) {
                strTemp = strValue.trim();
                int start = strTemp.indexOf(",") + 1;
                strTemp = strTemp.substring(start);
                strTemp = strTemp.substring(1, strTemp.indexOf(","));
            }
        } catch (Exception e) {
            System.err.println("Error Parsing Mower Location: " + e.getMessage());
            System.exit(0);
        }
        intValue = Integer.parseInt(strTemp);
        return intValue;
    }

    private static String mowerDirection(String strValue) {
        String strTemp = "";
        strTemp = strValue.trim();
        int start = strTemp.indexOf(",") + 1;
        strTemp = strTemp.substring(start);
        start = strTemp.indexOf(",") + 1;
        strTemp = strTemp.substring(start);
        return strTemp;
    }
    
    private static void scanSurroundings(int intMowerX, int intMowerY) {
        String strNeighbors = "";
        /*
            NORTH, NORTH-EAST, EAST, SOUTH-EAST, SOUTH, SOUTH-WEST, WEST, NORTH-WEST
        */
        
        // NORTH
        strNeighbors = "[N]: " + lawn.whatsOnTheLawn(intMowerX, intMowerY - 1);
        // NORTH-EAST
        strNeighbors = strNeighbors + " [NE]: " + lawn.whatsOnTheLawn(intMowerX + 1, intMowerY - 1);        
        // EAST
        strNeighbors = strNeighbors + " [E]: " + lawn.whatsOnTheLawn(intMowerX + 1, intMowerY);        
        // SOUTH-EAST
        strNeighbors = strNeighbors + " [SE]: " + lawn.whatsOnTheLawn(intMowerX + 1, intMowerY + 1);        
        // SOUTH
        strNeighbors = strNeighbors + " [S]: " + lawn.whatsOnTheLawn(intMowerX, intMowerY + 1);        
        // SOUTH-WEST
        strNeighbors = strNeighbors + " [SW]: " + lawn.whatsOnTheLawn(intMowerX - 1, intMowerY + 1);        
        // WEST
        strNeighbors = strNeighbors + " [W]: " + lawn.whatsOnTheLawn(intMowerX - 1, intMowerY);        
        // NORTH-WEST
        strNeighbors = strNeighbors + " [NW]: " + lawn.whatsOnTheLawn(intMowerX - 1, intMowerY - 1);    
        printActionNotification("Scan");
        System.out.println("Neighbors: " + strNeighbors);
    }
    
    private static void printActionNotification(String strAction) {
        System.out.println("");
        System.out.println("Action: " + strAction);
        System.out.println("------------");        
    } 
    
    private static void playGame() {
        intScanCount++;
        do {
            
        } while(mower.getStatus().contains("Active"));
        
        endGame();
    }

    @Override
    public void determineDirection() {
        
    }

    private static void endGame() {
        // Print output
        System.exit(0);
    }
    
}
