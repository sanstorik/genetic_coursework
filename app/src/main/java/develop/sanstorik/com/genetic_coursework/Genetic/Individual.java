package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.BitSet;
import java.util.Random;

class Individual {
    static final float MIN_X = 0;
    static final float MAX_X = 4;
    static final int GENES_SIZE = 16;

    static private class Chromosome{
        private static final double STEP;
        Random random;
        BitSet genes;

        static{
            STEP = ( Math.abs(MIN_X) + Math.abs(MAX_X) ) / Math.pow(2, GENES_SIZE);
        }

        private Chromosome(BitSet genes){
            this.genes = (BitSet)genes.clone();
            init();
        }

        private Chromosome(Chromosome chromosome){
            this.genes = chromosome.getGenes();
        }

        private Chromosome(){
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

        @Override public String toString() {
            StringBuilder bits = new StringBuilder();

            for(int i=0; i < genes.length(); i++)
                if (genes.get(i))
                    bits.append("1");
                else
                    bits.append("0");

            return bits.toString();
        }
    }


    private Chromosome chromosome;

    Individual(BitSet genes){
        this.chromosome = new Chromosome(genes);
    }

    double getFunctionValue(){
        double x = chromosome.getGenesValue();

        return x * (x - 2) * (x - 2.75f) * Math.exp((x/10)) * Math.cos((x/10)) *(2 - Math.pow(3, x-2));
    }

    Chromosome getChromosome() {
        return chromosome;
    }

    @Override public String toString() {
        return chromosome.toString();
    }
}
