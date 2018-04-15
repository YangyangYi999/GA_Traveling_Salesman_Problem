package com.me.GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * @author Yangyang Yi & Lu Bai
 */ 
public class GA {
	public static Logger log = Logger.getLogger(TSP_Graph.class.getName());
	private static Random random = new Random();
	private int bestGeneration = 1;
	private Individual bestIndividual = new Individual();
	private int currentGeneration = 0;

	public Population evolution(Population population, Float pm) {
		//Count the current generation
		currentGeneration++;
		Double[] fitness = new Double[population.getPopulation().size()];
		//Calculate the fitness
		fitness = fitness(population);
		Individual[] a = selectBest(fitness, population);
	  
		log.info("CurrentGeneration: "+currentGeneration);
		log.info("Best Individual:");
		for(String i:a[0].getBinaryCity()) {
			System.out.print(i+",");
		}
		System.out.println();
		for (int i : a[0].getDecimalCity()) {
			System.out.print(i+",");
		}
		System.out.println();
		log.info("CurrentDistance:"+a[0].getDistance());	
		System.out.println("====================================================");
		bestIndividual(a[0],currentGeneration);
		Population newPopulation = newPopulation(a);
		
		int childNumber = population.getPopulation().size()-a.length;
		
		for(int i=0; i<childNumber;i++) {
			Individual parents1 = rouletteSelect(fitness, population);
			Individual parents2 = rouletteSelect(fitness, population);
			Individual child = crossover(parents1,parents2);
			child.calculateDistance(child,TSP.distance);
			
			Float r =random.nextFloat();
			//IF r is less than mutation rate then do mutation
			if(r<pm) {
			child=mutation(child);
			child.calculateDistance(child,TSP.distance);
					}
			newPopulation.addIndividual(child);
			
		}
//     log.info("new Population");			
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
	//Crossover
	public Individual crossover(Individual p1,Individual p2) {
		    Individual child = new Individual();

	        // Find the 2 positions of parent 1 to crows over
	        int startPos = (int) (Math.random() *p1.getBinaryCity().length);
	        int endPos = (int) (Math.random() * p1.getBinaryCity().length);
	        String p1BinaryCity[] = p1.getBinaryCity();
	        String p2BinaryCity[] = p2.getBinaryCity();
	        String[]binaryCity = new String[p1BinaryCity.length];
	        // Loop parent 1 to get the gene 
	        for (int i = 0; i < p1.getBinaryCity().length; i++) {
	        // If start position is less than end position , get the gene between these two position
	            if (startPos < endPos && i > startPos && i < endPos) {
	                binaryCity[i] = p1BinaryCity[i];
	             }
	         // If start position is lager than end position, choose the gene outside these two position
	            else if (startPos > endPos) {
	                if (!(i < startPos && i > endPos)) {
	                	 binaryCity[i] = p1BinaryCity[i];
	                	// System.out.println("p1: "+binaryCity[i]);
	                }
	            }
	        }

	        // Loop partent2 to set other gene to child
	        for (int i = 0; i < p2.getBinaryCity().length; i++) {
	            // Check if child already has this gene
	            if (!Arrays.asList(binaryCity).contains(p2BinaryCity[i])) {
	                for (int j = 0; j < p2.getBinaryCity().length; j++) {
	                    //Find the blank position to set p2 gene
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
   //Mutation
	public Individual mutation(Individual in) {
	//System.out.println("number mutate " + k);
	//	Individual in = population.getPopulation().get(k);
	// log.info("mutation start")
	//  random two positions to swap
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
		return in;
	}

	public Double[] fitness(Population population) {
		Double[] fitness = new Double[population.getPopulation().size()];
		for (int i = 0; i < population.getPopulation().size(); i++) {
			Individual in = population.getPopulation().get(i);
			fitness[i] = (double) 1 / in.getDistance();
		}
		// for(int i=0;i<population.getPopulation().size();i++) {
		// System.out.println(fitness[i]);
		// }
		return fitness;
	}
	
	//Choose the best individual of the whole generation
	public void bestIndividual(Individual in , int generation) {
		
		if(generation==1) {
		bestIndividual.setBinaryCity(in.getBinaryCity());
		bestIndividual.setDecimalCity(in.getDecimalCity());
		bestIndividual.setDistance(in.getDistance());
		}
		else{
			//Exchange the individual if the more better indiviudal accurs
			if(in.getDistance()<bestIndividual.getDistance()) {
				bestIndividual.setBinaryCity(in.getBinaryCity());
				bestIndividual.setDecimalCity(in.getDecimalCity());
				bestIndividual.setDistance(in.getDistance());
				bestGeneration = generation;

			}
		}
		
	}
	//Select best individuals
	public Individual[] selectBest(Double[] fitness, Population population) {
		int size = fitness.length;
		//Set the number of good individuals wanted to select
		int selectNum = (int) Math.ceil(0.1 * size);
		Double[] Dfit = new Double[size];
		for (int i = 0; i < size; i++)
			Dfit[i] = fitness[i];
		//use map to set the individuals with there fitness
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
    //RouletteSelect the individual as the parent
	public static Individual rouletteSelect(Double[] fitness, Population pop) {
		Individual rouletteIndividual = new Individual();
		int k;
		int i;
		double r;
		double sumFitness = 0;
		double[] tempf = new double[pop.getPopulation().size()];
		float[] accumulatePro = new float[pop.getPopulation().size()];
		//Calculate the sum fitness of this population
		for (k = 0; k < pop.getPopulation().size(); k++) {
			tempf[k] = fitness[k];
			sumFitness += tempf[k];
		}
        
		accumulatePro[0] = (float) (tempf[0] / sumFitness);
		for (k = 1; k < pop.getPopulation().size(); k++) {
			accumulatePro[k] = (float) (tempf[k] / sumFitness + accumulatePro[k - 1]);
		}
			r = (float) (random.nextInt(65535) % 1000 / 1000.0);
			//Check the random float with the position of this individual accumulate pro
			for (i = 0; i < pop.getPopulation().size(); i++) {
				//If r less, get the position of that individual
				if (r <= accumulatePro[i]) {
					break;
				}
			}
            rouletteIndividual = pop.getPopulation().get(i);

		return rouletteIndividual;
	}
   // Set good individuals to new population
	public Population newPopulation(Individual[] selectedIndvs) {
		Population newPopulation = new Population();
		for (Individual in : selectedIndvs) {
			newPopulation.addIndividual(in);
		}
		return newPopulation;
	}
	
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

}
