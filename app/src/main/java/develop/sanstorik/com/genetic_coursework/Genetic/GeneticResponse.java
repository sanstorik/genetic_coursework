package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.ArrayList;
import java.util.Collection;

public class GeneticResponse {
    private static GeneticResponse instance;

    private ArrayList<Population> generations;
    private ArrayList<Individual> bestIndividuals;

    private GeneticResponse(){}

    void initData(Collection<Population> generations, Collection<Individual> bestIndividuals){
        this.generations = new ArrayList<>(generations.size());
        this.generations.addAll(generations);

        this.bestIndividuals = new ArrayList<>(bestIndividuals.size());
        this.bestIndividuals.addAll(bestIndividuals);
    }

    public ArrayList<Population> getGenerations(){
        return generations;
    }

    public ArrayList<Individual> getBestIndividalsInEachGeneration(){
        return bestIndividuals;
    }

    public static GeneticResponse getInstance(){
        if(instance == null)
            instance = new GeneticResponse();
        return instance;
    }
}
