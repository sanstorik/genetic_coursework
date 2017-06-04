package develop.sanstorik.com.genetic_coursework.Genetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

public class GeneticResponse {
    private static GeneticResponse instance;

    private Deque<Population> generations;
    private Deque<Individual> bestIndividuals;

    private GeneticResponse(){}

    void initData(Deque<Population> generations, Deque<Individual> bestIndividuals){
        this.generations = generations;
        this.bestIndividuals = bestIndividuals;
    }

    public Deque<Population> getGenerations(){
        return generations;
    }

    public Deque<Individual> getBestIndividalsInEachGeneration(){
        return bestIndividuals;
    }

    public static GeneticResponse getInstance(){
        if(instance == null)
            instance = new GeneticResponse();
        return instance;
    }
}
