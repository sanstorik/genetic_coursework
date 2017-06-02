package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.Random;

class Individual {
    private static final float MIN_X = 0;
    private static final float MAX_X = 4;
    static final int GENES_SIZE = 16;

    static private class Chromosome{
        private static final double STEP;
        Random random;
        boolean[] genes;

        static{
            STEP = ( Math.abs(MIN_X) + Math.abs(MAX_X) ) / Math.pow(2, GENES_SIZE);
        }

        private Chromosome(boolean[] genes){
            this.genes = genes;
            init();
        }

        private Chromosome(Chromosome chromosome){
            this.genes = chromosome.getGenes();
            init();
        }

        private Chromosome(){
            this.genes = new boolean[GENES_SIZE];
            init();
        }

        //swap two bits
        Chromosome mutate(){
            boolean[] tempGenes = genes.clone();

            int firstIndex = getRandomBitIndex();
            int secondIndex = getRandomBitIndex();
            boolean firstBit = tempGenes[firstIndex];
            boolean secondBit = tempGenes[secondIndex];

            tempGenes[firstIndex] = secondBit;
            tempGenes[secondIndex] = firstBit;

            return new Chromosome(getGenes().clone());
        }

        /*
        uniform crossover
        if (rand(0,1) > final rand[0,1])
        --- bit from first /else/ bit from second
        */
        private Chromosome crossover(Chromosome secondParent){
            final double trashHold = Math.random();
            boolean[] childGenes = new boolean[GENES_SIZE];

            for(int i=0; i < genes.length; i++){
                if(Math.random() > trashHold)
                    childGenes[i] = this.genes[i];
                else
                    childGenes[i] = secondParent.genes[i];
            }

            return new Chromosome(childGenes);
        }

        boolean[] getGenes() {
            return genes.clone();
        }

        double getGenesValue(){
            StringBuilder bits = new StringBuilder();

            for (boolean gene : genes)
                if (gene)
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

            for (boolean gene : genes)
                if (gene)
                    bits.append("1");
                else
                    bits.append("0");

            return bits.toString() + " size = " + genes.length;
        }
    }

    private Chromosome chromosome;

    Individual(boolean[] genes){
        this.chromosome = new Chromosome(genes.clone());
    }

    double getFunctionValue(){
        double x = chromosome.getGenesValue();

        return x * (x - 2) * (x - 2.75f) * Math.exp((x/10)) * Math.cos((x/10)) *(2 - Math.pow(3, x-2));
    }

    Chromosome getChromosome() {
        return chromosome;
    }

    @Override public String toString() {
        return " ind with genes = " + chromosome.toString();
    }
}
