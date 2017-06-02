package develop.sanstorik.com.genetic_coursework.Genetic;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class GeneticAlgorithm {
    private final int INITIAL_POPULATION_COUNT = 10;
    private final double ACCURACY = 6.1e-5;

    private int breedingIndividualCount = 20;
    private float mutationPossibility = 0.1f;

    private Population currentPopulation;

    private GeneticAlgorithm(){
        currentPopulation = new Population();
    }

    public void test(){
        spawnInitialPopulation();

        for(Individual ind : currentPopulation)
            Log.i("tag", ind.toString());

        mutationProcess();
       // currentPopulation = reproductionProcess();
        tournamentSelection(currentPopulation);

            /*
            Log.i("tag", "  ");
            for(Individual ind : currentPopulation)
            Log.i("tag", ind.toString());
            */
    }

    private void spawnInitialPopulation(){
        for(int i=0; i < INITIAL_POPULATION_COUNT; i++)
            currentPopulation.add(spawnRandomIndividual());
    }

    private void mutationProcess(){
        for(Individual ind : currentPopulation)
            if(Math.random() <= mutationPossibility)
                ind.mutate();
    }

    private Population reproductionProcess(){
        Population newPopulation = new Population();

        for(int i=0; i < breedingIndividualCount; i++){
            newPopulation.add(
                    currentPopulation.getRandomIndividual()
                    .crossover(currentPopulation.getRandomIndividual())
            );
        }

        return newPopulation;
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

    private Queue<Population> dividePopulationIntoClusters(Population parents){
        Queue<Population> clusters = new LinkedList<>();
        clusters.add(new Population());

        int individualsCount = 0;
        final int individualsPerCluster = 3;

        for(Individual ind : parents){
            individualsCount++;

            clusters.peek().add(ind);
            if(individualsCount % individualsPerCluster == 0)
                clusters.add(new Population());
        }

        Log.i("tag", "size = " + String.valueOf(clusters.size()));

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
