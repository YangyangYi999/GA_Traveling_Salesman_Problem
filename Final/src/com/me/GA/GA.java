package com.me.GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class GA {
	private static Random random = new Random();
	private int bestGeneration = 1;
	private Individual bestIndividual = new Individual();
	private int currentGeneration = 0;
	
	public int getBestGeneration() {
		return bestGeneration;
	}

	public void setBestGeneration(int bestGeneration) {
		this.bestGeneration = bestGeneration;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}

	public Population evolution(Population population, Float pm) {
		currentGeneration++;
		Double[] fitness = new Double[population.getPopulation().size()];
		fitness = fitness(population, fitness);
		Individual[] a = selectBest(fitness, population);
		System.out.println("currentGeneration: "+currentGeneration);
		System.out.println("best Individual:");
		for (int i : a[0].getDecimalCity()) {
			System.out.print(i+",");
		}
		System.out.println();
		System.out.println("currentDistance:"+a[0].getDistance());	
		bestIndividual(a[0],currentGeneration);
		Population newPopulation = newPopulation(a);
		
		int childNumber = population.getPopulation().size()-a.length;
		
		for(int i=0; i<childNumber;i++) {
			Individual parents1 = rouletteSelect(fitness, population);
			Individual parents2 = rouletteSelect(fitness, population);
			Individual child = crossover(parents1,parents2);
			child.calculateDistance(child,TSP.distance);
			
			Float r =random.nextFloat();
			if(r<pm) {
			child=mutation(child);
			child.calculateDistance(child,TSP.distance);
					}
			newPopulation.addIndividual(child);
			
		}
		
//		System.out.println("New Population:");
//		for (Individual x : newPopulation.getPopulation()) {
//			for (String s : x.getBinaryCity()) {
//				System.out.print(s + ",");
//			}
//			System.out.println();
//			for (int m : x.getDecimalCity()) {
//				System.out.print(m + ",");
//			}
//			System.out.println();
//			System.out.println("---------------");
//		}
		
		return newPopulation;
	}
	
	public Individual crossover(Individual p1,Individual p2) {
		    Individual child = new Individual();

	        // Get start and end sub tour positions for parent1's tour
	        int startPos = (int) (Math.random() *p1.getBinaryCity().length);
	        int endPos = (int) (Math.random() * p2.getBinaryCity().length);
	        String p1BinaryCity[] = p1.getBinaryCity();
	        String p2BinaryCity[] = p2.getBinaryCity();
	        String[]binaryCity = new String[p1BinaryCity.length];
	        // Loop and add the sub tour from parent1 to our child
	        for (int i = 0; i < p1.getBinaryCity().length; i++) {
	            // If our start position is less than the end position
	            if (startPos < endPos && i > startPos && i < endPos) {
	            
	                binaryCity[i] = p1BinaryCity[i];
	             //   System.out.println("p1: "+binaryCity[i]);
	            } // If our start position is larger
	            else if (startPos > endPos) {
	                if (!(i < startPos && i > endPos)) {
	                	
	                	 binaryCity[i] = p1BinaryCity[i];
	                	// System.out.println("p1: "+binaryCity[i]);
	                }
	            }
	        }

	        // Loop through parent2's city tour
	        for (int i = 0; i < p2.getBinaryCity().length; i++) {
	            // If child doesn't have the city add it !child.containsCity(parent2.getCity(i))
	            if (!Arrays.asList(binaryCity).contains(p2BinaryCity[i])) {
	                // Loop to find a spare position in the child's tour
	                for (int j = 0; j < p2.getBinaryCity().length; j++) {
	                    // Spare position found, add city
	                    if (binaryCity[j]==null) {
	                    	binaryCity[j] = p2BinaryCity[i];
	                    	//System.out.println("p2: "+binaryCity[i]);
	                        break;
	                    }
	                }
	            }
	        }
	        
	        child.setBinaryCity(binaryCity);
	        
	        TSP.transRoute(child);
	        
	        return child;
	}

	public Individual mutation(Individual in) {
		//System.out.println("number mutate " + k);
	//	Individual in = population.getPopulation().get(k);
		int r1 = random.nextInt(in.getBinaryCity().length);
		int r2 = random.nextInt(in.getBinaryCity().length);
		while (r1 == r2) {
			r2 = random.nextInt(in.getBinaryCity().length);
		}
		String[] binaryCity = in.getBinaryCity();
		String e1 = binaryCity[r1];
		binaryCity[r1] = binaryCity[r2];
		binaryCity[r2] = e1;        
		in.setBinaryCity(binaryCity);
		TSP.transRoute(in);
//		in.calculateDistance(in,TSP.distance);
		return in;
	}

	public Double[] fitness(Population population, Double[] fitness) {

		for (int i = 0; i < population.getPopulation().size(); i++) {
			Individual in = population.getPopulation().get(i);
			fitness[i] = (double) 1 / in.getDistance();
		}
		// for(int i=0;i<population.getPopulation().size();i++) {
		// System.out.println(fitness[i]);
		// }
		return fitness;
	}
	public void bestIndividual(Individual in , int generation) {
		
		if(generation==1) {
		bestIndividual.setBinaryCity(in.getBinaryCity());
		bestIndividual.setDecimalCity(in.getDecimalCity());
		bestIndividual.setDistance(in.getDistance());
		}
		else{
			if(in.getDistance()<bestIndividual.getDistance()) {
				bestIndividual.setBinaryCity(in.getBinaryCity());
				bestIndividual.setDecimalCity(in.getDecimalCity());
				bestIndividual.setDistance(in.getDistance());
				bestGeneration = generation;

			}
		}
		
	}
	public Individual[] selectBest(Double[] fitness, Population population) {
		int size = fitness.length;
		int selectNum = (int) Math.floor(0.1 * size);
		Double[] Dfit = new Double[size];
		for (int i = 0; i < size; i++)
			Dfit[i] = fitness[i];
		HashMap<Double, Individual> inMap = new HashMap<Double, Individual>(size);
		for (int i = 0; i < size; i++)
			inMap.put(Dfit[i], population.getPopulation().get(i));

		// Sort fitness[];
		MergeSort ms = new MergeSort(Dfit);
		ms.sort(Dfit, 0, Dfit.length - 1);
		Individual[] goodIndv = new Individual[selectNum];
		int index = size - 1;
		int i = 0;
		do {
			goodIndv[i++] = inMap.get(Dfit[index]);
			index--;
			selectNum--;

		} while (selectNum > 0);
//
//		for (Individual in : goodIndv) {
//			for (int j : in.getDecimalCity()) {
//				System.out.print(j + ", ");
//			}
//			System.out.println();
//			System.out.println("good individual " + in.getDistance());
//
//		}
		return goodIndv;
	}

	public static Individual rouletteSelect(Double[] fitness, Population pop) {
		//Individual[] selectedIndvs = new Individual[(int) (Math.floor(0.5 * (pop.getPopulation().size())))];
		Individual rouletteIndividual = new Individual();
		int k;
		int i;
		double r;
		double sumFitness = 0;
		double[] tempf = new double[pop.getPopulation().size()];
		float[] accumulatePro = new float[pop.getPopulation().size()];

		for (k = 0; k < pop.getPopulation().size(); k++) {
			tempf[k] = fitness[k];
			sumFitness += tempf[k];
		}

		accumulatePro[0] = (float) (tempf[0] / sumFitness);
		for (k = 1; k < pop.getPopulation().size(); k++) {
			accumulatePro[k] = (float) (tempf[k] / sumFitness + accumulatePro[k - 1]);
		}
			r = Math.random();
			for (i = 0; i < pop.getPopulation().size(); i++) {
				if (r <= accumulatePro[i]) {
					break;
				}
			}
            rouletteIndividual = pop.getPopulation().get(i);

		return rouletteIndividual;
	}

	public Population newPopulation(Individual[] selectedIndvs) {
		Population newPopulation = new Population();
		for (Individual in : selectedIndvs) {
			newPopulation.addIndividual(in);
		}
		return newPopulation;
	}
}
