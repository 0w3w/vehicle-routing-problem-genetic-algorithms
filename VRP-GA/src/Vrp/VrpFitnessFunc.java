package Vrp;
import java.util.LinkedList;
import org.jgap.*;

public class VrpFitnessFunc extends FitnessFunction{
    private VrpConfiguration vrpconf;
    public VrpFitnessFunc(VrpConfiguration conf) {
        System.out.println("Inicializo la función de fitness");
        this.vrpconf = conf;
    }
    
    @Override
    public double evaluate(IChromosome chromosome) {
        double fitness=0;
	/* 
	 * Ir penalizando la función fitness conforme al valor que contenga del Cromosoma
	 * Entre más se aleje del valor, se agrega más penalización
	int indiceCromosoma = 0;
	fitness += (double) chromosome.getGene(indiceCromosoma).getAllele()).intValue();
	*/
	// Para cada Vehiculo
        for(int i = 0; i<this.vrpconf.VEHICLE_NUMBER;i++){
            // Esta presente en el cromosoma (minimo una visita) No estoy seguro que sea necesario
            // fitness += this.isPresent(i, chromosome);
            // Distancia que recorre cada vehículo
            fitness += this.getDistance(i, chromosome);
            // Entrega toda su capacidad
            fitness += this.getCapacity(i, this.vrpconf.VEHICLE_CAPACITY, chromosome);
        }
                
	if (fitness<0){
		return 0;
	}
	//fitness = Integer.MAX_VALUE/2 - fitness;
        fitness = 100000 - fitness;
	return Math.max(1.0d, fitness);
    }
    
    public double isPresent(int vehicleNumber, IChromosome chromosome){
        // Sin contar el primer elemento, ya que este es el origen
        LinkedList positions = getPositions(vehicleNumber, chromosome);
        if(positions.size() > 0){
            return 0.0;
        }
        return 100.00;
    }
    
    public double getDistance(int vehicleNumber, IChromosome chromosome){
        double totalDistance    = 0.0;
        LinkedList positions    = getPositions(vehicleNumber, chromosome);
        Node deposito           = this.vrpconf.nodos[0];
        Node ultimaVisita       = deposito;
        
        while(!positions.isEmpty()){
            int pos = ((Integer) positions.pop()).intValue();
            Node visita  = this.vrpconf.nodos[pos];
            totalDistance += ultimaVisita.distancia(visita);
            ultimaVisita = visita;
        }
        
        totalDistance += ultimaVisita.distancia(deposito);

        return totalDistance;
    }
    
    public double getCapacity(int vehicleNumber, int vehicleCapacity, IChromosome chromosome){
        double demandaTotal = 0.0;
        LinkedList positions = getPositions(vehicleNumber, chromosome);
        while(!positions.isEmpty()){
            int pos = ((Integer) positions.pop()).intValue();
            Node visita  = this.vrpconf.nodos[pos];
            demandaTotal += visita.getDemanda();
        }
        
        if(demandaTotal > vehicleCapacity){
            // Mas penalización si tiene mas demanda de la capacidad
            return (demandaTotal-vehicleCapacity)*10;
        }
        // Muy poca penalización por no terminar toda su capacidad
        return (vehicleCapacity-demandaTotal)*2;
    }
    
    public LinkedList getPositions(int vehicleNumber, IChromosome chromosome){
        // Sin contar el deposito
        LinkedList p = new LinkedList();
        for(int i=1; i < this.vrpconf.GRAPH_DIMENSION; i++){
            int valorCromosoma = ((Integer) chromosome.getGene(i).getAllele()).intValue();
            if(valorCromosoma == i){
               p.add(i);
            }
        }
        return p;
    }
    
    public static double getNumberAtGene(IChromosome a_potentialSolution, int a_position) {
        Integer numAsignado = ((Integer) a_potentialSolution.getGene(a_position).getAllele()).intValue();
        return numAsignado.intValue();
    }
}
