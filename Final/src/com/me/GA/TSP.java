package com.me.GA;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * @author Yangyang Yi & Lu Bai
 */

public class TSP {
    public static Logger log = Logger.getLogger(TSP.class.getName());
	Coordinates co = new Coordinates();
	static int[][] distance;
    //Initializing population
	public Population generatePopulation(Population population, int cityNumber, int pathNumber) throws IOException {

		File f = new File("src/data.txt");
		//Initializing distance [][]
		distance = co.init(f.getPath(), 48);
		for (int i = 0; i < pathNumber; i++) {
			population.addIndividual(generateIndividual(cityNumber));
		}
		for (Individual x : population.getPopulation()) {
			transRoute(x);
			x.calculateDistance(x, distance);
			// for(String s: x.getBinaryCity()) {
			// System.out.print(s+",");}
			// System.out.println();
			// for(int m: x.getDecimalCity()) {
			// System.out.print(m+",");
			// }
			// System.out.println();
			// System.out.println("---------------");
		}
		return population;
	}
    
	//Generate binary array to set individual
	private Individual generateIndividual(int cityNumber) {
		Individual indi = new Individual();
		//Calculate the digits of the binary
		double d = Math.log(cityNumber) / Math.log(2);
		int digits = (int) Math.ceil(d);

		int y;
		String binaryCity[] = new String[cityNumber];
		for (int i = 0; i < cityNumber; i++) {
			String gene = new String();
			for (int j = 0; j < digits; j++) {
				double x = Math.random();
				if (x >= 0.5) {
					y = 1;
				} else {
					y = 0;
				}
				gene += String.valueOf(y);
			}
			if (transTo(cityNumber, gene)) {
				boolean flag = true;
				if (i == 0) {
					binaryCity[i] = gene;
				}

				for (int t = 0; t < i; t++) {
				//Check duplicates
					if ((binaryCity[t].equals(gene))) {
						flag = false;
						i--;
						break;
					}
					if (flag) {
						binaryCity[i] = gene;
					}
				}

			}

			else {
				i--;
			}
		}
		
		indi.setBinaryCity(binaryCity);
		return indi;
	
	}

	private Boolean transTo(int cityNumber, String gene) {
		int digit = Integer.parseInt(gene, 2);
		if (digit < cityNumber) {
			return true;
		} else {
			return false;
		}
	}
   //Transfer binaryRote to decimalRote
	public static int[] transRoute(Individual in) {
		int[] deximalCity = new int[in.getBinaryCity().length];
		String[] binaryCity = in.getBinaryCity();
		for (int i = 0; i < in.getBinaryCity().length; i++) {
			deximalCity[i] = Integer.parseInt(binaryCity[i], 2);
			// System.out.println("deci+ "+deximalCity[i]);
		}
		in.setDecimalCity(deximalCity);
		return deximalCity;
	}

	public static void main(String[] args) throws IOException {
		TSP tsp = new TSP();
		GA ga = new GA();
		Population population = new Population();
		//Generate the original population including set the city number and the route number
		population = tsp.generatePopulation(population, 48, 500);
		//Do loop until the generation times is 1000
		for (int i = 0; i < 1000; i++) {
			population = ga.evolution(population, 0.015f);
		}
	
		int g = ga.getBestGeneration();
		log.info("Best Generation: " + g);

		int[] bestRoute = ga.getBestIndividual().getDecimalCity();
		String[] binaryRoute = ga.getBestIndividual().getBinaryCity();
		System.out.println("Best Individual : ");
		for (int i = 0; i < binaryRoute.length; i++) {
			System.out.print(binaryRoute[i] + ", ");
		}
		System.out.println();
		for (int i = 0; i < bestRoute.length; i++) {
			System.out.print(bestRoute[i] + ", ");
		}
		System.out.println();
		log.info("Best Distance :" + ga.getBestIndividual().getDistance());

	
	}
}
