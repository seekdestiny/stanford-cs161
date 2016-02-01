import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

class Edge implements Comparable<Edge> {
	int src;
	int dest;
	int weight;

	public Edge(int src, int dest, int weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
		sb.append(src).append(" to ").append(dest).append("Weight: ").append(weight);
		return sb.toString();
	}

	@Override
	public int compareTo(Edge that) {
		return this.weight - that.weight;
	}
}

public class KCluster {
    private int numVertices;
	private int numEdges;
	private int maxClusterDistance;

	private List<Edge> EdgeList;

    public KCluster(int numV, int numE) {
        this.numVertices = numV;
		this.numEdges = numE;
		this.maxClusterDistance = 0;

		this.EdgeList = new ArrayList<Edge>(numEdges);
	}

	public void addEdge(int src, int dest, int weight) {
		this.EdgeList.add(new Edge(src, dest, weight));
	}

	public int getMaxSpacing(int clusterCount) {
	    Collections.sort(EdgeList);
        UnionFind uf = new UnionFind(numVertices);

		for (int i = 0; i < numEdges; i++) {
            Edge min = EdgeList.get(i);
            int src = min.src;
			int dest = min.dest;
			int weight = min.weight;		

		    if (!uf.isConnected(src, dest)) {
			    if (uf.getCount() == clusterCount) {
				    maxClusterDistance = weight;
					return maxClusterDistance;
			    }

				uf.Union(src, dest);
			}
        }

		return -1;
	}

	public static void main(String[] args) {
        String filename = "q1.txt";
        int numV = 0;
		int numE = 0;

		try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			numV = Integer.parseInt(line);
            numE = numV * (numV - 1) / 2;
            KCluster solution = new KCluster(numV, numE);

			while ((line = reader.readLine()) != null) {
			    String[] items = new String[3];
				items = line.split(" ");
				int src = Integer.parseInt(items[0]) - 1;
				int dest = Integer.parseInt(items[1]) - 1;
				int weight = Integer.parseInt(items[2]);
				solution.addEdge(src, dest, weight);
			}        

            System.out.println(solution.getMaxSpacing(4));

		} catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
