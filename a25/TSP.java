import java.io.*;
import java.util.*;

class City {
	double x;
    double y;

	public City(double x, double y) {
        this.x = x;
		this.y = y;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();

	    sb.append(this.x).append(" ").append(this.y);

		return sb.toString();
	}

	public double xCoor() {
        return this.x;
	}

	public double yCoor() {
        return this.y;
	}
}

class Map {
    int numCity;
	double distance[][];

	public Map(int numCity) {
        this.numCity = numCity;
		this.distance = new double[numCity][numCity];
	}

	public void addDistance(City source, City destination, int s, int d) {
        double sxCoor = source.xCoor();
		double syCoor = source.yCoor();
		double dxCoor = destination.xCoor();
		double dyCoor = destination.yCoor();

		double xDiff = sxCoor - dxCoor;
		double yDiff = syCoor - dyCoor;

		double xSquare = xDiff * xDiff;
        double ySquare = yDiff * yDiff;

		this.distance[s][d] = Math.sqrt(xSquare + ySquare);
	}

	public double distanceGet(int source, int destination) {
		return distance[source][destination];
	}
}

public class TSP {

	double[][] g;
	int[][] p;

	public TSP(int idx1, int idx2, Map cityMap) {
		this.g = new double[idx1][idx2];
		this.p = new int[idx1][idx2];

		int m, n;
		for (m = 0; m < idx1; m++) {
            for (n = 0; n < idx2; n++) {
                g[m][n] = -1;
			    p[m][n] = -1;
			}
		}
		
		for (m = 0; m < idx1; m++) {
            g[m][0] = cityMap.distanceGet(m, 0);
		}
	}

    public double solution(int start, int set, int idx2, int numCity, Map cityMap) {
        int masked, mask, i; 
	    double temp;
		double result = Double.MAX_VALUE;

		if(g[start][set] != -1) {
			return g[start][set];
		}
        
        for (i = 0; i < numCity; i++) {
            mask = (idx2 - 1) - (1 << i);
			masked = set&mask;

			if (masked != set) {
                temp = cityMap.distanceGet(start, i) + this.solution(i, masked, idx2, numCity, cityMap);
                if (temp < result) {
                    result = temp;
					p[start][set] = i;
				}
			}
		}

        g[start][set] = result;

		return result;
	}

	public void getpath(int start, int set, int idx2) {
        if (p[start][set] == -1) return;
		int x = p[start][set];
		int mask = (idx2-1) - (1<<x);
		int masked = set&mask;
		System.out.println(x + " ");
		this.getpath(x, masked, idx2);
	}

    public static void main(String[] args) {
        String line = null;
		String filename = "city.txt";
		int numCity = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
            numCity = Integer.parseInt(reader.readLine());
            City[] cityCoor = new City[numCity];

			int j = 0;
			while((line = reader.readLine()) != null) {
                String item[] = line.split(" ");
			    double x = Double.parseDouble(item[0]);
				double y = Double.parseDouble(item[1]);
				cityCoor[j] = new City(x, y);
                j++;
			}  

			Map cityMap = new Map(numCity);

            int m, n;
			for (m = 0; m < numCity; m++) {
                for (n = 0; n < numCity; n++) {
                    cityMap.addDistance(cityCoor[m], cityCoor[n], m, n);
				}
			}

			int idx1 = numCity;
			int idx2 = (int) Math.pow(2, numCity);

			TSP dpTSP = new TSP(idx1, idx2, cityMap);

            double result = dpTSP.solution(0, idx2-2, idx2, numCity, cityMap);

			//System.out.println(cityMap.distanceGet(0,1));
			System.out.println("Tour cost: " + result);
			System.out.println("0");
			dpTSP.getpath(0, idx2-2, idx2);
			System.out.println("0");
		}
		catch(FileNotFoundException e) {
            System.err.println("Error:" + e.getMessage());
        }
		catch(IOException e) {
            e.printStackTrace();
		}
    }
}
