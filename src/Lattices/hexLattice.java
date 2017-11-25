/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lattices;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import sandpile.node;

/**
 *
 * @author Edward Newkirk
 */
public class hexLattice extends sandpileLattice{

    
    public hexLattice(int setHeight, int setWidth, int startingDepth, int size){
        super(setHeight, setWidth, startingDepth, size);
        // and then we create the grid:
        nodeLattice = new node[width][height];
        sink = new node(0);
        // first, create all the nodes with the appropriate # of chips; not all of these will appear on the graph
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                nodeLattice[i][j] = new node(startingDepth);
            }
        }
        //then get neighbors set up, ONLY FOR NODES ON THE ACTUAL HEXAGONAL GRAPH
        //start with the four outer corners:
        nodeLattice[0][0].addNeighbor(nodeLattice[0][1]);
        nodeLattice[0][0].addNeighbor(nodeLattice[1][0]);
        nodeLattice[0][0].addNeighbor(nodeLattice[1][1]);
        nodeLattice[0][0].addNeighbor(sink);
        nodeLattice[0][0].addNeighbor(sink);
        nodeLattice[0][0].addNeighbor(sink);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[0][height-2]);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[1][height-1]);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[1][height-2]);
        nodeLattice[0][height-1].addNeighbor(sink);
        nodeLattice[0][height-1].addNeighbor(sink);
        nodeLattice[0][height-1].addNeighbor(sink);
        nodeLattice[width-centerY-1][0].addNeighbor(nodeLattice[width-centerY-1][1]);
        nodeLattice[width-centerY-1][0].addNeighbor(nodeLattice[width-centerY-2][0]);
        nodeLattice[width-centerY-1][0].addNeighbor(nodeLattice[width-centerY][1]);
        nodeLattice[width-centerY-1][0].addNeighbor(sink);
        nodeLattice[width-centerY-1][0].addNeighbor(sink);
        nodeLattice[width-centerY-1][0].addNeighbor(sink);
        nodeLattice[width-centerY-1][height-1].addNeighbor(nodeLattice[width-centerY-1][height-2]);
        nodeLattice[width-centerY-1][height-1].addNeighbor(nodeLattice[width-centerY-2][height-1]);
        nodeLattice[width-centerY-1][height-1].addNeighbor(nodeLattice[width-centerY][height-2]);
        nodeLattice[width-centerY-1][height-1].addNeighbor(sink);
        nodeLattice[width-centerY-1][height-1].addNeighbor(sink);
        nodeLattice[width-centerY-1][height-1].addNeighbor(sink);
        totalChips += 4*startingDepth;
        //then the top and bottom rows:
        for(i=1;i<width-centerY-1;i++){
            nodeLattice[i][0].addNeighbor(nodeLattice[i][1]);
            nodeLattice[i][0].addNeighbor(nodeLattice[i+1][0]);
            nodeLattice[i][0].addNeighbor(nodeLattice[i-1][0]);
            nodeLattice[i][0].addNeighbor(nodeLattice[i+1][1]);
            nodeLattice[i][0].addNeighbor(sink);
            nodeLattice[i][0].addNeighbor(sink);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i][height-2]);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i+1][height-1]);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i+1][height-2]);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i-1][height-1]);
            nodeLattice[i][height-1].addNeighbor(sink);
            nodeLattice[i][height-1].addNeighbor(sink);
            totalChips += 2*startingDepth;
        }
        //then the middle rows, adding from the top and bottom simultaneously
        for(k=1; k<centerY; k++){
            //endpoints of the row in the upper half:
            nodeLattice[0][centerY-k].addNeighbor(nodeLattice[0][centerY-k+1]);
            nodeLattice[0][centerY-k].addNeighbor(nodeLattice[0][centerY-k-1]);
            nodeLattice[0][centerY-k].addNeighbor(nodeLattice[1][centerY-k]);
            nodeLattice[0][centerY-k].addNeighbor(nodeLattice[1][centerY-k+1]);
            nodeLattice[0][centerY-k].addNeighbor(sink);
            nodeLattice[0][centerY-k].addNeighbor(sink);
            nodeLattice[width-1-k][centerY-k].addNeighbor(nodeLattice[width-k][centerY-k+1]);
            nodeLattice[width-1-k][centerY-k].addNeighbor(nodeLattice[width-1-k][centerY-k+1]);
            nodeLattice[width-1-k][centerY-k].addNeighbor(nodeLattice[width-2-k][centerY-k]);
            nodeLattice[width-1-k][centerY-k].addNeighbor(nodeLattice[width-2-k][centerY-k-1]);
            nodeLattice[width-1-k][centerY-k].addNeighbor(sink);
            nodeLattice[width-1-k][centerY-k].addNeighbor(sink);
            //and of the row in the lower half:
            nodeLattice[0][centerY+k].addNeighbor(nodeLattice[0][centerY+k+1]);
            nodeLattice[0][centerY+k].addNeighbor(nodeLattice[0][centerY+k-1]);
            nodeLattice[0][centerY+k].addNeighbor(nodeLattice[1][centerY+k-1]);
            nodeLattice[0][centerY+k].addNeighbor(nodeLattice[1][centerY+k]);
            nodeLattice[0][centerY+k].addNeighbor(sink);
            nodeLattice[0][centerY+k].addNeighbor(sink);
            nodeLattice[width-1-k][centerY+k].addNeighbor(nodeLattice[width-k][centerY+k-1]);
            nodeLattice[width-1-k][centerY+k].addNeighbor(nodeLattice[width-1-k][centerY+k-1]);
            nodeLattice[width-1-k][centerY+k].addNeighbor(nodeLattice[width-2-k][centerY+k]);
            nodeLattice[width-1-k][centerY+k].addNeighbor(nodeLattice[width-2-k][centerY+k+1]);
            nodeLattice[width-1-k][centerY+k].addNeighbor(sink);
            nodeLattice[width-1-k][centerY+k].addNeighbor(sink);
            totalChips += 4*startingDepth;
            for(i=1;i<width-k-1;i++){
                nodeLattice[i][centerY-k].addNeighbor(nodeLattice[i][centerY-k+1]);
                nodeLattice[i][centerY-k].addNeighbor(nodeLattice[i][centerY-k-1]);
                nodeLattice[i][centerY-k].addNeighbor(nodeLattice[i+1][centerY-k]);
                nodeLattice[i][centerY-k].addNeighbor(nodeLattice[i-1][centerY-k]);
                nodeLattice[i][centerY-k].addNeighbor(nodeLattice[i+1][centerY-k+1]);
                nodeLattice[i][centerY-k].addNeighbor(nodeLattice[i-1][centerY-k-1]);
                nodeLattice[i][centerY+k].addNeighbor(nodeLattice[i][centerY+k+1]);
                nodeLattice[i][centerY+k].addNeighbor(nodeLattice[i][centerY+k-1]);
                nodeLattice[i][centerY+k].addNeighbor(nodeLattice[i+1][centerY+k]);
                nodeLattice[i][centerY+k].addNeighbor(nodeLattice[i-1][centerY+k]);
                nodeLattice[i][centerY+k].addNeighbor(nodeLattice[i+1][centerY+k-1]);
                nodeLattice[i][centerY+k].addNeighbor(nodeLattice[i-1][centerY+k+1]);
                totalChips += 2*startingDepth;
            }
        }
        //and the very center row:
        j=centerY;
        nodeLattice[0][j].addNeighbor(nodeLattice[0][j+1]);
        nodeLattice[0][j].addNeighbor(nodeLattice[0][j-1]);
        nodeLattice[0][j].addNeighbor(nodeLattice[1][j]);
        nodeLattice[0][j].addNeighbor(sink);
        nodeLattice[0][j].addNeighbor(sink);
        nodeLattice[0][j].addNeighbor(sink);
        nodeLattice[width-1][j].addNeighbor(nodeLattice[width-2][j-1]);
        nodeLattice[width-1][j].addNeighbor(nodeLattice[width-2][j]);
        nodeLattice[width-1][j].addNeighbor(nodeLattice[width-2][j+1]);
        nodeLattice[width-1][j].addNeighbor(sink);
        nodeLattice[width-1][j].addNeighbor(sink);
        nodeLattice[width-1][j].addNeighbor(sink);
        totalChips += 2*startingDepth;
        for(i=1;i<width-1;i++){
            nodeLattice[i][j].addNeighbor(nodeLattice[i][j+1]);
            nodeLattice[i][j].addNeighbor(nodeLattice[i][j-1]);
            nodeLattice[i][j].addNeighbor(nodeLattice[i+1][j]);
            nodeLattice[i][j].addNeighbor(nodeLattice[i-1][j]);
            nodeLattice[i][j].addNeighbor(nodeLattice[i-1][j+1]);
            nodeLattice[i][j].addNeighbor(nodeLattice[i-1][j-1]);
            totalChips += startingDepth;
        }
    }
    

    
    @Override
    public Set fireAll(){
        recheck = new HashSet();
        totalFirings ++;
        k=1;
        for(k=1; k<centerY+1;k++){
            for(i=0;i<width-k;i++){
                try{
                    nodeLattice[i][centerY+k].fire(recheck, totalFirings);
                    nodeLattice[i][centerY-k].fire(recheck, totalFirings);
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Hexagonal Array Mismatch:");
                    System.out.println("i: " + i + ", centerY: "+ centerY + ", k: " + k + ", array length: " + nodeLattice[i].length);
                }
            }
        }
        for(i=0;i<width;i++){
            nodeLattice[i][centerY].fire(recheck, totalFirings);
        }
        if(recheck.contains(sink)){
            recheck.remove(sink);
        }
        return recheck;
    }
    
    public void setIdentity(){
        for(k=1; k<centerY+1;k++){
            for(i=0;i<width-k;i++){
                nodeLattice[i][centerY+k].addChips(nodeLattice[i][centerY+k].numEntrances());
                nodeLattice[i][centerY-k].addChips(nodeLattice[i][centerY-k].numEntrances());
            }
        }
        for(i=0;i<width;i++){
            nodeLattice[i][centerY].addChips(nodeLattice[i][centerY].numEntrances());
        }
    }
    
    public boolean nodesReady(){
         for(k=1;k<centerY+1;k++){
            for(i=0;i<width-k;i++){
                if(nodeLattice[i][centerY+k].numChips() >= nodeLattice[i][centerY+k].numExits()){
                    
                    return true;  
                }
                if(nodeLattice[i][centerY-k].numChips() >= nodeLattice[i][centerY-k].numExits()){

                    return true;
                }
            }
        }
         for(i=0;i<width;i++){
             if(nodeLattice[i][centerY].numChips() >= nodeLattice[i][centerY].numExits()){
                    return true;
                }
         }
        return false;
    }
    
    public void stabilize(){
        recheck = new HashSet();
        for(k=1; k<centerY+1;k++){
            for(i=0;i<width-k;i++){
                nodeLattice[i][centerY+k].fire(recheck, totalFirings);
                nodeLattice[i][centerY-k].fire(recheck, totalFirings);
            }
        }
        for(i=0;i<width;i++){
            nodeLattice[i][centerY].fire(recheck, totalFirings);
        }
        k=1;
        while(recheck.size()>0){
            nextcheck = new HashSet();
            for(node n: recheck){
                if(!n.equals(sink)){
                    n.fire(nextcheck, totalFirings);
                }
            }
            recheck = nextcheck;
            k++;
        }
    }

    
    @Override
    public void paintComponent (Graphics g){
        //first, clear the image:
        super.paintComponent(g);
        switch (displayType) {
            case "chips":
                //then mark any nodes which are ready to fire as ready to fire, then do the others
                for(j=0;j<centerY;j++){
                    k = height-j-1; // the index for the other row of equal width
                    for(i=0;i<width-centerY+j;i++){
                        // color the (i,j)th node, with horizontal displacement depending on distance from the mid-row
                        if(nodeLattice[i][j].numChips() >= nodeLattice[i][j].numExits()){
                            g.setColor(ready);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() < 0){
                            g.setColor(neg);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() == 0){
                            g.setColor(zero);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() == 1){
                            g.setColor(one);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() == 2){
                            g.setColor(two);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() == 3){
                            g.setColor(three);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() == 4){
                            g.setColor(four);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][j].numChips() == 5){
                            g.setColor(five);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        }
                        // and then the (i, k)th node
                        if(nodeLattice[i][k].numChips() >= nodeLattice[i][k].numExits()){
                            g.setColor(ready);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() < 0){
                            g.setColor(neg);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() == 0){
                            g.setColor(zero);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() == 1){
                            g.setColor(one);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() == 2){
                            g.setColor(two);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() == 3){
                            g.setColor(three);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() == 4){
                            g.setColor(four);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[i][k].numChips() == 5){
                            g.setColor(five);
                            g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                        }
                    }
                }
                j = centerY;
                for(i=0;i<width;i++) {
                    if(nodeLattice[i][j].numChips() >= nodeLattice[i][j].numExits()){
                           g.setColor(ready);
                           g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                       }
                       else if(nodeLattice[i][j].numChips() < 0){
                           g.setColor(neg);
                           g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                       }
                       else if(nodeLattice[i][j].numChips() == 0){
                           g.setColor(zero);
                           g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                       }
                       else if(nodeLattice[i][j].numChips() == 1){
                           g.setColor(one);
                           g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                       }
                       else if(nodeLattice[i][j].numChips() == 2){
                           g.setColor(two);
                           g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                       }
                       else if(nodeLattice[i][j].numChips() == 3){
                           g.setColor(three);
                           g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                       }
                        else if(nodeLattice[i][j].numChips() == 4){
                               g.setColor(four);
                               g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                           }
                        else if(nodeLattice[i][j].numChips() == 5){
                               g.setColor(five);
                               g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                           }
                }
                break;
            case "time":
                Color recentShade;
                int min = totalFirings-1;
                // get the actual min
                for(j=0;j<centerY;j++){
                    k = height-j-1; // the index for the other row of equal width
                    for(i=0;i<width-centerY+j;i++){
                        if(nodeLattice[i][j].lastFired() < min){
                            min = nodeLattice[i][j].lastFired();
                        }
                        if(nodeLattice[i][k].lastFired() < min){
                            min = nodeLattice[i][k].lastFired();
                        }
                    }
                }
                j = centerY;
                for(i=0;i<width;i++) {
                    if(nodeLattice[i][j].lastFired() < min){
                        min = nodeLattice[i][j].lastFired();
                    }
                }
                // and draw the pictures
                int p;
                for(j=0;j<centerY;j++){
                    k = height-j-1; // the index for the other row of equal width
                    for(i=0;i<width-centerY+j;i++){
                        p = 255*(nodeLattice[i][j].lastFired()-min)/(totalFirings-min);
                        recentShade = new Color(p,0,255-p);
                        g.setColor(recentShade);
                        g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        p = 255*(nodeLattice[i][k].lastFired()-min)/(totalFirings-min);
                        recentShade = new Color(p,0,255-p);
                        g.setColor(recentShade);
                        g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                    }
                }
                j = centerY;
                for(i=0;i<width;i++) {
                    p = 255*(nodeLattice[i][j].lastFired()-min)/(totalFirings-min);
                    recentShade = new Color(p,0,255-p);
                    g.setColor(recentShade);
                    g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                }
                System.out.println("Min: " + min + ", Total Firings: " + totalFirings);
                break;
            case "firings":
                Color firingShade;
                int max = 1;
                for(j=0;j<centerY;j++){
                    k = height-j-1; // the index for the other row of equal width
                    for(i=0;i<width-centerY+j;i++){
                        if(nodeLattice[i][j].numFirings() > max){
                            max = nodeLattice[i][j].numFirings();
                        }
                        if(nodeLattice[i][k].numFirings() > max){
                            max = nodeLattice[i][k].numFirings();
                        }
                    }
                }
                j = centerY;
                for(i=0;i<width;i++) {
                    if(nodeLattice[i][j].numFirings() > max){
                        max = nodeLattice[i][j].numFirings();
                    }
                }
                // and draw the pictures
                for(j=0;j<centerY;j++){
                    k = height-j-1; // the index for the other row of equal width
                    for(i=0;i<width-centerY+j;i++){
                        p = 255*(nodeLattice[i][j].numFirings())/(max);
                        recentShade = new Color(p,0,255-p);
                        g.setColor(recentShade);
                        g.fillRect(i*squareSize+(centerY-j)*squareSize/2, j*squareSize, squareSize, squareSize);
                        p = 255*(nodeLattice[i][k].numFirings())/(max);
                        recentShade = new Color(p,0,255-p);
                        g.setColor(recentShade);
                        g.fillRect(i*squareSize+(centerY-j)*squareSize/2, k*squareSize, squareSize, squareSize);
                    }
                }
                j = centerY;
                for(i=0;i<width;i++) {
                    p = 255*(nodeLattice[i][j].numFirings())/(max);
                    recentShade = new Color(p,0,255-p);
                    g.setColor(recentShade);
                    g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                }
                break;
        }
        // and mark the center
        g.setColor(center);
        g.fillOval(centerX*squareSize+1, centerY*squareSize+1, squareSize-2, squareSize-2);
    }
    
    public void setToIdentity(){
        /* per Propp et al, if d = layout with each node exactly ready to fire
         * and s = (2d-2), stab((s-stab(s)))=identity of sandpile group
         */
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                nodeLattice[i][j].addChips(2*nodeLattice[i][j].numExits()-2);
            }
        }
        stabilize(); // we are now at stab(s)
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                k = nodeLattice[i][j].numChips();
                nodeLattice[i][j].addChips(2*nodeLattice[i][j].numExits()-2*k-2);
                // each node now has s - 2 stab s + stab s chips
            }
        }
        stabilize();
        totalChips = -1;
        sink.addChips(-1*sink.numChips());
    }
}
