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
        BitSet bitSet = new BitSet(Individual.GENES_SIZE);

        bitSet.set(5);
        bitSet.set(3);

        Individual individual = new Individual(bitSet);

        Log.i("tag", individual.toString());
    }
}
