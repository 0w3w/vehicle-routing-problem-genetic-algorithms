/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vrp;

public class Node {
    private int x;
    private int y;
    private int demanda;
    
    Node(){
        this.x = 0;
        this.y = 0;
        this.demanda = 0;
    }
    
    Node(int ix, int iy){
        this.x = ix;
        this.y = iy;
        this.demanda = 0;
    }
    
    public double distancia(Node n){
        return Math.sqrt(Math.pow((n.getX() - this.getX()),2) + Math.pow((n.getY() - this.getY()),2));
    }
    
    public void print(){
        System.out.println("(" + this.x + " , " + this.y + ") - Demanda: " + this.demanda);
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the demanda
     */
    public int getDemanda() {
        return demanda;
    }

    /**
     * @param demanda the demanda to set
     */
    public void setDemanda(int demanda) {
        this.demanda = demanda;
    }
}
