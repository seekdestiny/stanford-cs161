import java.util.*;
import java.io.*;

class Edge {
	int source;
	int destination;
	int weight;

	public Edge(int source, int destination, int weight) {
        this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public int getDest() {
        return destination;
	}

	public int getWeight() {
		return weight;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();

		sb.append(source).append(" ");
		sb.append(destination).append(" ");
		sb.append(weight);

		return sb.toString();
	}
}

class Graph {
	int numV;
    List<List<Edge>> adj;

	public Graph(int numV) {
		this.numV = numV;
		this.adj = new ArrayList<List<Edge>>(); 
	}

	public void add(int vertex, List<Edge> edges) {
        this.adj.add(vertex, edges);
	}

	public List<Edge> getEdges(int vertex) {
        return adj.get(vertex);
	}
}

public class Dijkstra {
    int[] dist;
    int[] prev;
	boolean[] visited;
	int numV;
	Graph map;

	public int minDist(int numV, int dist[], boolean visited[]) {
        int minCost= 1000000;
		int pos = 0;

		for (int i = 0; i < numV; i++) {
			if (dist[i] < minCost && !visited[i]) {
                minCost = dist[i];
				pos = i;
			}
		}

		return pos;
	}

    public Dijkstra(int numV, Graph map) {
	   this.numV = numV;
       this.dist = new int[numV];
	   this.prev = new int[numV];
	   this.visited = new boolean[numV];
	   this.map = map;

	   for (int i = 0; i < numV; i++) {
           dist[i] = 1000000;
		   prev[i] = 1000000;
		   visited[i] = false;
	   }

	   dist[0] = 0;
	   prev[0] = 0;
    }

	public void shortestPath() {
		for (int j = 0; j < numV; j++) {
            int w = minDist(numV, dist, visited);
            visited[w] = true;

            List<Edge> neighbor = map.getEdges(w);
			for (Edge e: neighbor) {
				int dest = e.getDest();
				int weight = e.getWeight();

				if (!visited[dest]) {
					if (dist[w] + weight < dist[dest]) {
						dist[dest] = dist[w] + weight;
						prev[dest] = w;
					}
				}
			}                
		}
	}

	public int target(int destination) {
        return dist[destination - 1];
	}

    public static void main(String[] args) {
        String filename = "map.txt";
		String line = "";
		Graph g = new Graph(200);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			while ((line = reader.readLine()) != null) {
                String[] item = line.split("\\s+");

				int source = Integer.parseInt(item[0]);
				List<Edge> currentList = new ArrayList<Edge>();

				for (int i = 1; i < item.length; i++) {
					String[] info = item[i].split(",");
					int dest = Integer.parseInt(info[0]);
					int weight = Integer.parseInt(info[1]);

                    Edge temp = new Edge(source - 1, dest - 1, weight);

					currentList.add(temp);
				}

				g.add(source - 1, currentList);
			}

			Dijkstra solution = new Dijkstra(200, g);
			solution.shortestPath();

			System.out.println(solution.target(7));
			System.out.println(solution.target(37));
			System.out.println(solution.target(59));
			System.out.println(solution.target(82));
			System.out.println(solution.target(99));
			System.out.println(solution.target(115));
			System.out.println(solution.target(133));
			System.out.println(solution.target(165));
			System.out.println(solution.target(188));
			System.out.println(solution.target(197));

            /*for (int i = 0; i <= 0; i++) {
                List<Edge> test = new ArrayList<Edge>();
				test = g.getEdges(i);
                for (Edge e: test) {
					System.out.println(e.toString());
				}
			}*/

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
