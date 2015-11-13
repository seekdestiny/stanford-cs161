import java.util.*;
import java.io.*;

public class Median {
    public static void main(String[] args) {
        String filename = "median.txt";
		String line = "";
		int[] data = new int[10000];
		PriorityQueue<Integer> maxheap = new PriorityQueue<Integer>(Collections.reverseOrder());
		PriorityQueue<Integer> minheap = new PriorityQueue<Integer>();

        try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));

            int i = 0;
			while ((line = reader.readLine()) != null) {
				data[i] = Integer.parseInt(line);
				i++;
			}
		
			int mediansum = 0;

			maxheap.add(data[0]);
			mediansum += data[0];

			for (int j = 1; j < 10000; j++) {
				if (data[j] < maxheap.peek()) {
					maxheap.add(data[j]);
				} else {
                    minheap.add(data[j]);
				}

				if (maxheap.size() - minheap.size() > 1) {
					minheap.add(maxheap.poll());
				} 
				if (minheap.size() > maxheap.size()) {
                    maxheap.add(minheap.poll());
				}

				mediansum += maxheap.peek();
			}

			System.out.println(mediansum);

		} catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
