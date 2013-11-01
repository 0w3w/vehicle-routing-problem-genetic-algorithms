package Vrp;
import java.util.LinkedList;
import java.util.Scanner;
import org.jgap.*;
import org.jgap.impl.*;

public class Vrp {

    private static final int MAX_EVOLUTIONS   = 5000;

    public static void Vrp() throws Exception{
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
       
        System.out.println("------VRP-----");
        System.out.println("Elige el archivo con el problema vrp: ");
        System.out.println("0) test [10 destinos]");
        System.out.println("1) 45 destinos");
        System.out.println("2) 60 destinos");
        System.out.println("");
        int seleccion, camiones;
        Scanner scanIn = new Scanner(System.in);
        VrpConfiguration vrpconf = null;
        seleccion = scanIn.nextInt();
        System.out.println("Coloca el numero de camiones: ");
        camiones = scanIn.nextInt();
        if (seleccion == 0)
        {
            vrpconf = new VrpConfiguration();
        }
        else if (seleccion == 1)
        {
            vrpconf = new VrpConfiguration("Extras/A-n45-k6-in.txt", camiones);
        }
        else if (seleccion == 2)
        {
            vrpconf = new VrpConfiguration("Extras/A-n60-k0-in.txt", camiones);
        }
        else
        {
            vrpconf = new VrpConfiguration();
        }
        vrpconf.print();
        FitnessFunction myFunc = new VrpFitnessFunc(vrpconf);

        conf.setFitnessFunction(myFunc);
        Gene[] sampleGenes = new Gene[vrpconf.GRAPH_DIMENSION];
        /*
         * Iniciar los Genes en sus valores minimos y máximos
        */
        for(int i=0; i<vrpconf.GRAPH_DIMENSION; i++){
        	sampleGenes[i] = new IntegerGene(conf, 0, (vrpconf.VEHICLE_NUMBER-1));
        }
        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(60);

        Genotype population;
        population = Genotype.randomInitialGenotype(conf);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MAX_EVOLUTIONS; i++) {
        	if(i%50 == 0)
        		System.out.print(".");
        	if(i%5000 == 0)
        		System.out.println("");
        	if (!uniqueChromosomes(population.getPopulation())) {
        		throw new RuntimeException("Generación inválida en la evolucion: " + i);
        	}
        	population.evolve();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("");
        System.out.println("Tiempo total de la evolución: " + ( endTime - startTime) + " ms");
        System.out.println("Numero total de evoluciónes: " + MAX_EVOLUTIONS);

        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        double v1 = bestSolutionSoFar.getFitnessValue();
        System.out.println("La mejor solución fitness fue: " + v1);
        bestSolutionSoFar.setFitnessValueDirectly(-1);
        System.out.println("Resultado: ");
        for (int i = 0; i < vrpconf.GRAPH_DIMENSION; i++) {
           System.out.println(i +". " + VrpFitnessFunc.getNumberAtGene(bestSolutionSoFar, i) );  
        }
        Double  distance = 0.0;
        Double  distancep= 0.0;
        LinkedList routes;
        for(int i = 0; i<vrpconf.VEHICLE_NUMBER;i++){
            distancep = VrpFitnessFunc.getDistance(i, bestSolutionSoFar, vrpconf);
            routes = VrpFitnessFunc.getPositions(i, bestSolutionSoFar, vrpconf);
            System.out.print("Ruta #" + i + " :");
            while(!routes.isEmpty()){
                int pos = ((Integer) routes.pop()).intValue();
                System.out.print(pos + ", ");
            }
            System.out.println();
            System.out.println("\t La distancia de la ruta es: "+distancep);
            distance += distancep;
        }
        System.out.println("La mejor distancia fue: " + distance);
        System.out.println();
    }
    
    public static boolean uniqueChromosomes(Population a_pop) {
        for(int i=0;i<a_pop.size()-1;i++) {
          IChromosome c = a_pop.getChromosome(i);
          for(int j=i+1;j<a_pop.size();j++) {
            IChromosome c2 =a_pop.getChromosome(j);
            if (c == c2) {
              return false;
            }
          }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        Vrp();
    }
    
}
