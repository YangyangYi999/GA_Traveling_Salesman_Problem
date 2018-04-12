package com.me.GA;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;




public class GA {
	private static Random random;
	public Double[] fitness(Population population) {
		Double[] fitness = new Double[population.getPopulation().size()];
		for(int i=0;i<population.getPopulation().size();i++) {
			Individual in = population.getPopulation().get(i);
			fitness[i] = (double)1/in.getDistance();
			
		}
		selectIndividuals(fitness,population);
		rouletteSelect(fitness,population);
//	     for(int i=0;i<population.getPopulation().size();i++) {
//	    	 System.out.println(fitness[i]);
//	     }
		return  fitness;
	}
	
	public Individual[] selectIndividuals(Double[] fitness,Population population ) {
        int size = fitness.length;
        int selectNum = (int) Math.ceil(0.5*size);
        Double[] Dfit = new Double[size];
        for( int i=0; i<size; i++ )
            Dfit[i] = fitness[i];
        HashMap<Double,Individual> inMap = new HashMap<Double,Individual>( size );
        for( int i=0; i<size; i++ )
            inMap.put( Dfit[i], population.getPopulation().get(i) );

        //Sort fitness[];
        MergeSort ms = new MergeSort(Dfit);
        ms.sort( Dfit, 0, Dfit.length-1 );
        Individual[] goodIndv= new Individual[selectNum];
        int index = size-1;
        int i=0;
        do{
            goodIndv[i++] = inMap.get( Dfit[index] );
            index--;
            selectNum--;
            
        }while(selectNum>0);
        
        for(Individual in : goodIndv) {
        	for(int j:in.getDecimalCity())
        	{System.out.print(j+", ");}
        	System.out.println();
        	System.out.println("good individual "+in.getDistance());
        	
        }
        return goodIndv;
    }
	

	public static Individual[]  rouletteSelect(Double[] fitness,Population pop)
	    {
		    Individual[] selectedIndvs= new Individual[ (int) (Math.floor(0.5*(pop.getPopulation().size())))];
		    int k;  
		    int i;
		    double r;
	        double sumFitness = 0;// 适应度总和  
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
	        
	        for(k=0;k<selectedIndvs.length;k++) {
	       
	        	   r=Math.random();
	        	  for( i=0; i<pop.getPopulation().size();i++)
	        	  {
	        		  if(r<=accumulatePro[i])
	        		  {
	        			  break;
	        		  }
	        	  }
	        	  
	        	  selectedIndvs[k]=pop.getPopulation().get(i);
	        }
	    	
	        for(Individual in : selectedIndvs) {
	        	for(int j:in.getDecimalCity())
	        	{System.out.print(j+", ");}
	        	System.out.println();
	        	System.out.println("random individual "+in.getDistance());
	        	
	        }
	        
			return selectedIndvs;  

  
        /* 
         * for(k=0;k<scale;k++) { System.out.println(fitness[k]+" "+Pi[k]); } 
         */  
    }
}

