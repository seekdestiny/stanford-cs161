import java.io.*;

public class quicksort {
	public int quickSort(int[] data, int left, int right) {

		if (left >= right) return 0;

		int mid = (left + right) / 2;
		int target = 0;

        if (data[left] < data[right]) {
			if (data[right] < data[mid]) {
				target = right;
			} else {
				if (data[left] < data[mid]) {
				    target = mid;
				} else {
					target = left;
				}
			}
		} else {
			if (data[left] < data[mid]) {
				target = left;
			} else {
				if (data[right] < data[mid]) {
					target = mid;
				} else {
					target = right;
				}
			}
		}

		int t = data[left];
		data[left] = data[target];
		data[target] = t;

        int pivot = partion(data, left, right);

		int leftC = quickSort(data, left, pivot - 1);
		int rightC = quickSort(data, pivot + 1, right);
 
        return leftC + right - left + rightC;
	}

	public int partion(int[] data, int left, int right) {
		int l = left;
		int i = l + 1;
		
		for (int j = i; j <= right; j++) {
			if (data[j] < data[l]) {
				int temp = data[i];
				data[i] = data[j];
				data[j] = temp;
				i++;
			}
		}

		if (i > l + 1) {
		    int temp = data[l];
		    data[l] = data[i - 1];
			data[i - 1] = temp;
		}

		return i - 1;
	}

    public static void main(String[] args) {
		int[] data = new int[10000];
		String filename = "data.txt";
		String line = "";
		quicksort solution = new quicksort();

        try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));

            int i = 0;
			while ((line = reader.readLine()) != null) {
				data[i] = Integer.parseInt(line);  
				i++;
			}

			/*for (int j = 0; j < data.length; j++) {
				System.out.println(data[j]);
			}*/

            System.out.println(solution.quickSort(data, 0, data.length - 1)); 

            /*for (int j = 0; j < data.length; j++) {
				System.out.println(data[j]);
			}*/

		} catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
		} catch (IOException e) {
            e.printStackTrace();
	    }
	}
}
