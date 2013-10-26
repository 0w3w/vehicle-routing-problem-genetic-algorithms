/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vrp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VrpConfiguration 
{
    public int GRAPH_DIMENSION;
    public int VEHICLE_CAPACITY;
    public int VEHICLE_NUMBER;
    public Node nodos[];
    
    VrpConfiguration()
    {
        // Init with a Dummy configuration, testing purposes
        this.GRAPH_DIMENSION = 10;
        this.VEHICLE_CAPACITY = 5;
        this.VEHICLE_NUMBER = 2;
        this.nodos = new Node[GRAPH_DIMENSION];
        this.nodos[0] = new Node(0,0); // Inicio
        this.nodos[1] = new Node(1,1);
        this.nodos[2] = new Node(2,2);
        this.nodos[3] = new Node(4,0);
        this.nodos[4] = new Node(-1,1);
        this.nodos[5] = new Node(-3,1);
        this.nodos[6] = new Node(-1,-2);
        this.nodos[7] = new Node(-1,-1);
        this.nodos[8] = new Node(-1,-4);
        this.nodos[9] = new Node(1,-2);
        for(int i=0; i<this.GRAPH_DIMENSION;i++){
            this.nodos[i].setDemanda(1);
        }
        System.out.println("Dummy Configuration created");
    }
    
    VrpConfiguration(String file, int vehicles) throws FileNotFoundException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String [] value;
        int i = 0;
        int x, y, d;
        boolean demand = false;
        
        try
        {
            VEHICLE_NUMBER = vehicles;
            while ((line = br.readLine()) != null)
            {
                if (line.contains("DIMENSION"))
                {
                    value = line.split(":");
                    GRAPH_DIMENSION = Integer.parseInt(value[1].trim());
                }
                else if (line.contains("CAPACITY"))
                {
                    value = line.split(":");
                    VEHICLE_CAPACITY = Integer.parseInt(value[1].trim());
                }
            }
            this.nodos = new Node[GRAPH_DIMENSION];
            line = br.readLine(); // this line is to jump the "NODE_COORD_SECTION" line
            while ((line = br.readLine()) != null)
            {
                if (line.contains("DEPOT_SECTION"))
                    break;
                if (line.contains("DEMAND_SECTION"))
                {
                    demand = true;
                    i = 0;
                }
                if (!demand)
                {
                    value = line.split(" ");
                    x = Integer.parseInt(value[1]);
                    y = Integer.parseInt(value[2]);
                    nodos[i++] = new Node(x, y);
                    System.out.println("New node created ("+x+","+y+")");
                }
                else
                {
                    value = line.split(" ");
                    d = Integer.parseInt(value[1]);
                    System.out.println("Set demanda del nodo #"+i+" a "+d);
                    nodos[i++].setDemanda(d);
                }
            }
            System.out.println("Configuration created");
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
        }
        finally
        {
            try
            {
                    br.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex.toString());
            }
        }
    }
    
    public void print(){
        System.out.println("GRAPH_DIMENSION: " + this.GRAPH_DIMENSION);
        System.out.println("VEHICLE_CAPACITY: " + this.VEHICLE_CAPACITY);
        System.out.println("VEHICLE_NUMBER: " + this.VEHICLE_NUMBER);
        for(int i=0; i<this.GRAPH_DIMENSION; i++){
            System.out.print("Nodo "+i+" ");
            this.nodos[i].print();
        }
    }
}
