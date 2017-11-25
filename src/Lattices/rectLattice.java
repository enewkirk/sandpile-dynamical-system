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
public class rectLattice extends sandpileLattice {
    
    public rectLattice (int setHeight, int setWidth, int startingDepth, int size){
        super(setHeight, setWidth, startingDepth, size);
        // and then we create the grid:
        nodeLattice = new node[width][height];
        sink = new node(0);
        // first, create all the nodes with the appropriate # of chips
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                nodeLattice[i][j] = new node(startingDepth);
                totalChips += startingDepth;
            }
        }
        // next, give the four corner nodes their exit edges
        nodeLattice[0][0].addNeighbor(nodeLattice[0][1]);
        nodeLattice[0][0].addNeighbor(nodeLattice[1][0]);
        nodeLattice[0][0].addNeighbor(sink);
        nodeLattice[0][0].addNeighbor(sink);
        nodeLattice[width-1][0].addNeighbor(nodeLattice[width-1][1]);
        nodeLattice[width-1][0].addNeighbor(nodeLattice[width-2][0]);
        nodeLattice[width-1][0].addNeighbor(sink);
        nodeLattice[width-1][0].addNeighbor(sink);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[0][height-2]);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[1][height-1]);
        nodeLattice[0][height-1].addNeighbor(sink);
        nodeLattice[0][height-1].addNeighbor(sink);
        nodeLattice[width-1][height-1].addNeighbor(nodeLattice[width-1][height-2]);
        nodeLattice[width-1][height-1].addNeighbor(nodeLattice[width-2][height-1]);
        nodeLattice[width-1][height-1].addNeighbor(sink);
        nodeLattice[width-1][height-1].addNeighbor(sink);
        // then the left-most and right-most columns, apart from the corners
        for(j = 1; j < height-1; j++){
            nodeLattice[0][j].addNeighbor(nodeLattice[0][j+1]);
            nodeLattice[0][j].addNeighbor(nodeLattice[0][j-1]);
            nodeLattice[0][j].addNeighbor(nodeLattice[1][j]);
            nodeLattice[0][j].addNeighbor(sink);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[width-1][j+1]);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[width-1][j-1]);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[width-2][j]);
            nodeLattice[width-1][j].addNeighbor(sink);
        }
        // then fill in the middle columns
        for(i=1; i<width-1; i++){
            // the two endpoints will border a sink, the rest we do by a for loop
            nodeLattice[i][0].addNeighbor(nodeLattice[i][1]);
            nodeLattice[i][0].addNeighbor(nodeLattice[i+1][0]);
            nodeLattice[i][0].addNeighbor(nodeLattice[i-1][0]);
            nodeLattice[i][0].addNeighbor(sink);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i][height-2]);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i+1][height-1]);
            nodeLattice[i][height-1].addNeighbor(nodeLattice[i-1][height-1]);
            nodeLattice[i][height-1].addNeighbor(sink);
            for(j = 1; j < height-1; j++){
                nodeLattice[i][j].addNeighbor(nodeLattice[i][j+1]);
                nodeLattice[i][j].addNeighbor(nodeLattice[i][j-1]);
                nodeLattice[i][j].addNeighbor(nodeLattice[i+1][j]);
                nodeLattice[i][j].addNeighbor(nodeLattice[i-1][j]);
            }
        }
    }
    
    @Override
    public Set fireAll(){
        recheck = new HashSet();
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                nodeLattice[i][j].fire(recheck, totalFirings);
            }
        }
        if(recheck.contains(sink)){
            recheck.remove(sink);
        }
        return recheck;
    }
    
    public void setColors (Color neg, Color zero, Color one, Color two, Color three, Color ready, Color center){
        this.neg = neg;
        this.zero = zero;
        this.one = one;
        this.two = two;
        this.three = three;
        this.ready = ready;
        this.center = center;
    }
   
    
    public boolean nodesReady(){
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                if(nodeLattice[i][j].numChips() >= nodeLattice[i][j].numExits()){
                    return true;
                }
            }
        }
        return false;
    }
    

    
    public void stabilize(){
        recheck = new HashSet();
        for (i = 0; i < width; i++){
            for(j = 0; j < height; j++){
                nodeLattice[i][j].fire(recheck, totalFirings);
            }
        }
        k=1;
        while(recheck.size()>0){
            this.repaint();
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
    
    @Override
    public void paintComponent (Graphics g){
        int a, b;
        //first, clear the image:
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width*squareSize, height*squareSize);
        switch (displayType) {
            case "chips":
                //then mark any nodes which are ready to fire as ready to fire, then do the others
                for (a = 0; a < width; a++){
                    for(b = 0; b < height; b++){
                        if(nodeLattice[a][b].numChips() >= nodeLattice[a][b].numExits()){
                            g.setColor(ready);
                            g.fillRect(a*squareSize, b*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[a][b].numChips() < 0){
                            g.setColor(neg);
                            g.fillRect(a*squareSize, b*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[a][b].numChips() == 0){
                            g.setColor(zero);
                            g.fillRect(a*squareSize, b*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[a][b].numChips() == 1){
                            g.setColor(one);
                            g.fillRect(a*squareSize, b*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[a][b].numChips() == 2){
                            g.setColor(two);
                            g.fillRect(a*squareSize, b*squareSize, squareSize, squareSize);
                        }
                        else if(nodeLattice[a][b].numChips() == 3){
                            g.setColor(three);
                            g.fillRect(a*squareSize, b*squareSize, squareSize, squareSize);
                        }
                    }
                }
                break;
            case "time":
                Color recentShade;
                int min = totalFirings-1;
                for (i = 0; i < width; i++){
                    for(j = 0; j < height; j++){
                        if(nodeLattice[i][j].lastFired() < min){
                            min = nodeLattice[i][j].lastFired();
                        }
                    }
                }
                for (i = 0; i < width; i++){
                    for(j = 0; j < height; j++){
                        k = 255*(nodeLattice[i][j].lastFired()-min)/(totalFirings-min);
                        recentShade = new Color(k,0,255-k);
                        g.setColor(recentShade);
                        g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                    }
                }
                System.out.println("Min: " + min + ", Total Firings: " + totalFirings);
                break;
            case "firings":
                Color firingsShade;
                int max = 1;
                for (i = 0; i < width; i++){
                    for(j = 0; j < height; j++){
                        if(nodeLattice[i][j].numFirings() > max){
                            max = nodeLattice[i][j].numFirings();
                        }
                    }
                }
                for (i = 0; i < width; i++){
                    for(j = 0; j < height; j++){
                        k = 255*(nodeLattice[i][j].numFirings())/(max);
                        firingsShade = new Color(k,0,255-k);
                        g.setColor(firingsShade);
                        g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
                    }
                }
                break;
        }
        // and mark the center
        g.setColor(center);
        g.fillOval(centerX*squareSize+1, centerY*squareSize+1, squareSize-2, squareSize-2);
    }
    
    

}
