package com.me.GA;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.util.logging.Logger;
/**
 * @author Yangyang Yi & Lu Bai
 */
@SuppressWarnings("serial")
public class TSP_Graph extends JPanel {
	public static Logger log = Logger.getLogger(TSP_Graph.class.getName());
	int cityNum = 48;
	File f = new File("src/data.txt");
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		
		try {			
			TSP tsp = new TSP();
			GA ga = new GA();
			Population population = new Population();
			//Generate the original population including set the city number and the route number
			population = tsp.generatePopulation(population, 48, 500);
			//Do loop until the generation times is 1000
			for (int i = 0; i < 1000; i++) {
				population = ga.evolution(population, 0.015f);
			}
			log.info("Best Generation !!!!!!!!!");
			int bg = ga.getBestGeneration();
			System.out.println("Best Generation: " + bg);
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
			System.out.println("Best Distance :" + ga.getBestIndividual().getDistance());

		    g.drawString("BestGeneration:"+bg, 50, 510);  
			g.drawString("BestDestance:"+ga.getBestIndividual().getDistance(), 50, 540);
			StringBuilder sb=new StringBuilder();
			for (int i = 0; i < cityNum; i++) {
				sb.append(bestRoute[i]+"-");
	        }
			sb.delete(sb.length()-1, sb.length());
			g.drawString("Best Route:"+sb.toString(), 50, 570);
			
			g.setColor(Color.BLUE); // set color
			// read data
			int[] x;
			int[] y;

			String row;
			BufferedReader data = new BufferedReader(new InputStreamReader(
					new FileInputStream(f.getPath())));

			x = new int[cityNum];
			y = new int[cityNum];
			for (int i = 0; i < cityNum; i++) {
				// read the row 
				row = data.readLine();
				// split by space
				String[] strcol = row.split(" ");
				x[i] = Integer.valueOf(strcol[1]);// get the x axis
				y[i] = Integer.valueOf(strcol[2]);// y axis
				g.fillOval(x[i] / 10, y[i] / 10, 5, 5);
				g.drawString(String.valueOf(i), x[i] / 10, y[i] / 10);
			}
			data.close();
			
			g.setColor(Color.DARK_GRAY); // set color
			for(int j=0;j<cityNum-1;j++)
			{
				g.drawLine(x[bestRoute[j]]/ 10, y[bestRoute[j]]/ 10, x[bestRoute[j+1]]/ 10, y[bestRoute[j+1]]/ 10);
			}
			
			g.setColor(Color.red); // set begin and final city color
			g.fillOval(x[bestRoute[0]]/ 10, y[bestRoute[0]]/ 10, 6, 6);
			g.fillOval(x[bestRoute[cityNum-1]]/ 10, y[bestRoute[cityNum-1]]/ 10, 6, 6);
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		f.setTitle("Trveling salesman problem");
		f.getContentPane().add(new TSP_Graph());
		f.setSize(950, 800);
	
		f.setBackground(Color.lightGray);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}