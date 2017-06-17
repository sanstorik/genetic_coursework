package develop.sanstorik.com.genetic_library.genetic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Population implements Iterable<Individual>, Parcelable{
    private ArrayList<Individual> individuals;
    private Random random = new Random(47);
    private Individual lastRandom;

    Population(){
        individuals = new ArrayList<>(5);
    }

    Population(Population population){
        individuals = new ArrayList<>(population.size() + 5);

        for(Individual ind : population.getIndividuals())
            this.individuals.add(new Individual(ind.getChromosome()));
    }

    Individual get(int index){
        return individuals.get(index);
    }

    void add(Individual ind){
        individuals.add(ind);
    }

    public int size(){
        return individuals.size();
    }

    public Individual getBestIndividual(){
        return Collections.max(individuals);
    }


    /*
    It compares random individual taken from previous random,
    so there will be never two same random individuals in n > 1
     */
    Individual getRandomIndividual(){
        Individual ind = individuals.get( random.nextInt(individuals.size()) );

        if(lastRandom != null && ind == lastRandom && individuals.size() > 1)
            while(ind == lastRandom)
                ind = individuals.get( random.nextInt(individuals.size()) );

        lastRandom = ind;

        return ind;
    }

    public List<Individual> getIndividuals(){
        return Collections.unmodifiableList(individuals);
    }

    @Override public Iterator<Individual> iterator() {
        return individuals.iterator();
    }


    /*
    Implementing parceble to pass it to
    another activity in bundle.
     */

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(individuals.size());
        dest.writeTypedList(individuals);
    }

    public static final Parcelable.Creator<Population> CREATOR =
            new Parcelable.Creator<Population>(){
                @Override public Population[] newArray(int size) {
                    return new Population[size];
                }

                @Override public Population createFromParcel(Parcel source) {
                    return new Population(source);
                }
            };


    private Population(Parcel in){
        individuals = new ArrayList<>(in.readInt());
        in.readTypedList(individuals, Individual.CREATOR);
    }
}
