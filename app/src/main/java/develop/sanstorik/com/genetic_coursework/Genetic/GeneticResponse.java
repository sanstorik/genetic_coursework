package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.Collection;

class GeneticResponse {
    private Collection<Population> generations;
    private Collection<Individual> bestIndividuals;

    GeneticResponse(Collection<Population> generations, Collection<Individual> bestIndividuals){
        this.generations = generations;
        this.bestIndividuals = bestIndividuals;
    }

    public Population[] getGenerations(){
        return (Population[])generations.toArray();
    }

    public Individual[] getBestIndividalsInEachGeneration(){
        return (Individual[])bestIndividuals.toArray();
    }
}
