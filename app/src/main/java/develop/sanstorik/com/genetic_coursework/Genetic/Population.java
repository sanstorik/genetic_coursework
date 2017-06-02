package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

class Population implements Iterable<Individual>{
    private ArrayList<Individual> individuals;
    Random random = new Random(47);

    Population(){
        individuals = new ArrayList<>(20);
    }

    Population(Population population){
        individuals = new ArrayList<>(20);
        for(Individual ind : population.getIndividuals())
            this.individuals.add(new Individual(ind.getChromosome()));
    }

    Individual get(int index){
        return individuals.get(index);
    }

    void add(Individual ind){
        individuals.add(ind);
    }

    Individual getRandomIndividual(){
        return individuals.get( random.nextInt(individuals.size()) );
    }

    private ArrayList<Individual> getIndividuals(){
        return (ArrayList<Individual>)Collections.unmodifiableCollection(individuals);
    }

    @Override public Iterator<Individual> iterator() {
        return individuals.iterator();
    }
}
