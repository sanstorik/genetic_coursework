package develop.sanstorik.com.genetic_coursework.Genetic;

import android.util.Log;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class GeneticAlgorithm {
    private final double ACCURACY = 6.1e-5;

    private final int breedingIndividualCount;
    private final float mutationPossibility;

    private Population currentPopulation;
    private Deque<Population> generations;
    private Deque<Individual> bestIndividuals;

    private GeneticAlgorithm(int breedingIndividualCount, float mutationPossibility){
        currentPopulation = new Population();
        generations = new LinkedList<>();
        bestIndividuals = new LinkedList<>();

        this.breedingIndividualCount = breedingIndividualCount;
        this.mutationPossibility = mutationPossibility;
    }

    public void solve(){
        spawnInitialPopulation();

        for(Individual ind : currentPopulation)
            Log.i("tag", ind.toString());

        for(int i=0; i < 100; i++) {
            mutationProcess(currentPopulation);
            currentPopulation = reproductionProcess(currentPopulation);

            Log.i("tag", "  new selection ");
            for (Individual ind : currentPopulation)
                Log.i("tag", ind.toString());

            generations.offerLast(new Population(currentPopulation));
            bestIndividuals.offerLast(Collections.max(currentPopulation.getIndividuals()));

            Log.i("tag", "best = " + bestIndividuals.peekLast().toString());
        }

    }

    private void spawnInitialPopulation(){
        final int INITIAL_POPULATION_COUNT = 10;

        for(int i=0; i < INITIAL_POPULATION_COUNT; i++)
            currentPopulation.add(spawnRandomIndividual());
    }

    private void mutationProcess(Population population){
        for(Individual ind : population)
            if(Math.random() <= mutationPossibility)
                ind.mutate();
    }

    private Population reproductionProcess(Population parents){
        Population newPopulation = new Population();

        for(int i=0; i < breedingIndividualCount; i++){
            newPopulation.add(
                    parents.getRandomIndividual()
                    .crossover(parents.getRandomIndividual())
            );
        }

        for(Individual ind : parents)
            newPopulation.add(ind);

        return tournamentSelection(newPopulation);
    }

    private Population tournamentSelection(Population parents){
        Population selection = new Population();
        Queue<Population> clusters = dividePopulationIntoClusters(parents);

        for(Population cluster : clusters)
            selection.add(
                    Collections.max(cluster.getIndividuals())
            );

        return selection;
    }

    private Deque<Population> dividePopulationIntoClusters(Population parents){
        Deque<Population> clusters = new LinkedList<>();

        int individualsCount = 0;
        final int individualsPerCluster = 2;

        for(Individual ind : parents){
            if(individualsCount % individualsPerCluster == 0) {
                clusters.offer(new Population());
            }

            individualsCount++;

            clusters.peekLast().add(ind);
        }

        return clusters;
    }

    private Individual spawnRandomIndividual(){
        boolean[] genes = new boolean[Individual.GENES_SIZE];

        for(int i=0; i < genes.length; i++)
            genes[i] = Math.random() < 0.5f;

        return new Individual(genes);
    }

    public static GeneticAlgorithm newInstance(int breedingIndividualCount, float mutationPossibility){
        return new GeneticAlgorithm(breedingIndividualCount, mutationPossibility);
    }
}
