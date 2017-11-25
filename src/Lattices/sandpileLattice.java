/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lattices;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import sandpile.node;

/**
 *
 * @author Edward Newkirk
 */
public class sandpileLattice extends JPanel {
    protected int height, width, centerX, centerY;
    // in hex case, central row of hexagons should have (width) hexagons, and there will be (height) rows
    protected int i, j, k;// for use in for loops
    protected int squareSize; // sets the size with which the squares will be drawn
    protected int totalChips;
    protected int totalFirings; // tracks total number of firings
    protected boolean displayTimeMode; // if set to "true", we'll display a heat map of how recently nodes were fired
    protected node[][] nodeLattice;// will contain all the nodes except for the sink
    protected node sink; //the sink which catches all the chips falling off the edge.
    protected Color neg, zero, one, two, three, four, five, ready, center;
    protected Set<node> recheck, nextcheck;
    protected String displayType;
    
    public sandpileLattice(int setHeight, int setWidth, int startingDepth, int size){
        height = setHeight;
        width = setWidth;
        centerX = (width-1)/2;
        centerY = (height-1)/2;
        squareSize = size;
        // setting the various colors to black, they'll be reset later
        this.neg = Color.BLACK;
        this.zero = Color.BLACK;
        this.one = Color.BLACK;
        this.two = Color.BLACK;
        this.three = Color.BLACK;
        this.four = Color.BLACK;
        this.five = Color.BLACK;
        this.ready = Color.BLACK;
        this.center = Color.BLACK;
        totalFirings = 0;
        displayTimeMode = false;
    }
    
    public void displayTime(){
        displayType = "time";
    }
    
    public void displayChips(){
        displayType = "chips";
    }
    
    public void displayFirings(){
        displayType = "firings";
    }
    
    public String displayType(){
        return displayType;
    }
    
    public void addNumExits(){
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                nodeLattice[i][j].addChips(nodeLattice[i][j].numExits());
                totalChips += nodeLattice[i][j].numExits();
            }
        }
    }
    
    public void prepareForIdentity(){
        System.out.println("Started Preparing Identity");
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                try{
                    nodeLattice[i][j].addChips(2*nodeLattice[i][j].numExits()-2);
                    totalChips += 2*nodeLattice[i][j].numExits()-2;
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Boundary error. i: " + i + ", j: " + j + ", height: " + nodeLattice[i].length + ", width: " + nodeLattice.length);
                }
            }
        }
        System.out.println("Finished Preparing Identity");
    }
    
    public void prepareForIdentitySimple(){
        System.out.println("Started Preparing Identity");
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                try{
                    nodeLattice[i][j].addChips(nodeLattice[i][j].numExits());
                    totalChips += nodeLattice[i][j].numExits();
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Boundary error. i: " + i + ", j: " + j + ", height: " + nodeLattice[i].length + ", width: " + nodeLattice.length);
                }
            }
        }
        System.out.println("Finished Preparing Identity");
    }
    
    public void zeroSink(){
        totalChips -= sink.numChips();
        sink.addChips(-1*sink.numChips());
    }
    
    public void addChipsAtCenter(int chips){
        for(i=0; i<chips; i++){
            nodeLattice[centerX][centerY].addChip();
        }
        totalChips += chips;
        repaint();
    }
    
    public Set fireAll(){
        recheck = new HashSet();
        return recheck;
    }
    
    public Set fireFromSet(Set<node> nodesToCheck){
        recheck = new HashSet();
        totalFirings ++;
        for(node n:nodesToCheck){
            n.fire(recheck, totalFirings);
        }
        if(recheck.contains(sink)){
            recheck.remove(sink);
        }
        return recheck;
    }
    
    public int totalChips(){
        return totalChips;
    }
    
    public int totalChipsMinusSink(){
        return totalChips - sink.numChips();
    }
    
    public void setSquareSize(int newSize){
        squareSize = newSize;
    }
    
    public int getSquareSize(){
        return squareSize;
    }
    
    public int getGridWidth(){
        return width;
    }
    
    public int getGridHeight(){
        return height;
    }
    
    public node[][] getArray(){
        return nodeLattice;
    }
    
    public int getTotalFirings(){
        return totalFirings;
    }
    
    public void setDisplayMode(boolean x){
        displayTimeMode = x;
    }
    
    public void addArray(sandpileLattice otherPile){
        if(height != otherPile.getGridHeight() || width != otherPile.getGridWidth()){
            System.out.println("Dimensions mismatch, piles not added.");
            System.out.println("Height: " + height + " vs. " + otherPile.getGridHeight());
            System.out.println("Width: " + width + " vs. " + otherPile.getGridWidth());
        }
        else{
            otherPile.zeroSink();
            node[][] otherLattice = otherPile.getArray();
            for (i = 0; i < width; i++){
                for(j = 0; j < height; j++){
                    nodeLattice[i][j].addChips(otherLattice[i][j].numChips());
                }
            }
            totalChips += otherPile.totalChips();
        }
    }
    
    public void subtractArray(sandpileLattice otherPile){
        if(height != otherPile.getGridHeight() || width != otherPile.getGridWidth()){
            System.out.println("Dimensions mismatch, piles not added.");
            System.out.println("Height: " + height + " vs. " + otherPile.getGridHeight());
            System.out.println("Width: " + width + " vs. " + otherPile.getGridWidth());
        }
        else{
            System.out.println("Started Subtracting");
            otherPile.zeroSink();
            node[][] otherLattice = otherPile.getArray();
            for (i = 0; i < width; i++){
                for(j = 0; j < height; j++){
                    nodeLattice[i][j].addChips(-1*otherLattice[i][j].numChips());
                }
            }
            totalChips -= otherPile.totalChips();
            System.out.println("Finished Subtracting");
        }
    }
    
    public void setColors (Color neg, Color zero, Color one, Color two, Color three, Color four, Color five, Color ready, Color center){
        this.neg = neg;
        this.zero = zero;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.ready = ready;
        this.center = center;
    }
}
