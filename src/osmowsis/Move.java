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
public abstract class Move {
    public abstract void determineAvailableDirection(String strN, String strNE, 
    String strE, String strSE, String strS, String strSW, String strW, 
    String strNW);
    
    public abstract void determineOptimalDistance(boolean blnN,boolean blnNE,
    boolean blnE,boolean blnSE,boolean blnS,boolean blnSW,boolean blnW,
    boolean blnNW);
    
    public abstract int calculateMaxDistance(String strDirection);
    
    public abstract String moveMe(String strFinalDestination, int intSteps);
    
}
