package com.me.GA;

import java.util.Calendar;
import java.util.Random;



public class GA {  
  private Population population = new Population();
  private Random random;
 
  
  private void generatePopulation(int cityNumber,int pathNumber) {
	  for(int i=0;i<pathNumber;i++) {
		  population.addIndividual(generateIndividual(cityNumber));
	  }
	  for(Individual x:population.getPopulation()) {
		  for(String s: x.getBinaryCity()) {
		  System.out.println(s);}
		  System.out.println("---------------");
	  }
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
	   if(digit<=cityNumber) {
		   return true;
	   }
	   else {
		   return false;
	   }
  }
  
  public static void main(String[] args) {
      GA ga = new GA();
//      ga.generateIndividual(20);
      ga.generatePopulation(20,10);
      
  }
} 
