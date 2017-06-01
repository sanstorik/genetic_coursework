package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.ArrayList;
import java.util.List;

class Population {
    private ArrayList<Individual> individuals;

    Population(Population population){
        for(Individual ind : population.getIndividuals())
            this.individuals.add(ind);
    }

    ArrayList<Individual> getIndividuals(){
        return individuals;
    }
}
