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

public class twoSAT {

	private Map<Vertex, Integer> belongs;
	private Map<Vertex, Integer> low;
	private Map<Vertex, Integer> disc;
	private Map<Vertex, Boolean> inStack;
	private int time;
	private int scc;
	private Stack<Vertex> stack;

	public twoSAT() {        
		this.belongs = new HashMap<Vertex, Integer>();
		this.low = new HashMap<Vertex, Integer>();
		this.disc = new HashMap<Vertex, Integer>();
		this.inStack = new HashMap<Vertex, Boolean>();
	    this.time = 0;
		this.scc = 0;
		this.stack = new Stack<Vertex>();
	}

	public void changeBelongs(Vertex v, int newbelongs) {
        if (belongs.containsKey(v)) {
            belongs.remove(v);
		}

		belongs.put(v, newbelongs);
	}

	public void changeLow(Vertex v, int newlow) {
        if (low.containsKey(v)) {
            low.remove(v);
		}

		low.put(v, newlow);
	}


	public void changeDisc(Vertex v, int newdisc) {
        if (disc.containsKey(v)) {
            disc.remove(v);
		}

		disc.put(v, newdisc);
	}

	public void changeinStack(Vertex v, boolean newinStack) {
        if (inStack.containsKey(v)) {
            inStack.remove(v);
		}

		inStack.put(v, newinStack);
	}

	public void dfs(Graph<Vertex> g, Vertex u) {
	   time++;
       changeLow(u, time);
	   changeDisc(u, time);
	   changeinStack(u, true);
	   stack.push(u);

       List<Vertex> edgelist = g.getEdge(u);
	   //System.out.println(u + " " + disc.get(u) + " " +low.get(u) + " " +belongs.get(u) + " " +inStack.get(u));
       for (Vertex v: edgelist) {
           //System.out.println("->" + v + " " + disc.get(v) + " " +low.get(v) + " " +belongs.get(v) + " " +inStack.get(v));
	   }

	   //System.out.println("\n");
	   for (Vertex v: edgelist) {
           if (disc.get(v) == -1) {
			   dfs(g, v);
			   if (low.get(v) < low.get(u)) {
                   changeLow(u, low.get(v));
			   }
		   }
		   else if (inStack.get(v)) {
               if (disc.get(v) < low.get(u)) {
                   changeLow(u, disc.get(v));
			   }
		   }
	   }

	   //System.out.println("HI" + u + " " + disc.get(u) + " " +low.get(u) + " " +belongs.get(u) + " " +inStack.get(u));
	   int ulow = low.get(u);
	   int udisc = disc.get(u);
	   if (ulow == udisc) {
		   //System.out.println(u + "hi");
		   scc++;
           while(stack.peek().getIndex() != u.getIndex()) {
			   changeBelongs(stack.peek(), scc);
               changeinStack(stack.peek(), false);
			   stack.pop();
		   }
		   changeBelongs(stack.peek(), scc);
           changeinStack(stack.peek(), false);
		   stack.pop();
	   }
	}

    public boolean check() {
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
	}

	public void SCC(Graph<Vertex> g) {
		Iterator<Vertex> all = g.getAllVertice();

		while (all.hasNext()) {
            Vertex i = all.next();
			if (low.get(i) == -1) {
                dfs(g, i);
			}
		}
	}

    public static void main(String[] args) {
        String filename = "set6.txt";
		String line = null;
		int numClause = 0;
		twoSAT solution = new twoSAT();
		
        try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			numClause = Integer.parseInt(reader.readLine());
			Graph<Vertex> clause = new Graph<Vertex>();

			while((line = reader.readLine()) != null) {
				String item[] = line.split(" ");
				int idx1 = Integer.parseInt(item[0]);
				int idx2 = Integer.parseInt(item[1]);
				Vertex s1 = new Vertex(-idx1);
				Vertex d1 = new Vertex(idx2);
				Vertex s2 = new Vertex(-idx2);
				Vertex d2 = new Vertex(idx1);

				clause.addVertex(s1);
				clause.addVertex(s2);
				clause.addVertex(d1);
				clause.addVertex(d2);

				clause.addEdge(s1, d1);
				clause.addEdge(s2, d2);

				solution.changeBelongs(s1, -1);
				solution.changeBelongs(s2, -1);
				solution.changeBelongs(d1, -1);
				solution.changeBelongs(d2, -1);
				solution.changeLow(s1, -1);
				solution.changeLow(s2, -1);
				solution.changeLow(d1, -1);
				solution.changeLow(d2, -1);
				solution.changeDisc(s1, -1);
				solution.changeDisc(s2, -1);
				solution.changeDisc(d1, -1);
				solution.changeDisc(d2, -1);
				solution.changeinStack(s1, false);
				solution.changeinStack(s2, false);
				solution.changeinStack(d1, false);
				solution.changeinStack(d2, false);
			}

			solution.SCC(clause);

			if (solution.check()) {
				System.out.println("satisfiable");
			}
			else {
                System.out.println("unsatisfiable");
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Error:" + e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
