package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

class Population implements Iterable<Individual>{
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

    private int size(){
        return individuals.size();
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

    Collection<Individual> getIndividuals(){
        return Collections.unmodifiableCollection(individuals);
    }

    @Override public Iterator<Individual> iterator() {
        return individuals.iterator();
    }
}
