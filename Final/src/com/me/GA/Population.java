package com.me.GA;

import java.util.ArrayList;

public class Population {
	private ArrayList <Individual> population = new ArrayList<Individual>();
    
	public ArrayList<Individual> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}
	
	public void addIndividual(Individual in) {
		population.add(in);
	}
	
	
}
