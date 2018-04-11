package com.me.GA;


<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> branch 'master' of https://github.com/LucyBai/AlgorithmFinal.git
import java.util.Random;



public class GA {  
  private Population population = new Population();
 
  
  private void generatePopulation(int cityNumber,int pathNumber) {
	  for(int i=0;i<pathNumber;i++) {
		  population.addIndividual(generateIndividual(cityNumber));
	  }
	  for(Individual x:population.getPopulation()) {
		  transRoute(x);
		  for(String s: x.getBinaryCity()) {
		  System.out.print(s+",");}
		  System.out.println();
		  for(int m: x.getDecimalCity()) {
			  System.out.print(m+",");
		  }
		  System.out.println();
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
	   if(digit<cityNumber) {
		   return true;
	   }
	   else {
		   return false;
	   }
  }
  
  private void transRoute(Individual in ) {
<<<<<<< HEAD
	  
	  int []deximalCity = new int [in.getBinaryCity().length];
	  String[]binaryCity = in.getBinaryCity();
	  for(int i=0;i<in.getBinaryCity().length;i++) {
		  deximalCity[i] = Integer.parseInt(binaryCity[i],2);
		 // System.out.println("deci+ "+deximalCity[i]);
	  }
	  in.setDecimalCity(deximalCity);
  }
  
  
  public static void main(String[] args) throws IOException {
=======
	  int []deximalCity = new int [in.getBinaryCity().length];
	  String[]binaryCity = in.getBinaryCity();
	  for(int i=0;i<in.getBinaryCity().length;i++) {
		  deximalCity[i] = Integer.parseInt(binaryCity[i],2);
		  System.out.println("deci+ "+deximalCity[i]);
	  }
  }
  
  
  public static void main(String[] args) {
>>>>>>> branch 'master' of https://github.com/LucyBai/AlgorithmFinal.git
      GA ga = new GA();
//      ga.generateIndividual(20);
 //     ga.generatePopulation(20,10);
      Coordinates co = new Coordinates();
      int [][] xy;
      xy=co.init("C:\\Users\\Lucy Bai\\Desktop\\data.txt", 48);
      for(int i=0;i<10;i++) {
    	  for(int j=0;j<10;j++) {
    		  System.out.print(" "+xy[i][j]);
    		  
    	  }
    	  System.out.println();
      }
      
      
  }
} 
