package develop.sanstorik.com.genetic_coursework.Genetic;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GeneticAlgorithm {
    private final int INITIAL_POPULATION_COUNT = 10;
    private final double ACCURACY = 6.1e-5;
    private final double PERCENT_TO_DIE = 1e-1;

    private float breedingIndividualCount;
    private float mutationPossibility;

    private Population currentPopulation;

    private GeneticAlgorithm(){
        currentPopulation = new Population();
    }

    public void test(){
        spawnInitialPopulation();

        for(Individual ind : currentPopulation)
            Log.i("tag", ind.toString());

        mutationProcess();
        currentPopulation = reproductionProcess();

        Log.i("tag", "  ");
        for(Individual ind : currentPopulation)
            Log.i("tag", ind.toString());
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

    private Queue<Population> tournamentSelection(Population population){
        Queue<Population> clusters = new LinkedList<>();

        //int clustersCount = population.ge
       // for(int i=0; i < )
        //for(int i = 0; i < population.)

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
