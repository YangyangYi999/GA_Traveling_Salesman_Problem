package com.me.test;


import java.io.File;


import com.me.GA.Coordinates;
import com.me.GA.GA;
import com.me.GA.Individual;
import com.me.GA.Population;
import com.me.GA.TSP;
import org.junit.Assert;
import org.junit.Test;

public class GATest {
	File f = new File("src/data.txt");

	@Test
	public void testCityDistance() throws Exception {
		Coordinates cood = new Coordinates();
		int[][] distance = cood.init(f.getPath(), 3);

		Assert.assertEquals(distance[0][1], distance[1][0]);
		Assert.assertEquals(distance[1][2], distance[2][1]);
		Assert.assertEquals(distance[0][2], distance[2][0]);
	}

	@Test
	public void testPathDistance() throws Exception {
		Coordinates cood = new Coordinates();
		int[][] distance = cood.init(f.getPath(), 3);

		Individual in = new Individual();
		String binaryCity[] = new String[3];
		for (int i = 0; i < 3; i++) {
			binaryCity[i] = Integer.toBinaryString(i);
		}
		in.setBinaryCity(binaryCity);
		TSP.transRoute(in);
		int caldis = in.calculateDistance(in, distance);
		int dis = distance[0][1] + distance[1][2] + distance[0][2];

		Assert.assertEquals(caldis, dis);
	}

	@Test
	public void testTransferCity() throws Exception {
		String[] a = { "0011", "0110", "0111", "1000", "0100", "0101", "0001", "1001", "0010", "1010" };
		Individual in = new Individual();
		in.setBinaryCity(a);
		int[] b = { 3, 6, 7, 8, 4, 5, 1, 9, 2, 10 };
		int[] d = TSP.transRoute(in);
		for (int i = 0; i < in.getDecimalCity().length; i++) {
			Assert.assertEquals(b[i], d[i]);
		}
	}

	@Test
	public void testMutation() throws Exception {
		Coordinates cood = new Coordinates();
		int[][] distance = cood.init(f.getPath(), 20);
		Individual in = new Individual();
		String[] a = { "0011", "0110", "0111", "1000", "0100", "0101", "0001", "1001", "0010", "1010" };
		in.setBinaryCity(a);
		GA ga = new GA();

		TSP.transRoute(in);
		in.calculateDistance(in, distance);

		Individual inn = new Individual();
		inn.setBinaryCity(in.getBinaryCity());
		inn.setDecimalCity(in.getDecimalCity());
		inn.setDistance(in.getDistance());

		ga.mutation(in);
		in.calculateDistance(in, distance);
		Assert.assertFalse(in.getDistance() == inn.getDistance());

	}

	@Test
	public void testCrossOver() throws Exception {
		Coordinates cood = new Coordinates();
		int[][] distance = cood.init(f.getPath(), 20);
		Individual p1 = new Individual();
		Individual p2 = new Individual();
		String[] a = { "0011", "0110", "0111", "1000", "0100", "0101", "0001", "1001", "0010", "1010" };
		String[] b = { "0110", "0111", "0101", "0001", "0011", "1000", "1001", "1010", "0100", "0010" };
		p1.setBinaryCity(a);
		p2.setBinaryCity(b);
		GA ga = new GA();
		TSP.transRoute(p1);
		TSP.transRoute(p2);
		p1.calculateDistance(p1, distance);
		p2.calculateDistance(p2, distance);
		Individual child = new Individual();
		child = ga.crossover(p1, p2);
		Assert.assertFalse(p1.getDistance() == child.getDistance());
		Assert.assertFalse(p2.getDistance() == child.getDistance());
	}

	@Test
	public void testGoodIndividuals() throws Exception {
		Coordinates cood = new Coordinates();
		int[][] distance = cood.init(f.getPath(), 20);
		String[] a = { "0011", "0110", "0111", "1000", "0100", "0101", "0001", "1001", "0010", "1010" };
		String[] b = { "0110", "0111", "0101", "0001", "0011", "1000", "1001", "1010", "0100", "0010" };
		String[] c = { "0101", "0001", "0011", "0110", "0111", "1000", "1001", "1010", "0100", "0010" };
		String[] d = { "1001", "0010", "1010", "0011", "0110", "1000", "0100", "0101", "0001", "0111" };
		String[] e = { "0011", "0110", "0111", "1000", "0100", "0101", "0001", "1001", "0010", "1010" };
		Individual aa = new Individual();
		Individual bb = new Individual();
		Individual cc = new Individual();
		Individual dd = new Individual();
		Individual ee = new Individual();
		aa.setBinaryCity(a);
		TSP.transRoute(aa);
		aa.calculateDistance(aa, distance);
		bb.setBinaryCity(b);
		TSP.transRoute(bb);
		bb.calculateDistance(bb, distance);
		cc.setBinaryCity(c);
		TSP.transRoute(cc);
		cc.calculateDistance(cc, distance);
		dd.setBinaryCity(d);
		TSP.transRoute(dd);
		dd.calculateDistance(dd, distance);
		ee.setBinaryCity(e);
		TSP.transRoute(ee);
		ee.calculateDistance(ee, distance);
		Population p = new Population();
		p.addIndividual(aa);
		p.addIndividual(bb);
		p.addIndividual(cc);
		p.addIndividual(dd);
		p.addIndividual(ee);
		GA ga = new GA();
		Double[] f = ga.fitness(p);
		Individual[] best = ga.selectBest(f, p);
		Individual bestIndi = best[0];
		String[] binaryCity = bestIndi.getBinaryCity();
		for (int i = 0; i < c.length; i++) {
			Assert.assertEquals(c[i], binaryCity[i]);
		}

	}

}
