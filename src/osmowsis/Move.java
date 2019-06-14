/*
 * CLASS NOT USED
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
