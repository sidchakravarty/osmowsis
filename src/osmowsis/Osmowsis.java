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
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Osmowsis {
    // VARIABLES //
    private static Map<String, String> mower_surroundings = new HashMap<String, String>();
    private static int intMovesCount;
    private static int intScanCount;
    private static ArrayList<String> strInstructions = new ArrayList();
    private static Lawn lawn;
    private static Mower mower;
    private static int intTurns = 0;
    private static int intMaxTurns = 0;
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
            intMaxTurns = Integer.parseInt(retrieveTurns(strInstructions.get(strInstructions.size()-1)));
            
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
    
    private static String retrieveTurns(String strValue) {
        String strTemp;
            strTemp = strValue.trim();
            strTemp = strTemp.substring(1);
            strTemp = strTemp.substring(0, strTemp.length() - 1);
            strTemp = String.valueOf(strTemp);
            System.out.println(strTemp);
        return strTemp;
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
                mower = new Mower(intMowerX + 1, intMowerY + 1, "active", strMowerDirection);
                lawn.moveMower(intMowerX + 1, intMowerY + 1);
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
        String strDecoration = "";
        /*
            NORTH, NORTH-EAST, EAST, SOUTH-EAST, SOUTH, SOUTH-WEST, WEST, NORTH-WEST
        */
        
        // NORTH
        strDecoration = lawn.whatsOnTheLawn(intMowerX, intMowerY - 1);
        strNeighbors = "[N]: " + strDecoration;
        mower_surroundings.put("N", strDecoration);
        // NORTH-EAST
        strDecoration = lawn.whatsOnTheLawn(intMowerX + 1, intMowerY - 1);
        strNeighbors = strNeighbors + " [NE]: " + strDecoration;   
        mower_surroundings.put("NE", strDecoration);
        // EAST
        strDecoration = lawn.whatsOnTheLawn(intMowerX + 1, intMowerY);
        strNeighbors = strNeighbors + " [E]: " + strDecoration;   
        mower_surroundings.put("E", strDecoration);
        // SOUTH-EAST
        strDecoration = lawn.whatsOnTheLawn(intMowerX + 1, intMowerY + 1);
        strNeighbors = strNeighbors + " [SE]: " + strDecoration;        
        mower_surroundings.put("SE", strDecoration);
        // SOUTH
        strDecoration = lawn.whatsOnTheLawn(intMowerX, intMowerY + 1);
        strNeighbors = strNeighbors + " [S]: " + strDecoration;   
        mower_surroundings.put("S", strDecoration);
        // SOUTH-WEST
        strDecoration = lawn.whatsOnTheLawn(intMowerX - 1, intMowerY + 1);
        strNeighbors = strNeighbors + " [SW]: " + strDecoration;      
        mower_surroundings.put("SW", strDecoration);
        // WEST
        strDecoration = lawn.whatsOnTheLawn(intMowerX - 1, intMowerY);
        strNeighbors = strNeighbors + " [W]: " + strDecoration;   
        mower_surroundings.put("W", strDecoration);
        // NORTH-WEST
        strDecoration = lawn.whatsOnTheLawn(intMowerX - 1, intMowerY - 1);
        strNeighbors = strNeighbors + " [NW]: " + strDecoration;   
        mower_surroundings.put("NW", strDecoration);
        printActionNotification("Scan");
        System.out.println("Neighbors: " + strNeighbors);
    }
    
    private static void printActionNotification(String strAction) {
        System.out.println("");
        System.out.println("Action: " + strAction);
        System.out.println("------------");        
    } 
    
    /**
     * GamePlay:
     * 1. While mover status = "Active"
     * 2. For current position of the mower,
     *    determine the viable directions in which the mower can travel
     * 4. For those directions, determine the maximum distance the mower
     *    can travel
     * 5. Choose the direction that maximizes the distance
     * 6. Travel on the intended path for the intended direction
     * 7. While traveling, determine if you encountered a crater <CRASH>
     * 8. If mower has reached a boundary, scan again.
     * 9. Game continues till the mower has crashed or all grass has been cut
     */
    private static void playGame() {
        intScanCount++;
        do {
            intTurns++;
            determineAvailableDirection(mower_surroundings.get("N"),
            mower_surroundings.get("NE"),mower_surroundings.get("E"),
            mower_surroundings.get("SE"),mower_surroundings.get("S"),
            mower_surroundings.get("SW"),mower_surroundings.get("W"),
            mower_surroundings.get("NW"));
        } while(mower.getStatus().contains("active") || intTurns < intMaxTurns);
    }

    private static void determineAvailableDirection(String strN, String strNE, String strE, 
    String strSE, String strS, String strSW, String strW, String strNW) {
        boolean blnN    = false;
        boolean blnNE   = false;
        boolean blnE    = false;
        boolean blnSE   = false;
        boolean blnS    = false;
        boolean blnSW   = false;
        boolean blnW    = false;
        boolean blnNW   = false;
        
        if (!strN.contains("crater") && !strN.contains("fence") && !strN.contains("empty")) {
            blnN = true;
        }
        
        if (!strNE.contains("crater") && !strNE.contains("fence") && !strNE.contains("empty")) {
            blnNE = true;
        }        

        if (!strE.contains("crater") && !strE.contains("fence") && !strE.contains("empty")) {
            blnE = true;
        }

        if (!strSE.contains("crater") && !strSE.contains("fence") && !strSE.contains("empty")) {
            blnSE = true;
        }        

        if (!strS.contains("crater") && !strS.contains("fence") && !strS.contains("empty")) {
            blnS = true;
        }        

        if (!strSW.contains("crater") && !strSW.contains("fence") && !strSW.contains("empty")) {
            blnSW = true;
        }        

        if (!strW.contains("crater") && !strW.contains("fence") && !strW.contains("empty")) {
            blnW = true;
        }        

        if (!strNW.contains("crater") && !strNW.contains("fence") && !strNW.contains("empty")) {
            blnNW = true;
        }        
        
        if(!blnN && !blnNE && !blnE && !blnSE && !blnS && !blnSW && !blnW && !blnNW) {
            printActionNotification("Game End - No. of Turns");
            int intLawnSquares = (lawn.getIntLawnHeight() - 2)* (lawn.getIntLawnWidth() - 2);
            endGame(intLawnSquares,mower.getnoOfTurns(), lawn.getOriginalGrass(), lawn.getCutGrass());
        }
        determineOptimalDistance(blnN,blnNE,blnE,blnSE,blnS,blnSW,blnW,blnNW); 
        
    }

    private static void determineOptimalDistance(boolean blnN, boolean blnNE, 
    boolean blnE, boolean blnSE, boolean blnS, boolean blnSW, boolean blnW, 
    boolean blnNW) {
        int intCurrentDistance = 0;
        int intMaxDistance = 0;
        String strChosenDirection = "";
        
        if (blnN) {
            intCurrentDistance = calculateMaxDistance("N");
            intMaxDistance = intCurrentDistance;
            strChosenDirection = "N";
        }
        if (blnNE) {
            intCurrentDistance = calculateMaxDistance("NE");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "NE";
            }
        }
        if (blnE) {
            intCurrentDistance = calculateMaxDistance("E");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "E";
            }            
        }
        if (blnSE) {
            intCurrentDistance = calculateMaxDistance("SE");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "SE";
            }            
        }
        if (blnS) {
            intCurrentDistance = calculateMaxDistance("S");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "S";
            }            
        }
        if (blnSW) {
            intCurrentDistance = calculateMaxDistance("SW");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "SW";
            }            
        }
        if (blnW) {
            intCurrentDistance = calculateMaxDistance("W");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "W";
            }            
        }
        if (blnNW) {
            intCurrentDistance = calculateMaxDistance("NW");
            if(intCurrentDistance > intMaxDistance) {
                intMaxDistance = intCurrentDistance;
                strChosenDirection = "NW";
            }            
        }
        moveMe(strChosenDirection,intMaxDistance);
    }
    
    private static int calculateMaxDistance(String strDirection) {
        int intDistance = 0;
        int intMX = 0;  // Current Mower X location
        int intMY = 0;  // Current Mower Y location
        int intStepX = 0;
        int intStepY = 0;

        intMX = mower.getIntX(); 
        intMY = mower.getIntY();
        intStepX = intMX;
        intStepY = intMY;
            
        switch(strDirection) {

            case "N":
                // X = 0; Y = -1
                do{
                    intStepY --;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;

            case "NE":
                // X = +1; Y = -1
                do{
                    intStepY --;
                    intStepX ++;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;

            case "E":
                // X = +1; Y = 0
                do{
                    intStepX ++;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;                

            case "SE":
                // X = +1; Y = +1
                do{
                    intStepY ++;
                    intStepX ++;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;       

            case "S":
                // X = 0; Y = +1
                do{
                    intStepY ++;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;                

            case "SW":
                // X = -1; Y = +1
                do{
                    intStepY ++;
                    intStepX --;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;          

            case "W":
                // X = -1; Y = 0
                do{
                    intStepX --;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;         

            case "NW":
                // X = -1; Y = -1
                do{
                    intStepY --;
                    intStepX --;
                    intDistance++;
                }while(!lawn.whatsOnTheLawn(intStepX, intStepY).contains("fence"));
                intDistance--;
                break;                
        }
        
        return intDistance;
    }

    // After determining the direction and the no. of steps
    // This one will move the mower and determine if it crashed or not
    // Change status of the lawn to empty
    private static String moveMe(String strFinalDestination, int intSteps) {
        int intMX = 0;  // Current Mower X location
        int intMY = 0;  // Current Mower Y location
        int intStepX = 0;
        int intStepY = 0;
            
            String strMowerStatus = "";
            intMX = mower.getIntX(); 
            intMY = mower.getIntY();
            intStepX = intMX;
            intStepY = intMY;
            mower.setStrDirection(strFinalDestination);

            for(int i = 0; i < intSteps; i++) {
                switch(strFinalDestination) {
                    case "N":
                            intStepY--;
                        break;
                    case "NE":
                            intStepX++;
                            intStepY--;
                        break;
                    case "E":
                            intStepX++;
                        break;
                    case "SE":
                            intStepX++;
                            intStepY++;
                        break;
                    case "S":
                            intStepY++;
                        break;
                    case "SW":
                            intStepX--;
                            intStepY++;
                        break;
                    case "W":
                            intStepX--;
                        break;
                    case "NW":
                            intStepX--;
                            intStepY--;
                        break;
                }
                intMovesCount++;
                if(lawn.whatsOnTheLawn(intStepX, intStepY).contains("crater")) {
                    strMowerStatus = "crash";
                    
                    printActionNotification("Crash");
                    System.out.println("[Turns]: " + intMovesCount + " [Direction]: " + strFinalDestination);
                    System.out.println("Crash");   
                    mower.setTurns(intMovesCount);
                    int intLawnSquares = (lawn.getIntLawnHeight() - 2)* (lawn.getIntLawnWidth() - 2);
                    endGame(intLawnSquares,mower.getnoOfTurns(), lawn.getOriginalGrass(), lawn.getCutGrass());
                } else {
                    strMowerStatus = "active";
                    mower.setIntX(intStepX);
                    mower.setIntY(intStepY);
                    lawn.moveMower(intStepX, intStepY);
                    lawn.cutGrass(intMX, intMY);
                    intMX = intStepX;
                    intMY = intStepY;            
                    printActionNotification("Move");
                    System.out.println("[Turns]: " + intMovesCount + " [Direction]: " + strFinalDestination);
                    System.out.println("OK");
                }
                mower.setStatus(strMowerStatus);
            }
            
        printLawn(lawn.getIntLawnWidth(), lawn.getIntLawnHeight());
        scanSurroundings(intMX,intMY); // Updates Mower Surroundings
        return strMowerStatus;
    }
    
    private static void endGame(int intLawnSquares, int intMoves, int intOriginalGrass, int intCutGrass) {
        // Print output
        printActionNotification("End Game");
        System.out.println("Final Lawn Output");
        System.out.println("-----------------");
        printLawn(lawn.getIntLawnWidth(), lawn.getIntLawnHeight());
        System.out.println("-----------------");
        System.out.println("");
        System.out.println("");
        System.out.println("Lawn Squares    : " + intLawnSquares);
        System.out.println("Initial Grass   : " + intOriginalGrass);
        System.out.println("Cut Grass       : " + intCutGrass);
        System.out.println("No. of moves    : " + intMoves);
        System.exit(0);
    }
    
}
