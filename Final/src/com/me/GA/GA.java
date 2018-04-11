package com.me.GA;

import java.util.Calendar;
import java.util.Random;



public class GA {  
  private Population population;
  private Random random;
  private Individual indi;
  
  private void generatePopulation(int cityNumber,int pathNumber) {
	  for(int i=0;i<pathNumber;i++) {
		  
	  }
  }
  
  private void generateIndividual(int cityNumber) {
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
			 
			 System.out.println(binaryCity[i]);}
		 
		 else {
			 i--;
		 }
	 }
	   
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
      ga.generateIndividual(20);
      
  }
} 
