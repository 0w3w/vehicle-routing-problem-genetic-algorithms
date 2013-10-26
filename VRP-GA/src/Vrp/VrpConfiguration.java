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
    private int GRAPH_DIMENSION;
    private int VEHICLE_CAPACITY;
    private int VEHICLE_NUMBER;
    public Node nodos[];
    
    VrpConfiguration()
    {
        this.GRAPH_DIMENSION = 0;
        this.VEHICLE_CAPACITY = 0;
        this.VEHICLE_NUMBER = 0;
        nodos = new Node[GRAPH_DIMENSION];
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
                    GRAPH_DIMENSION = Integer.parseInt(value[1]);
                }
                else if (line.contains("CAPACITY"))
                {
                    value = line.split(":");
                    VEHICLE_CAPACITY = Integer.parseInt(value[1]);
                }
            }
            nodos = new Node[GRAPH_DIMENSION];
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
                }
                else
                {
                    value = line.split(" ");
                    d = Integer.parseInt(value[1]);
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

    /**
     * @return the GRAPH_DIMENSION
     */
    public int getGRAPH_DIMENSION() {
        return GRAPH_DIMENSION;
    }

    /**
     * @param GRAPH_DIMENSION the GRAPH_DIMENSION to set
     */
    public void setGRAPH_DIMENSION(int GRAPH_DIMENSION) {
        this.GRAPH_DIMENSION = GRAPH_DIMENSION;
    }

    /**
     * @return the VEHICLE_CAPACITY
     */
    public int getVEHICLE_CAPACITY() {
        return VEHICLE_CAPACITY;
    }

    /**
     * @param VEHICLE_CAPACITY the VEHICLE_CAPACITY to set
     */
    public void setVEHICLE_CAPACITY(int VEHICLE_CAPACITY) {
        this.VEHICLE_CAPACITY = VEHICLE_CAPACITY;
    }

    /**
     * @return the VEHICLE_NUMBER
     */
    public int getVEHICLE_NUMBER() {
        return VEHICLE_NUMBER;
    }

    /**
     * @param VEHICLE_NUMBER the VEHICLE_NUMBER to set
     */
    public void setVEHICLE_NUMBER(int VEHICLE_NUMBER) {
        this.VEHICLE_NUMBER = VEHICLE_NUMBER;
    }
    
}
