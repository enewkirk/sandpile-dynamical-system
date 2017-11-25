/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sandpile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;




/**
 *
 * @author Edward Newkirk
 */
public class node {
    int chips; // tracks the number of chips on the node, if negative corresponds to a hole
    int numExits, numEntrances; //the number of outgoing & ingoing edges
    int lastFired; // tracks the last step on which the node was fired
    int numFirings; // tracks the number of times the node's been fired
    List<node> neighbors; // the set of nodes to which this one sends chips
    
    public node(int startingChips){
        chips = startingChips;
        numExits = 0;
        numEntrances=0;
        neighbors = new ArrayList<>();
    }
    
    public void addNeighbor(node n){
        numExits++;
        neighbors.add(n);
        n.addEntrance();
    }
    
    public int numFirings(){
        return numFirings;
    }
    
    public int lastFired(){
        return lastFired;
    }
    
    public void addChip(){
        chips ++;
    }
    
    public void addEntrance(){
        numEntrances++;
    }
    
    public void addChips(int chipsToAdd){
        chips += chipsToAdd;
    }
    
    public int numChips(){
        return chips;
    }
    
    public int numExits(){
        return numExits;
    }
    
    public int numEntrances(){
        return numEntrances;
    }
    
    public void fire(int i){
        // if there's enough chips to send one along each outward edge, this should do so until that's no longer true.
        while (chips >= numExits){
            chips -= numExits;
            for(node n:neighbors){
                n.addChip();
            }
            numFirings ++;
            lastFired = i;
        }
    }
    
    public void fire(Set<node> recheck, int i){
        while (chips >= numExits){
            chips -= numExits;
            for(node n:neighbors){
                n.addChip();
                recheck.add(n);
            }
            numFirings ++;
            lastFired = i;
        }
    }
    
}
