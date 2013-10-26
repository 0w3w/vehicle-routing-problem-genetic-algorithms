package Vrp;
import org.jgap.*;
import org.jgap.impl.*;

public class Vrp {

    private static final int MAX_EVOLUTIONS   = 2000;
    
    public static void Vrp() throws Exception{
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
       
        VrpConfiguration vrpconf = new VrpConfiguration("A-n45-k6-in.txt", 6);
        
        FitnessFunction myFunc = new VrpFitnessFunc(vrpconf);

        conf.setFitnessFunction(myFunc);
        Gene[] sampleGenes = new Gene[vrpconf.getGRAPH_DIMENSION()];
        /*
         * Iniciar los Genes en sus valores minimos y máximos
        */
        for(int i=0; i<vrpconf.getGRAPH_DIMENSION(); i++){
        	sampleGenes[i] = new IntegerGene(conf, 1, vrpconf.getVEHICLE_NUMBER());
        }
        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(30);

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
        System.out.println("La mejor solución fue: " + v1);
        bestSolutionSoFar.setFitnessValueDirectly(-1);
        System.out.println("Resultado: ");
        for (int i = 0; i < vrpconf.getGRAPH_DIMENSION(); i++) {
           System.out.println(i +". " + VrpFitnessFunc.getNumberAtGene(bestSolutionSoFar, i) );  
        }
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
