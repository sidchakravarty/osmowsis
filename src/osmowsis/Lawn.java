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
public class Lawn {
    int intLawnWidth;
    int intLawnHeight;
    String strLawn[][];

    /**
     * OBJECTIVE: Class Constructor
     *            Adds Fence to the farm
     * @param intLawnWidth
     * @param intLawnHeight 
     */
    public Lawn(int intLawnWidth, int intLawnHeight) {
        this.intLawnWidth = intLawnWidth;
        this.intLawnHeight = intLawnHeight;
        this.strLawn = new String [intLawnWidth][intLawnHeight];
        for (int i = 0; i < intLawnWidth; i++) {
            for (int j = 0; j < intLawnHeight; j++) {
                if (i == 0) {
                    addFence(i,j);
                } else if (j == 0) {
                    addFence(i,j);
                } else if (i == intLawnWidth) {
                    addFence(i,j);
                } else if (j == intLawnHeight) {
                    addFence(i,j);                    
                }
            }
        }
    }
    
    public void addCrater(int intX, int intY){
        strLawn[intX][intY] = "Crater";
    }
    
    private void addFence(int intX, int intY) {
        strLawn[intX][intY] = "Fence";
    }
    
}
