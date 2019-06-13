/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osmowsis;

/**
 *
 * @author Chakravarty
 */
public class Mower {
    
    private int intX;
    private int intY;
    private String status;
    private String strDirection;

    public Mower(int intX, int intY, String status, String strDirection) {
        this.intX = intX;
        this.intY = intY;
        this.status = "Active";
        this.strDirection = strDirection.substring(0,strDirection.length() - 1);
    }

    // Setters
    public void setIntX(int intX) {this.intX = intX;}
    public void setIntY(int intY) {this.intY = intY;}
    public void setStatus(String status) {this.status = status;}
    public void setStrDirection(String strDirection) {this.strDirection = strDirection;}
    
    // Getters
    public int getIntX() {return intX;}
    public int getIntY() {return intY;}
    public String getStatus() {return status;}
    public String getStrDirection() {return strDirection;}    
    
}