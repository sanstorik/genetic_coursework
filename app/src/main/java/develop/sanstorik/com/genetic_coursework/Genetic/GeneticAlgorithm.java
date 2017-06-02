package develop.sanstorik.com.genetic_coursework.Genetic;

import android.util.Log;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class GeneticAlgorithm {
    private final double ACCURACY = 6.1e-5;

    private int breedingIndividualCount = 15;
    private float mutationPossibility = 0.1f;

    private Population currentPopulation;

    private GeneticAlgorithm(){
        currentPopulation = new Population();
    }

    public void solve(){
        spawnInitialPopulation();

        for(Individual ind : currentPopulation)
            Log.i("tag", ind.toString());

        for(int i=0; i < 25; i++) {
            mutationProcess(currentPopulation);
            currentPopulation = reproductionProcess(currentPopulation);

            Log.i("tag", "  new selection ");
            for (Individual ind : currentPopulation)
                Log.i("tag", ind.toString());
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

        Log.i("tag", ""+clusters.size());

        return clusters;
    }

    private Individual spawnRandomIndividual(){
        boolean[] genes = new boolean[Individual.GENES_SIZE];

        for(int i=0; i < genes.length; i++)
            genes[i] = Math.random() < 0.5f;

        return new Individual(genes);
    }

    public static GeneticAlgorithm newInstance(){
        return new GeneticAlgorithm();
    }
}
