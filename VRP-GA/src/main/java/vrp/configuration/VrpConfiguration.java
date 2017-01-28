package vrp.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class VrpConfiguration {

    private int numberOfTrucks;
    private int graphDimension;
    private int vehicleCapacity;

    private Node nodes[];

    public VrpConfiguration(final String file) throws IOException {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {

            String line;

            boolean demand = false;

            while ((line = br.readLine()) != null) {
                if (line.contains("DIMENSION")) {
                    this.graphDimension = Integer.parseInt(line.split(":")[1].trim());
                    this.nodes = new Node[graphDimension];
                } else if (line.contains("CAPACITY")) {
                    vehicleCapacity = Integer.parseInt(line.split(":")[1].trim());
                    break;
                }
            }


            br.readLine(); // this line is to jump the "NODE_COORD_SECTION" line

            int i = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains("DEPOT_SECTION"))
                    break;
                if (line.contains("DEMAND_SECTION")) {
                    demand = true;
                    i = 0;
                    line = br.readLine();
                }
                if (!demand) {
                    final String[] tokens = line.trim().split(" ");
                    final int x = Integer.parseInt(tokens[1]);
                    final int y = Integer.parseInt(tokens[2]);
                    nodes[i] = new Node(x, y, i);
                    i++;
                } else {
                    nodes[i++].setDemand(Integer.parseInt(line.trim().split(" ")[1]));
                }
            }
        }

        this.numberOfTrucks = (int) Math.ceil(1.0 * getTotalDemand() / vehicleCapacity);
    }

    public VrpConfiguration(final String file, final int numberOfTrucks) throws IOException {
        this(file);
        this.numberOfTrucks = numberOfTrucks;
    }


    public int getNumberOfTrucks() {
        return numberOfTrucks;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public Node getNode(final int p) {
        return nodes[p];
    }

    public int getGraphDimension() {
        return graphDimension;
    }

    public int getTotalDemand() {
        return Arrays.stream(this.nodes).mapToInt(Node::getDemand).sum();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("total number of nodes: ").append(this.graphDimension).append("\n");
        stringBuilder.append("capacity of vehicles: ").append(this.vehicleCapacity).append("\n");
        stringBuilder.append("number of vehicles: ").append(this.numberOfTrucks).append("\n");
        for (int i = 0; i < this.graphDimension; i++) {
            stringBuilder.append("Node ").append(i + 1).append(" ").append(this.nodes[i]).append("\n");
        }
        stringBuilder.append("total demanded: ").append(this.getTotalDemand()).append("\n");
        return stringBuilder.toString();
    }
}
