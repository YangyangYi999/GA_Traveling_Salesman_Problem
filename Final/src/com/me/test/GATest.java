package com.me.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.me.GA.Coordinates;
import com.me.GA.GA;
import com.me.GA.Individual;
import com.me.GA.Population;
import com.me.GA.TSP;



class GATest {
	File f = new File("src/data.txt");

    
	    @Test
	    public void testCityDistance() throws Exception {
	    	    Coordinates cood = new Coordinates();
	    	    int [][]distance = cood.init(f.getPath(), 3);
	    	    
	        Assert.assertEquals(distance[0][1],distance[1][0]);
	        Assert.assertEquals(distance[1][2],distance[2][1]);
	        Assert.assertEquals(distance[0][2],distance[2][0]);
	    }
	    
	    @Test
	    public void testPathDistance() throws Exception {
	    	    Coordinates cood = new Coordinates();
	    	    int [][]distance = cood.init(f.getPath(), 3);
	    	    
	    	    Individual in = new Individual();
	    	    String binaryCity[] = new String[3];
	    	    for(int i = 0;i<3;i++) {
	    	    		binaryCity[i] = Integer.toBinaryString(i);
//	    	    		System.out.println(binaryCity[i]);
	    	    }
	    	    in.setBinaryCity(binaryCity);
	    	    TSP.transRoute(in);
	    	    int caldis=cood.calculateDistance(in,distance);
	    	    int dis = distance[0][1]+distance[1][2]+distance[0][2];
	    	    
	        Assert.assertEquals(caldis,dis);
	    }
	    @Test
	    public void testTransferCity()throws Exception{
	    	String []a= {"0011","0110","0111","1000","0100","0101","0001","1001","0010","1010"};
	        Individual in = new Individual();
	        in.setBinaryCity(a);
	    	int[]b= {3,6,7,8,4,5,1,9,2,10};
	    	int[]d=TSP.transRoute(in);
	    	for(int i =0;i<in.getDecimalCity().length;i++) {
	    	Assert.assertEquals(b[i], d[i]);
	    	}
	    }
	    @Test
	    public void testMutation() throws Exception {
	    	  Coordinates cood = new Coordinates();
	    	  int [][]distance = cood.init(f.getPath(), 20);
	    	  Individual in = new Individual();
	    	  String []a= {"0011","0110","0111","1000","0100","0101","0001","1001","0010","1010"};
	    	  in.setBinaryCity(a);
	    	  GA ga =new GA();
	    	  
	    	  TSP.transRoute(in);
	    	 // cood.calculateDistance(in,distance);
	    	  
	    	  
	    	  Individual inn=ga.mutation(in);
	    	  TSP.transRoute(inn);
	    	 // cood.calculateDistance(inn,distance);
	    	  Assert.assertFalse(in.getDistance()==inn.getDistance());
	    	  
	    	  
	    
	    }
	    
}
