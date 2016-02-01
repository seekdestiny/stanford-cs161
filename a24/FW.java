import java.io.*;

public class FW {
    public static int min(int i1, int i2) {
        if (i1 < i2) {
			return i1;
		} else {
			return i2;
		}
	}

    public static void main(String[] args) {
		String filename = "g3.txt";
		String line = null;
		int numV = 0;
		int numE = 0;
		int[][] dist;
		int minDist = Integer.MAX_VALUE;

        try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			line = reader.readLine();
			String[] field = line.split(" ");
			numV = Integer.parseInt(field[0]);
			numE = Integer.parseInt(field[1]);

			dist = new int[numV+1][numV+1];

			int i,j = 0;

			for (i = 0; i <= numV; i++) {
				for (j = 0; j <= numV; j++) {
					if (i == j) {
                        dist[i][j] = 0;
					} else {
                        dist[i][j] = Integer.MAX_VALUE;
					}
				}
			}

			while ((line = reader.readLine()) != null) {
                String[] item = line.split(" ");
				int v1 = Integer.parseInt(item[0]);
			    int v2 = Integer.parseInt(item[1]);
				int w =  Integer.parseInt(item[2]);
				dist[v1][v2] = w;
			}

			for (int k = 1; k <= numV; k++) {
				for (i = 1; i <= numV; i++) {
					for (j = 1; j <= numV; j++ ) {

						if (dist[i][k] < Integer.MAX_VALUE && dist[k][j] < Integer.MAX_VALUE) {
						    dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
						}

						if (i == j) {
                            if (dist[i][j] < 0) {
								System.out.println("Negative Cycle detected!" + i + " " + j + " " + k);
								return;
							}
						}

						if (k == numV) {
							minDist = min(minDist, dist[i][j]);
						}
					}
				}
			}

			System.out.println(minDist);

		} catch(FileNotFoundException e) {
			System.err.println("Error:" + e.getMessage());

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
