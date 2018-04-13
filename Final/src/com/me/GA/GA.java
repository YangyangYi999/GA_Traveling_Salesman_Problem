package com.me.GA;

import java.util.ArrayList;
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
			System.out.print(i);
		}
		System.out.println();
		System.out.println(a[0].getDistance());
		
		bestIndividual(a[0],currentGeneration);
		Individual[] b = rouletteSelect(fitness, population);
		Population newPopulation = newPopulation(a, b);
		System.out.println("New Population:");
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
		// 交叉方法

		for (int k = 0; k < population.getPopulation().size() - 1; k = k + 2) {
			// Float r = random.nextFloat();// /产生概率
			// System.out.println("交叉率..." + r);
			// if (r < Pc) {
			// // System.out.println(k + "与" + k + 1 + "进行交叉...");
			// //OXCross(k, k + 1);// 进行交叉
			// OXCross1(k, k + 1);
			// } else {
			Float r = random.nextFloat();// /产生概率
			System.out.println("变异率1..." + r);
			// 变异
			if (r < pm) {
				System.out.println(k + "变异...");
				newPopulation = mutation(newPopulation, k);
			}
			r = random.nextFloat();// /产生概率
			// System.out.println("变异率2..." + r);
			// 变异
			if (r < pm) {
				System.out.println(k + 1 + "变异...");
				newPopulation = mutation(newPopulation, k + 1);
			}
		}
		System.out.println("Mutate Population:");
		for (Individual x : newPopulation.getPopulation()) {
			for (String s : x.getBinaryCity()) {
				System.out.print(s + ",");
			}
			System.out.println();
			for (int m : x.getDecimalCity()) {
				System.out.print(m + ",");
			}
			System.out.println();
			System.out.println("---------------");
		}
		return newPopulation;
	}

	public Population mutation(Population population, int k) {
		System.out.println("number mutate " + k);
		Individual in = population.getPopulation().get(k);
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
		in.calculateDistance();

		return population;
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
		int selectNum = (int) Math.ceil(0.5 * size);
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

	public static Individual[] rouletteSelect(Double[] fitness, Population pop) {
		Individual[] selectedIndvs = new Individual[(int) (Math.floor(0.5 * (pop.getPopulation().size())))];
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

		for (k = 0; k < selectedIndvs.length; k++) {

			r = Math.random();
			for (i = 0; i < pop.getPopulation().size(); i++) {
				if (r <= accumulatePro[i]) {
					break;
				}
			}

			selectedIndvs[k] = pop.getPopulation().get(i);
		}

//		for (Individual in : selectedIndvs) {
//			for (int j : in.getDecimalCity()) {
//				System.out.print(j + ", ");
//			}
//			System.out.println();
//			System.out.println("random individual " + in.getDistance());
//
//		}

		return selectedIndvs;

		/*
		 * for(k=0;k<scale;k++) { System.out.println(fitness[k]+" "+Pi[k]); }
		 */
	}

	public Population newPopulation(Individual[] selectedIndvs, Individual[] goodIndv) {
		Population newPopulation = new Population();
		for (Individual in : selectedIndvs) {
			newPopulation.addIndividual(in);
		}
		for (Individual in : goodIndv) {
			newPopulation.addIndividual(in);
		}
		return newPopulation;
	}
}
