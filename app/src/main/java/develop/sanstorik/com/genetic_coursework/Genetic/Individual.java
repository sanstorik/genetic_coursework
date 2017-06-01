package develop.sanstorik.com.genetic_coursework.Genetic;

import android.support.annotation.NonNull;

import java.util.BitSet;
import java.util.Random;

class Individual {
    static private class Chromosome{
        static final float MIN_X = 0;
        static final float MAX_X = 4;
        private static final double STEP;
        static final int GENES_SIZE = 16;

        Random random;
        BitSet genes;
        static{
            STEP = ( Math.abs(MIN_X) + Math.abs(MAX_X) ) / Math.pow(2, GENES_SIZE);
        }

        Chromosome(@NonNull BitSet genes){
            this.genes = genes;
            init();
        }

        Chromosome(Chromosome chromosome){
            this(chromosome.getGenes());
        }

        Chromosome(){
            this.genes = new BitSet(GENES_SIZE);
            init();
        }

        //swap two bits
        Chromosome mutate(){
            BitSet bitSet = (BitSet)genes.clone();

            int firstIndex = getRandomBitIndex();
            int secondIndex = getRandomBitIndex();
            boolean firstBit = bitSet.get(GENES_SIZE);
            boolean secondBit = bitSet.get(GENES_SIZE);

            bitSet.set(firstIndex, secondBit);
            bitSet.set(secondIndex, firstBit);

            return new Chromosome(bitSet);
        }

        BitSet getGenes() {
            return (BitSet)genes.clone();
        }

        double getGenesValue(){
            StringBuilder bits = new StringBuilder();

            for(int i=0; i < genes.length(); i++)
                if (genes.get(i))
                    bits.append("1");
                else
                    bits.append("0");

            float bitsValue = Integer.parseInt(bits.toString(), 2);

            return MIN_X + (bitsValue * STEP);
        }

        private void init(){
            random = new Random(47);
        }

        private int getRandomBitIndex(){
            return random.nextInt(GENES_SIZE);
        }
    }


    private Chromosome chromosome;

    Individual(Chromosome chromosome){
        this.chromosome = chromosome;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }
}
