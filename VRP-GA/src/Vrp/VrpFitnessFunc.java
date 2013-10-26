package Vrp;
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
            // Esta presente en el cromosoma (minimo una visita)
            fitness += this.isPresent(i, chromosome);
            // Distancia que recorre cada vehículo
            fitness += this.getDistance(i, chromosome);
            // Entrega toda su capacidad
            fitness += this.getCapacity(i, this.vrpconf.VEHICLE_CAPACITY, chromosome);
        }
                
	if (fitness<0){
		return 0;
	}
	fitness = Integer.MAX_VALUE/2 - fitness;
	return Math.max(1.0d, fitness);
    }
    
    public double isPresent(int vehicleNumber, IChromosome chromosome){
        return 0.0;
    }
    
    public double getDistance(int vehicleNumber, IChromosome chromosome){
        return 0.0;
    }
    
    public double getCapacity(int vehicleNumber, int vehicleCapacity, IChromosome chromosome){
        return 0.0;
    }
    
    public static double getNumberAtGene(IChromosome a_potentialSolution, int a_position) {
        Integer numAsignado = ((Integer) a_potentialSolution.getGene(a_position).getAllele()).intValue();
        return numAsignado.intValue();
    }
}
