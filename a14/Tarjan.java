import java.io.*;
import java.util.*;

class Vertex {
	private int index;

	public Vertex(int index) {
        this.index = index;
	}

	public int getIndex() {
        return index;
	}

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
		sb.append(index);
		return sb.toString();
	}

    public int hashCode() {
        final int PRIME = 17;
		int result = 1;
		result = PRIME * result + getIndex();
		return result;
	}

	public boolean equals(Object o) {
        if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		Vertex e = (Vertex) o;
		return (this.getIndex() == e.getIndex());
	}
}

class Graph<Vertex> {
    private Map<Vertex, List<Vertex>> adj;
	private Set<Vertex> vertice;

	public Graph() {
        this.adj = new HashMap<Vertex, List<Vertex>>();
		this.vertice = new HashSet<Vertex>();
	}

	public void addVertex(Vertex v) {
        if(!vertice.contains(v)) {
            vertice.add(v);
			adj.put(v, new ArrayList<Vertex>());
		}
	}

    public void addEdge(Vertex s, Vertex d) {
		//System.out.println("Edge: " + s + "->" + d + " added!");
		adj.get(s).add(d);
	}

	public List<Vertex> getEdge(Vertex v) {
        if(!adj.containsKey(v)) {
			return null;
		}

        return adj.get(v);
	}

	public Iterator<Vertex> getAllVertice() {
		return vertice.iterator();
	}
}

public class Tarjan {

	private int[] belongs;
	private int[] low;
	private int[] disc;
	private boolean[] inStack;
	private int time;
	private int scc;
	private Stack<Integer> stack;
	private PriorityQueue<Integer> fiveL;

	public Tarjan() {        
		this.belongs = new int[875714];
		this.low = new int[875714];
		this.disc = new int[875714];
		this.inStack = new boolean[875714];
	    this.time = 0;
		this.scc = 0;
		this.stack = new Stack<Integer>();
		this.fiveL = new PriorityQueue<Integer>();
	}

	public void changeBelongs(int v, int newbelongs) {
		belongs[v -1] = newbelongs;
	}

	public void changeLow(int v, int newlow) {
		low[v - 1] = newlow;
	}


	public void changeDisc(int v, int newdisc) {
		disc[v - 1] = newdisc;
	}

	public void changeinStack(int v, boolean newinStack) {
		inStack[v - 1] = newinStack;
	}

	public void dfs(Graph<Vertex> g, Vertex u) {
	   time++;
       changeLow(u.getIndex(), time);
	   changeDisc(u.getIndex(), time);
	   changeinStack(u.getIndex(), true);
	   stack.push(u.getIndex());

       List<Vertex> edgelist = g.getEdge(u);
	   //System.out.println(u + " " + disc.get(u) + " " +low.get(u) + " " +belongs.get(u) + " " +inStack.get(u));
       /*for (Vertex v: edgelist) {
           //System.out.println("->" + v + " " + disc.get(v) + " " +low.get(v) + " " +belongs.get(v) + " " +inStack.get(v));
	   }*/

	   //System.out.println("\n");
	   for (Vertex v: edgelist) {
           if (disc[v.getIndex() - 1] == -1) {
			   dfs(g, v);
			   if (low[v.getIndex() - 1] < low[u.getIndex() - 1]) {
                   changeLow(u.getIndex(), low[v.getIndex() - 1]);
			   }
		   }
		   else if (inStack[v.getIndex() - 1]) {
               if (disc[v.getIndex() - 1] < low[u.getIndex() - 1]) {
                   changeLow(u.getIndex(), disc[v.getIndex() - 1]);
			   }
		   }
	   }

	   //System.out.println("HI" + u + " " + disc.get(u) + " " +low.get(u) + " " +belongs.get(u) + " " +inStack.get(u));
	   int ulow = low[u.getIndex() - 1];
	   int udisc = disc[u.getIndex() - 1];
	   if (ulow == udisc) {
		   int count = 0;
		   //System.out.println(u + "hi");
		   scc++;
           while(stack.peek() != u.getIndex()) {
			   changeBelongs(stack.peek(), scc);
               changeinStack(stack.peek(), false);
			   stack.pop();
			   count++;
		   }
		   changeBelongs(stack.peek(), scc);
           changeinStack(stack.peek(), false);
		   stack.pop();
		   count++;

		   if (fiveL.size() < 5) {
			   fiveL.add(count);
		   } else {
			   if (count > fiveL.peek()) {
				   fiveL.add(count);
				   fiveL.poll();
			   }
		   }
	   }
	}

    /*public boolean check() {
		for (Vertex k : belongs.keySet()) {
			//System.out.println(k + " " + disc.get(k) + " " + low.get(k) + " " + belongs.get(k));
            int idx = -k.getIndex();
			Vertex neg = new Vertex(idx);
            //System.out.println(neg + " " + disc.get(neg) + " " + low.get(neg) + " " + belongs.get(neg));

			int kbelongs = belongs.get(k);
			int negbelongs = belongs.get(neg);
			if (kbelongs == negbelongs) {
				return false;
			}
		}

		return true;
	}*/

	public void printFive() {
		for (int size: fiveL) {
			System.out.println(size);
		}
	}

	public void SCC(Graph<Vertex> g) {
		Iterator<Vertex> all = g.getAllVertice();

		while (all.hasNext()) {
            Vertex i = all.next();
			if (low[i.getIndex() - 1] == -1) {
                dfs(g, i);
			}
		}
	}

    public static void main(String[] args) {
        String filename = "direct.txt";
		String line = null;
		Tarjan solution = new Tarjan();
		
        try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			Graph<Vertex> directG = new Graph<Vertex>();

			while((line = reader.readLine()) != null) {
				String item[] = line.split(" ");
				int idx1 = Integer.parseInt(item[0]);
				int idx2 = Integer.parseInt(item[1]);
				if (idx1 != idx2) {
				    Vertex s = new Vertex(idx1);
				    Vertex d = new Vertex(idx2);

				    directG.addVertex(s);
				    directG.addVertex(d);

				    directG.addEdge(s, d);

				    solution.changeBelongs(s.getIndex(), -1);
				    solution.changeBelongs(d.getIndex(), -1);
				    solution.changeLow(s.getIndex(), -1);
				    solution.changeLow(d.getIndex(), -1);
				    solution.changeDisc(s.getIndex(), -1);
				    solution.changeDisc(d.getIndex(), -1);
				    solution.changeinStack(s.getIndex(), false);
				    solution.changeinStack(d.getIndex(), false);
				}
			}

			solution.SCC(directG);

			solution.printFive();
		}
		catch (FileNotFoundException e) {
			System.err.println("Error:" + e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
