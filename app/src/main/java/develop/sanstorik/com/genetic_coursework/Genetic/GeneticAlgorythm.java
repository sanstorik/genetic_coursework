package develop.sanstorik.com.genetic_coursework.Genetic;

import android.util.Log;

import java.util.BitSet;

public final class GeneticAlgorythm {
    private final int INITIAL_POPULATION_COUNT = 10;
    private final double ACCURACY = 6.1e-5;
    private float breedingIndividualCount;
    private float mutationPossibility;

    private GeneticAlgorythm(){}

    public static GeneticAlgorythm newInstance(){
        return new GeneticAlgorythm();
    }

    public void test(){
        boolean[] genes = new boolean[14];

        for(int i=0; i < genes.length; i++)
            genes[i] = true;

        Individual individual = new Individual(genes);

        Log.i("tag", individual.toString());
    }
}
