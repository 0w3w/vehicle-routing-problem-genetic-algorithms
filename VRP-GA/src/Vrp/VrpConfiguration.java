/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vrp;

public class VrpConfiguration {
    public int GRAPH_DIMENSION;
    public int VEHICLE_CAPACITY;
    public int VEHICLE_NUMBER;
    public Node nodos[];
    
    VrpConfiguration(){
        this.GRAPH_DIMENSION = 0;
        this.VEHICLE_CAPACITY = 0;
        this.VEHICLE_NUMBER = 0;
        nodos = new Node[0];
    }
    
    VrpConfiguration(String file){
        // TODO: Implement this
    }
    
}
