package com.me.GA;

public class Individual {
	private String binaryCity[];
	private int decimalCity[];
    private int distance;
    
    public int calculateDistance() {
    	int totalDistance=0;
    	for(int d=0;d<decimalCity.length-1;d++) {
    		totalDistance+=TSP.distance[decimalCity[d]][decimalCity[d+1]];    		
    	}
    	totalDistance+=TSP.distance[decimalCity[decimalCity.length-1]][decimalCity[0]];
    	this.setDistance(totalDistance);
      return totalDistance;
    	
    }
    

	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}





	public String[] getBinaryCity() {
		return binaryCity;
	}

	public void setBinaryCity(String[] binaryCity) {
		this.binaryCity = binaryCity;
	}

	public int[] getDecimalCity() {
		return decimalCity;
	}

	public void setDecimalCity(int[] decimalCity) {
		this.decimalCity = decimalCity;
	}

}
