package develop.sanstorik.com.genetic_coursework.Genetic;

import android.util.Log;

public class GeneticAlgorithm {
    private final int INITIAL_POPULATION_COUNT = 10;
    private final double ACCURACY = 6.1e-5;

    private float breedingIndividualCount;
    private float mutationPossibility;

    private Population initialPopulation;

    private GeneticAlgorithm(){
        initialPopulation = new Population();
    }

    public void test(){
        boolean[] genes = new boolean[14];

        for(int i=0; i < genes.length; i++)
            genes[i] = true;

        Individual individual = new Individual(genes);

        spawnInitialPopulation();
        for(Individual ind : initialPopulation)
            Log.i("tag", ind.toString());
    }

    private void spawnInitialPopulation(){
        for(int i=0; i < INITIAL_POPULATION_COUNT; i++)
            initialPopulation.add(spawnRandomIndividual());
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
