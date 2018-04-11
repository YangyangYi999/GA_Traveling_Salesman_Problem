package com.me.GA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Coordinates {

	  public int[][] init(String filename,int cityNum) throws IOException { 
		  
	        
		    int distance[][];
	        int[] x;  
	        int[] y;  
	        String strbuff;  
	        BufferedReader data = new BufferedReader(new InputStreamReader(  
	                new FileInputStream(filename)));  
	        distance = new int[cityNum][cityNum];  
	        x = new int[cityNum];  
	        y = new int[cityNum];  
	        for (int i = 0; i < cityNum; i++) {  
	            strbuff = data.readLine();  
	            String[] strcol = strbuff.split(" ");  
	            x[i] = Integer.valueOf(strcol[1]);
	            y[i] = Integer.valueOf(strcol[2]);
	        }  
	        
	        for (int i = 0; i < cityNum - 1; i++) {  
	            distance[i][i] = 0; 
	            for (int j = i + 1; j < cityNum; j++) {  
	                double rij = Math  
	                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])  
	                                * (y[i] - y[j])) / 10.0);  
	             
	                int tij = (int) Math.round(rij);  
	                if (tij < rij) {  
	                    distance[i][j] = tij + 1;  
	                    distance[j][i] = distance[i][j];  
	                } else {  
	                    distance[i][j] = tij;  
	                    distance[j][i] = distance[i][j];  
	                }  
	            }  
	        }  
	        distance[cityNum - 1][cityNum - 1] = 0;  
	  
//	        bestLength = Integer.MAX_VALUE;  
//	        bestTour = new int[cityNum + 1];  
//	        bestT = 0;  
//	        t = 0;  
//	  
//	        newPopulation = new int[scale][cityNum];  
//	        oldPopulation = new int[scale][cityNum];  
//	        fitness = new int[scale];  
//	        Pi = new float[scale];  
//	  
//	        random = new Random(System.currentTimeMillis());  
	        /* 
	         * for(int i=0;i<cityNum;i++) { for(int j=0;j<cityNum;j++) { 
	         * System.out.print(distance[i][j]+","); } System.out.println(); } 
	         */  
	     
	    return distance;
	    }  
	  

	
}
