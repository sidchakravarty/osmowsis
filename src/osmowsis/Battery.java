/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osmowsis;

/**
 *
 * @author sid21
 */
public class Battery {
    public int intBatteryID;
    public int intBatteryLocation_X;
    public int intBatterLocation_Y;

    public Battery(int intBatteryID, int intBatteryLocation_X, int intBatterLocation_Y) {
        this.intBatteryID = intBatteryID;
        this.intBatteryLocation_X = intBatteryLocation_X;
        this.intBatterLocation_Y = intBatterLocation_Y;
    }
    
    public boolean isThereABatteryHere(){
        boolean blnIsThereBatteryHere = false;
        // TODO: Write code to detect if a battery exist at a certain location
        return blnIsThereBatteryHere;
    }    
}
