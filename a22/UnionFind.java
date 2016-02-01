import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class UFNode {
    int parent;
	int rank;

	UFNode(int parent, int rank) {
        this.parent = parent;
		this.rank = rank;
	}
}

public class UnionFind {
    private UFNode[] nodeHolder;
	int numVertices;
	private int count;

	public UnionFind(int size) {
        /*if(size < 0) {
			throw new IllegalArgumentException();
		}*/

		count = size;
		numVertices = size;
		nodeHolder = new UFNode[size];

		for (int i = 0; i < size; i++) {
			nodeHolder[i] = new UFNode(i, 1);
		}
	}

	public int Find(int vertex) {
        /*if (vertex < 0 || vertex >= nodeHolder.length) {
			throw new IndexOutofBoundsException();
		}*/

		if (nodeHolder[vertex].parent != vertex) {
			nodeHolder[vertex].parent = Find(nodeHolder[vertex].parent);
		}

		return nodeHolder[vertex].parent; 
	}

	public int getCount() {
		return count;
	}

	public boolean isConnected(int v1, int v2) {
		return Find(v1) == Find(v2);
	}

    public void Union(int v1, int v2) {
        int i = Find(v1);
		int j = Find(v2);

		if(i == j) {
			return;
		}

		if (nodeHolder[i].rank < nodeHolder[j].rank) {
			nodeHolder[i].parent = j;
			nodeHolder[j].rank += nodeHolder[i].rank;
		} else {
            nodeHolder[j].parent = i;
			nodeHolder[i].rank += nodeHolder[j].rank;
		}
		count--;
	}
}
