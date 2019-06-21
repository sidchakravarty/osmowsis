/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osmowsis;

import java.util.HashMap;

/**
 *
 * @author Chakravarty
 */
public class Mower {
    private static int intTotalMowers;
    private int intX;
    private int intY;
    private String status;
    private String strDirection;
    private String currentAction;
    private String nextAction;
    private String nextDirection;
    private int noOfTurns;
    private int intMowerID;
    private int intRemainingBatteryCharge;
    private HashMap<String, String> myNeighbors = new HashMap<String, String>();

    public Mower(int intX, int intY, String status, String strDirection, int strMowerID, int intRemainingBatteryCharge) {
        this.intX = intX;
        this.intY = intY;
        this.status = "Active";
        this.currentAction = "Initialized";
        this.nextAction = "Scan";
        this.nextDirection = "";
        this.noOfTurns = 0;
        this.strDirection = strDirection.substring(0,strDirection.length() - 1);
        this.intMowerID = intMowerID;
        this.intRemainingBatteryCharge = intRemainingBatteryCharge;
    }

    // Setters
    public void setIntX(int intX) {this.intX = intX;}
    public void setIntY(int intY) {this.intY = intY;}
    public void setStatus(String status) {this.status = status;}
    public void setStrDirection(String strDirection) {this.strDirection = strDirection;}
    public void setTurns(int intTurns) {this.noOfTurns = intTurns;}
    public void setRemainingCharge (int intRemainingBatteryCharge) {this.intRemainingBatteryCharge = intRemainingBatteryCharge;}
    public static void setTotalMowers(int intTotalMowers) {Mower.intTotalMowers = intTotalMowers;}
    public void setNeighbors(HashMap<String, String> localNeighbor) {
        this.myNeighbors.put("N", localNeighbor.get("N"));
        this.myNeighbors.put("NE", localNeighbor.get("NE"));
        this.myNeighbors.put("E", localNeighbor.get("E"));
        this.myNeighbors.put("SE", localNeighbor.get("SE"));
        this.myNeighbors.put("S", localNeighbor.get("S"));
        this.myNeighbors.put("SW", localNeighbor.get("SW"));
        this.myNeighbors.put("W", localNeighbor.get("W"));
        this.myNeighbors.put("NW", localNeighbor.get("NW"));                
    }
        
    // Getters
    public int getIntX() {return this.intX;}
    public int getIntY() {return this.intY;}
    public String getStatus() {return this.status;}
    public String getStrDirection() {return this.strDirection;}    
    public int getnoOfTurns() {return this.noOfTurns;}
    public int getID(){return this.intMowerID;}
    public int getRemainingBatteryCharge(){return this.intRemainingBatteryCharge;}
    public static int getMowerCount(){return intTotalMowers;}
    
    public String moveMower(String strDirection, int noOfPlaces) {
        String strActionStatus = "";
        noOfTurns  += noOfPlaces;
        return strActionStatus;
    }
    
    public HashMap<String, String> getNeighbors(){return myNeighbors;}
}
