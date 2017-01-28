package vrp.app;

import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vrp.configuration.VrpConfiguration;
import vrp.evolution.VrpFitnessFunc;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Vrp {


    private static Logger log = LoggerFactory.getLogger(Vrp.class);

    private static final int EVOLUTIONS = 2000;
    private static final int POPULATION_SIZE = 350;

    private static final String SET_NAME = "/A-n45-k6-in.txt";
    private static final int NUMBER_OF_TRUCKS = 6;

    public static void main(String[] args) throws Exception {
        final Configuration configuration = new DefaultConfiguration();
        final VrpConfiguration vrpConfiguration = new VrpConfiguration(SET_NAME, NUMBER_OF_TRUCKS);
        configuration.setPreservFittestIndividual(true);
        configuration.setFitnessFunction(new VrpFitnessFunc(vrpConfiguration));
        configuration.setPopulationSize(POPULATION_SIZE);

        log.info("Loaded vrp configuration:\n" + vrpConfiguration);

        final int graphDimension = vrpConfiguration.getGraphDimension();
        final Gene[] genes = new Gene[2 * graphDimension];
        for (int i = 0; i < graphDimension; i++) {
            genes[i] = new IntegerGene(configuration, 1, NUMBER_OF_TRUCKS);
            genes[i + graphDimension] = new DoubleGene(configuration, 0, 45);//to keep order of nodes
        }

        configuration.setSampleChromosome(new Chromosome(configuration, genes));
        final Genotype population = Genotype.randomInitialGenotype(configuration);

        final Instant start = Instant.now();
        log.info("Generations: " + EVOLUTIONS);
        for (int i = 1; i <= EVOLUTIONS; i++) {
            if (i % 100 == 0) {
                final IChromosome bestSolution = population.getFittestChromosome();
                log.info("Best fitness after " + i + " evolutions: " + bestSolution.getFitnessValue());
                double total = 0;
                final List<Double> demands = new ArrayList<>();
                for (int j = 1; j <= NUMBER_OF_TRUCKS; ++j) {
                    final double distanceRoute = VrpFitnessFunc.computeTotalDistance(j, bestSolution, vrpConfiguration);
                    final double demand = VrpFitnessFunc.computeTotalCoveredDemand(j, bestSolution, vrpConfiguration);
                    total += distanceRoute;
                    demands.add(demand);
                }
                log.info("Total distance: " + total);
                log.info("Covered demands: " + demands);
            }
            population.evolve();
        }


        log.info("Execution time: " + Duration.between(start, Instant.now()));


        final IChromosome bestSolution = population.getFittestChromosome();

        log.info("Best fitness: " + bestSolution.getFitnessValue());
        log.info("Result: ");
        for (int i = 0; i < 2 * graphDimension; i++) {
            log.info((i + 1) + ". " + bestSolution.getGene(i).getAllele());
        }

        double total = 0.0;

        for (int i = 1; i <= NUMBER_OF_TRUCKS; ++i) {
            final List<Integer> route = VrpFitnessFunc.getPositions(i, bestSolution, vrpConfiguration, true);
            final double distanceRoute = VrpFitnessFunc.computeTotalDistance(i, bestSolution, vrpConfiguration);
            final double demand = VrpFitnessFunc.computeTotalCoveredDemand(i, bestSolution, vrpConfiguration);
            log.info("Vehicle #" + i + " :" + formatRoute(route));
            log.info("Distance: " + distanceRoute);
            log.info("Demand: " + demand);
            total += distanceRoute;
        }
        log.info("Total distance: " + total);

    }

    private static List<Integer> formatRoute(List<Integer> list) {
        final List<Integer> result = new ArrayList<>(Collections.singletonList(1));//source node
        result.addAll(list.stream().map(aList -> aList + 1).collect(Collectors.toList()));
        return result;
    }

}

