import java.io.*;
import java.util.*;

class Graph {
	int numV;
    Map<Integer, List<Integer>> adj;

	public Graph(int numV) {
		this.numV = numV;
		this.adj = new HashMap<Integer, List<Integer>>(); 
	}

	public void add(int source, List<Integer> edges) {
        this.adj.put(source, edges);
	}

	public List<Integer> getEdges(int vertex) {
        return adj.get(vertex);
	}

	public void contractEdge(int nodeA, int nodeB) {
        deleteEdge(nodeA, nodeB); // delete the edge nodeB in nodeA  
        findEnd(nodeA, nodeB); // change nodeB to a in node change  
        addList(nodeA, nodeB); // add nodeB in the last of nodeA  
        this.adj.remove(nodeB);// remove nodeB
	}

	public void deleteEdge(int nodeA, int nodeB) {
		while (adj.get(nodeA).contains(nodeB)) {
			int index = adj.get(nodeA).indexOf(nodeB);
            adj.get(nodeA).remove(index);
		}

		while (adj.get(nodeB).contains(nodeA)) {
			int index = adj.get(nodeB).indexOf(nodeA);
            adj.get(nodeB).remove(index);
		}
	}

	public void findEnd(int nodeA, int nodeB) {
		boolean record[] = new boolean[numV];
		for (int end: adj.get(nodeB)) {
			if (record[end - 1] == false) {
                while (adj.get(end).contains(nodeB)) {
                    adj.get(end).set(adj.get(end).indexOf(nodeB), nodeA);
		        }
                record[end - 1] = true;
			}
		}
	}

	public void addList(int nodeA, int nodeB) {
	    for (int end: adj.get(nodeB)) {
            adj.get(nodeA).add(end);
		}
	}
}

public class contraction {
    private int numV;
	private Graph adj;
	private int numCut;
	private int nodeA, nodeB;  
	private List<Integer> nodeSet;

	public contraction(int numV) {
		this.numV = numV;
		this.adj = new Graph(200);
		this.numCut = Integer.MAX_VALUE;
		this.nodeA = 0;
		this.nodeB = 0;
		this.nodeSet = new ArrayList<Integer>();
	}

	public void input() {
        String filename = "adj.txt";
	    String line = "";

        try {
		    BufferedReader reader = new BufferedReader(new FileReader(filename));

            while((line = reader.readLine()) != null) {
				String[] item = line.split("\\s+");
				int source = Integer.parseInt(item[0]);
				nodeSet.add(source);

				List<Integer> edges = new ArrayList<Integer>();

				for (int i = 1; i < item.length; i++) {
					edges.add(Integer.parseInt(item[i]));
				}

				adj.add(source, edges);
		    }
	    } catch (FileNotFoundException e) {
		    System.err.println("Error:" + e.getMessage());
	    } catch (IOException e) {
            e.printStackTrace();
	    }
	}

	public void contract() {
        while (nodeSet.size() > 2) {  
            randEdge();  
            adj.contractEdge(nodeA, nodeB); 
			if (numCut > adj.getEdges(nodeA).size()) {
				numCut = adj.getEdges(nodeA).size();
			}
        }
	}

	public int cutNum() {
        return numCut;
	}

	/*public void randEdge() {
		Random rand = new Random();
		int indexOfNodeA;  
        int indexOfNodeB;
        indexOfNodeA = rand.nextInt(nodeSet.size());
        indexOfNodeB = rand.nextInt(nodeSet.size());

        nodeA = nodeSet.get(indexOfNodeA);
        nodeB = nodeSet.get(indexOfNodeB);

		while (!adj.getEdges(nodeA).contains(nodeB)) {
			//System.out.println("no edge");
            indexOfNodeB = rand.nextInt(nodeSet.size());
            nodeB = nodeSet.get(indexOfNodeB);
		}

		nodeSet.remove(indexOfNodeB);

		//System.out.println("contract" + " " + nodeA + "-" + nodeB);
	}*/

	public void randEdge() {
		Random rand = new Random();
		int indexOfNodeA;
		int indexOfNodeB;

		indexOfNodeA = rand.nextInt(nodeSet.size());
		nodeA = nodeSet.get(indexOfNodeA);

		int randomSeed = adj.getEdges(nodeA).size();
		indexOfNodeB = rand.nextInt(randomSeed);

		nodeB = adj.getEdges(nodeA).get(indexOfNodeB);

		nodeSet.remove(nodeSet.indexOf(nodeB));
	}

	public static void main(String[] args) {
        int minCut = Integer.MAX_VALUE;

		for (int count = 0; count < 50; count++) {
		    contraction solution = new contraction(200);
		    solution.input();
		    solution.contract();
            if (minCut > solution.cutNum()) {
				minCut = solution.cutNum();
			}

			System.out.println("///" + solution.cutNum() + " - " + minCut + "///" + count); // test
		}

        System.out.println(minCut);
	}
}
