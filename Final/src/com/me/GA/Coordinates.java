package com.me.GA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Coordinates {

	  public int[][] init(String filename,int cityNum) throws IOException { 
		  
	        // 读取数据  
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
	            // 读取一行数据，数据格式1 6734 1453  
	            strbuff = data.readLine();  
	            // 字符分割  
	            String[] strcol = strbuff.split(" ");  
	            x[i] = Integer.valueOf(strcol[1]);// x坐标  
	            y[i] = Integer.valueOf(strcol[2]);// y坐标  
	        }  
	        // 计算距离矩阵  
	        // ，针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628  
	        for (int i = 0; i < cityNum - 1; i++) {  
	            distance[i][i] = 0; // 对角线为0  
	            for (int j = i + 1; j < cityNum; j++) {  
	                double rij = Math  
	                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])  
	                                * (y[i] - y[j])) / 10.0);  
	                // 四舍五入，取整  
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
	        // 初始化种群  
	    return distance;
	    }  
	  

	
}
