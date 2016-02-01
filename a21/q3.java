import java.io.*;

class Graph {
    int vertice;
	int edge;
	int cost[][];
	boolean connect[][];

	public Graph(int numV, int numE) {
        this.vertice = numV;
		this.edge = numE;
		this.cost = new int[numV][numV];
		this.connect = new boolean[numV][numV];
	}

    public void addEdge(int v1, int v2, int weight) {
		this.cost[v1][v2] = weight;
        this.cost[v2][v1] = weight;
		this.connect[v1][v2] = true;
        this.connect[v2][v1] = true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.vertice).append(" ");
		sb.append(this.edge).append("\n");

		for (int i = 0; i < vertice; i++) {
            for (int j = 0; j < vertice; j++) {
                sb.append(this.cost[i][j]);
				if(j == vertice -1) {
					sb.append("\n");
				}
				else {
                    sb.append(" ");
				}
			}
		}

        return sb.toString();
	}
}

public class q3 { 
    public static int minDist(int numV, int dist[], boolean visited[]) {
        int minCost = Integer.MAX_VALUE;
		int pos = 0;

        for (int i = 0; i < numV; i++) {
			if (dist[i] < minCost && !visited[i]) {
                minCost = dist[i];
				pos = i;
			}
		}

		return pos;
	}    

	public static void main(String[] args) {
		String filename = "edges.txt";
		String line = null;
		int numV = 0;
		int numE = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            line = reader.readLine();
            String item[] = line.split(" ");
			numV = Integer.parseInt(item[0]);
			numE = Integer.parseInt(item[1]);

			Graph g = new Graph(numV, numE);

			while ((line = reader.readLine()) != null) {
                String field[] = line.split(" ");
				int v1 = Integer.parseInt(field[0]);
				int v2 = Integer.parseInt(field[1]);
				int weight = Integer.parseInt(field[2]);
				g.addEdge(v1 - 1, v2 - 1, weight);
			}

			//System.out.println(g);

			int prev[] = new int[numV];
			int dist[] = new int[numV];
			boolean visited[] = new boolean[numV];

			for (int i = 0; i < numV; i++) {
                prev[i] = -1;
				dist[i] = Integer.MAX_VALUE;
				visited[i] = false;
			}

			dist[0] = 0;

			for (int j = 0; j < numV; j++) {
                int w = minDist(numV, dist, visited);
				visited[w] = true;

				for (int m = 0; m < numV; m++) {
                    if (!visited[m] && g.connect[w][m]) {
						if (g.cost[w][m] < dist[m]) {
							dist[m] = g.cost[w][m];
							prev[m] = w;
						}
					}
				}
			}

			int sum = 0;
			for (int n = 1; n < numV; n++) {
                int v1 = n + 1;
				int v2 = prev[n] + 1;
				int weight = g.cost[n][prev[n]];
				sum += weight;

				//System.out.println(v1 + " - " + v2 + " " + weight);
			}

		    System.out.println(sum);
		}
		catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
		}
		catch (IOException e) {
            e.printStackTrace();
		}
    }
}
