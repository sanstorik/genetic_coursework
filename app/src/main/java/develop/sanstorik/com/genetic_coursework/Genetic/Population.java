package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class Population implements Iterable<Individual>{
    private ArrayList<Individual> individuals;

    Population(){}

    Population(Population population){
        for(Individual ind : population.getIndividuals())
            this.individuals.add(ind);
    }

    ArrayList<Individual> getIndividuals(){
        return (ArrayList<Individual>)Collections.unmodifiableCollection(individuals);
    }

    void add(Individual ind){
        individuals.add(ind);
    }

    @Override public Iterator<Individual> iterator() {
        return individuals.iterator();
    }
}
