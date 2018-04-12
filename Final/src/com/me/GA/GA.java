package com.me.GA;


import java.util.HashMap;


public class GA {
	
	public Double[] fitness(Population population) {
		Double[] fitness = new Double[population.getPopulation().size()];
		for(int i=0;i<population.getPopulation().size();i++) {
			Individual in = population.getPopulation().get(i);
			fitness[i] = (double)1/in.getDistance();
			
		}
		selectIndividuals(fitness,population);
//	     for(int i=0;i<population.getPopulation().size();i++) {
//	    	 System.out.println(fitness[i]);
//	     }
		return  fitness;
	}
	
	public Individual[] selectIndividuals(Double[] fitness,Population population ) {
        int size = fitness.length;
        int selectNum = (int) Math.ceil(0.25*size);
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
}
