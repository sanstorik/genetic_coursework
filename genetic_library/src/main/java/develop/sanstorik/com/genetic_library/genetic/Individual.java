package develop.sanstorik.com.genetic_library.genetic;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public final class Individual implements Comparable<Individual>, Parcelable, Serializable{
    private static final double MIN_X = 0;
    private static final double MAX_X = 4;
    static final int GENES_SIZE = 32;

    static private class Chromosome implements Parcelable{
        private static final double STEP;
        private Random random;
        private boolean[] genes;
        private String genesString;
        private double genesValue;

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
        private Chromosome mutate(){
            boolean[] tempGenes = genes.clone();

            int firstIndex = getRandomBitIndex();
            int secondIndex = getRandomBitIndex();
            boolean firstBit = tempGenes[firstIndex];
            boolean secondBit = tempGenes[secondIndex];

            tempGenes[firstIndex] = secondBit;
            tempGenes[secondIndex] = firstBit;

            return new Chromosome(tempGenes);
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
            return genesValue;
        }

        private double calculateGenesValue(){
            double bitsValue = Long.parseLong(genesString, 2);
            return MIN_X + (bitsValue * STEP);
        }

        private void init(){
            random = new Random(47);

            genesString = genesToString();
            genesValue = calculateGenesValue();
        }

        private int getRandomBitIndex(){
            return random.nextInt(GENES_SIZE);
        }


        private String genesToString(){
            StringBuilder bits = new StringBuilder();
            for (boolean gene : genes)
                if (gene)
                    bits.append("1");
                else
                    bits.append("0");

            return bits.toString();
        }

        @Override public String toString() {
            return genesToString();
        }


        /*
          Implementing parceble to pass it to
          another activity in bundle.
        */

        @Override public int describeContents() {
            return 0;
        }

        @Override public void writeToParcel(Parcel dest, int flags) {
            dest.writeBooleanArray(genes);
            dest.writeDouble(genesValue);
        }

        public static final Parcelable.Creator<Chromosome> CREATOR =
                new Parcelable.Creator<Chromosome>(){
                    @Override public Chromosome[] newArray(int size) {
                        return new Chromosome[size];
                    }

                    @Override public Chromosome createFromParcel(Parcel source) {
                        return new Chromosome(source);
                    }
                };

        private Chromosome(Parcel in){
            genes = new boolean[GENES_SIZE];
            in.readBooleanArray(genes);
            genesValue = in.readDouble();
        }
    }





    private transient Chromosome chromosome;

    Individual(boolean[] genes){
        this.chromosome = new Chromosome(genes.clone());
    }

    Individual(Chromosome chromosome){
        this.chromosome = new Chromosome(chromosome);
    }


    public double getFunctionValue(){
        double x = chromosome.getGenesValue();

       return x * (x - 2) * (x - 2.75f) * Math.exp((x/10)) * Math.cos((x/10)) *(2 - Math.pow(3, x-2));
       //return Math.pow(x, 2);

       // return x * Math.sin(x+5) * Math.cos(x-6) * Math.sin(x-7) * Math.cos(x-8) * Math.sin((x/3));
    }

    public double getGenesValue(){
        return chromosome.getGenesValue();
    }

    public String getBits(){
        return chromosome.toString();
    }

    Chromosome getChromosome() {
        return chromosome;
    }

    void mutate(){
        chromosome.mutate();
    }

    Individual crossover(Individual ind){
        return new Individual(chromosome.crossover(ind.getChromosome()));
    }

    @Override public int compareTo(Individual second) {
        return Double.valueOf(getFunctionValue())
                .compareTo(second.getFunctionValue());
    }

    @Override public String toString() {
        return " ind with genes = " + chromosome.toString() + " y = " + getFunctionValue();
    }

    /*
    Implementing parceble to pass it to
    another activity in bundle.
     */


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(chromosome, flags);
    }

    public static final Parcelable.Creator<Individual> CREATOR =
            new Parcelable.Creator<Individual>(){
                @Override public Individual[] newArray(int size) {
                    return new Individual[size];
                }

                @Override public Individual createFromParcel(Parcel source) {
                    return new Individual(source);
                }
            };

    private Individual(Parcel in){
        chromosome = in.readParcelable(Chromosome.class.getClassLoader());
    }



    /*Serialize*/

    private void writeObject(ObjectOutputStream stream) throws IOException{
        stream.defaultWriteObject();

        stream.writeObject(chromosome.genes);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
        stream.defaultReadObject();

        chromosome = new Chromosome((boolean[])stream.readObject());
    }
}
