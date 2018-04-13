package com.me.GA;


import java.io.IOException;

import java.util.Random;



public class TSP {  
  
 
  Coordinates co = new Coordinates();
  static int[][]distance;
  
  
  public Population generatePopulation(Population population,int cityNumber,int pathNumber) throws IOException {
	  
	  distance=co.init("C:\\Users\\Lucy Bai\\Desktop\\data.txt", 48);
//	  for(int i=0;i<10;i++) {
//    	  for(int j=0;j<10;j++) {
//    		  System.out.print(" "+distance[i][j]);
//    		  
//    	  }
//    	  System.out.println();
//      }
 
	  for(int i=0;i<pathNumber;i++) {
		  population.addIndividual(generateIndividual(cityNumber));
	  }
	  for(Individual x:population.getPopulation()) {
		  transRoute(x);
		 System.out.println(x.calculateDistance()); 
		  
//		  for(String s: x.getBinaryCity()) {
//		  System.out.print(s+",");}
//		  System.out.println();
//		  for(int m: x.getDecimalCity()) {
//			  System.out.print(m+",");
//		  }
//		  System.out.println();
//		  System.out.println("---------------");
	  }
	  return population;
  }
  
  private Individual  generateIndividual(int cityNumber) {
	  Individual indi = new Individual();
	  double d = Math.log(cityNumber)/Math.log(2);
	 int digits = (int) Math.ceil(d);
	
	 int y;
	 String binaryCity[]=new String[cityNumber];
	 for(int i=0;i<cityNumber;i++) {
		 String gene = new String();
		 for(int j =0;j<digits;j++) {
			 double x = Math.random();
			 if(x>=0.5) { y=1;}
			 else {y=0;}
			 gene+=String.valueOf(y);
		 }
		 if(transTo(cityNumber,gene)) {
			 boolean flag =true;
			 if(i==0) {
				 binaryCity[i]=gene;
			 }
			 
			 for(int t=0;t<i;t++) {
				 if((binaryCity[t].equals(gene))) {
					 flag=false;
					 i--;
					 break;
				 }
				 if(flag){
				    binaryCity[i]=gene;
				 }
			 }
			 
			}
		 
		 else {
			 i--;
		 }
	 }
	// System.out.println(binaryCity.length);
	 indi.setBinaryCity(binaryCity);
	 //System.out.println(indi.getBinaryCity().length);
	 return indi;
//	 for(String x:binaryCity) {
//	 System.out.println(x);};
  }
  
  
  
  private Boolean transTo(int cityNumber,String gene) {
	   int digit =Integer.parseInt(gene,2);
	   if(digit<cityNumber) {
		   return true;
	   }
	   else {
		   return false;
	   }
  }
  
  public static void transRoute(Individual in ) {
	  int []deximalCity = new int [in.getBinaryCity().length];
	  String[]binaryCity = in.getBinaryCity();
	  for(int i=0;i<in.getBinaryCity().length;i++) {
		  deximalCity[i] = Integer.parseInt(binaryCity[i],2);
		 // System.out.println("deci+ "+deximalCity[i]);
	  }
	  in.setDecimalCity(deximalCity);
  }
  
  
  public static void main(String[] args) throws IOException{
      TSP tsp = new TSP();
      GA ga = new GA();
      
    //  ga.generateIndividual(20);
      Population population = new Population();
      population=tsp.generatePopulation(population,48,50);
      System.out.println("Original Population:");
//      for(Individual x:population.getPopulation()) {
//    	  for(String s: x.getBinaryCity()) {
//    		  System.out.print(s+",");}
//    		  System.out.println();
//    		  for(int m: x.getDecimalCity()) {
//    			  System.out.print(m+",");
//    		  }
//    		  System.out.println();
//    		  System.out.println("---------------");
//      }
      
      for(int i=0;i<300;i++) {

    	population = ga.evolution(population,0.8f);
        
      }
      int g = ga.getBestGeneration();
      System.out.println("Best Generation"+ g);
      System.out.println("Best Distance"+ ga.getBestIndividual().getDistance());
      int[] bestRoute = ga.getBestIndividual().getDecimalCity();
      for(int i=0;i<bestRoute.length;i++) {
    	  System.out.print(bestRoute[i]+", ");
      }
      System.out.println();
        
        
        
        
//      Double sortedFit[] = ga.evolution(population);
//      System.out.println(sortedFit[0]);
 
  }
} 
