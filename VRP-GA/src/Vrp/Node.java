/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vrp;

public class Node {
    public int x;
    public int y;
    public int demanda;
    
    Node(){
        this.x = 0;
        this.y = 0;
        this.demanda = 0;
    }
    
    Node(int ix, int iy, int idemanda){
        this.x = ix;
        this.y = iy;
        this.demanda = idemanda;
    }
    
    public double distancia(Node n){
        return Math.sqrt(Math.pow((n.x + this.x),2) + Math.pow((n.y + this.y),2));
    }
}
