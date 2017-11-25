/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lattices;

import sandpile.node;

/**
 *
 * @author Edward Newkirk
 */
public class CylinderLattice extends rectLattice {
    
    public CylinderLattice (int setHeight, int setWidth, int startingDepth, int size){
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
        nodeLattice[0][0].addNeighbor(nodeLattice[width-1][0]);
        nodeLattice[0][0].addNeighbor(sink);
        nodeLattice[width-1][0].addNeighbor(nodeLattice[width-1][1]);
        nodeLattice[width-1][0].addNeighbor(nodeLattice[width-2][0]);
        nodeLattice[width-1][0].addNeighbor(nodeLattice[0][0]);
        nodeLattice[width-1][0].addNeighbor(sink);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[0][height-2]);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[1][height-1]);
        nodeLattice[0][height-1].addNeighbor(nodeLattice[width-1][height-1]);
        nodeLattice[0][height-1].addNeighbor(sink);
        nodeLattice[width-1][height-1].addNeighbor(nodeLattice[width-1][height-2]);
        nodeLattice[width-1][height-1].addNeighbor(nodeLattice[width-2][height-1]);
        nodeLattice[width-1][height-1].addNeighbor(nodeLattice[0][height-1]);
        nodeLattice[width-1][height-1].addNeighbor(sink);
        // then the left-most and right-most columns, apart from the corners
        for(j = 1; j < height-1; j++){
            nodeLattice[0][j].addNeighbor(nodeLattice[0][j+1]);
            nodeLattice[0][j].addNeighbor(nodeLattice[0][j-1]);
            nodeLattice[0][j].addNeighbor(nodeLattice[1][j]);
            nodeLattice[0][j].addNeighbor(nodeLattice[width-1][j]);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[width-1][j+1]);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[width-1][j-1]);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[width-2][j]);
            nodeLattice[width-1][j].addNeighbor(nodeLattice[0][j]);
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
    
    

}
