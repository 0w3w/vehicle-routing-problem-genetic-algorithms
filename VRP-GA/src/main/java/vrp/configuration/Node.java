package vrp.configuration;

public class Node {
    private int x;
    private int y;
    private int index;
    private int demand;


    Node(int x, int y, int index) {
        this(x, y, index, 0);
    }

    Node(final int x, final int y, final int index, final int demand) {
        this.x = x;
        this.y = y;
        this.demand = demand;
        this.index = index;
    }

    /**
     * Computes the euclidean distance between the current node and the given node
     *
     * @param node given node
     * @return distance from this to node
     */
    public double distanceTo(final Node node) {
        return Math.sqrt(Math.pow((node.x - this.x), 2) + Math.pow((node.y - this.y), 2));
    }

    public int getDemand() {
        return demand;
    }


    public int getIndex() {
        return index;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ") - Demand: " + this.demand;
    }
}
