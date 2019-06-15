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
    private int intLawnWidth;
    private int intLawnHeight;
    private String strLawn[][];
    private int intOriginalGrass = 0;
    private int intGrassCut = 0;
    
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
                } else if (i == intLawnWidth - 1) {
                    addFence(i,j);
                } else if (j == intLawnHeight - 1) {
                    addFence(i,j);                    
                }
            }
        }
    }
    
    private void addFence(int intX, int intY) {strLawn[intX][intY] = "fence";}
    public void addCrater(int intX, int intY){strLawn[intX][intY] = "crater";}
    public void moveMower(int intX, int intY){strLawn[intX][intY] = "mower";}    
    public void cutGrass(int intX, int intY) {strLawn[intX][intY] = "empty";intGrassCut++;}
    
    public void addGrass() {
        for (int i = 1; i < intLawnWidth-1; i++) {
            for (int j = 1; j < intLawnHeight-1; j++) {
                try {
                    if (strLawn[i][j].isEmpty()) {
                        strLawn[i][j] = "grass";
                        intOriginalGrass++;
                    }
                } catch (Exception e) { // Null Pointer Exception
                    strLawn[i][j] = "grass";   
                    intOriginalGrass++;
                }
            }
        }
    }    

    public int getIntLawnWidth() {return intLawnWidth;}
    public int getIntLawnHeight() {return intLawnHeight;}
    public int getOriginalGrass() {return intOriginalGrass;}
    public int getCutGrass() {return intGrassCut;}
    
    public String whatsOnTheLawn(int intX, int intY) {
        if (strLawn[intX][intY].contains("fence")) {
            return "fence";
        } else if (strLawn[intX][intY].contains("crater")) {
            return "crater";
        } else if (strLawn[intX][intY].contains("mower")) {
            return "mower";
        } else if (strLawn[intX][intY].contains("grass")) {
            return "grass";
        }
        return "";
    }
}
