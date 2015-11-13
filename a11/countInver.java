import java.util.*;
import java.io.*;

public class countInver {
	public double countInversion(int[] data) {
        if (data.length < 2) return 0;

		int mid = data.length/2;

		int[] leftArray = Arrays.copyOfRange(data, 0, mid);
		int[] rightArray = Arrays.copyOfRange(data, mid, data.length);

        double leftN = countInversion(leftArray);
		double rightN = countInversion(rightArray);
		double splitInv = countSplitInv(data, leftArray, rightArray);

		return leftN + splitInv + rightN;
	}

	public double countSplitInv(int[] data, int[] leftArray, int[] rightArray) {
	    double splitInv = 0;

        int totalL = leftArray.length + rightArray.length;
		int i, li, ri;
		i = li = ri = 0;

		while (i < totalL) {
            if ((li < leftArray.length) && (ri < rightArray.length)) {
                if (leftArray[li] < rightArray[ri]) {
                    data[i] = leftArray[li];
					i++;
					li++;
					splitInv += ri;
				} else {
					data[i] = rightArray[ri];
					i++;
					ri++;
				}
			} else {
				if (li >= leftArray.length) {
                    while (ri < rightArray.length) {
                        data[i] = rightArray[ri];
						i++;
						ri++;
					}
				}
				if (ri >= rightArray.length) {
                    while (li < leftArray.length) {
                        data[i] = leftArray[li];
						i++;
						li++;
						splitInv += rightArray.length;
					}
				}
			}
		}

		return splitInv;
	}

    public static void main(String[] args) {
        int[] data = new int[100000];
        String file = "data.txt";
		String line = "";

		countInver solution = new countInver();

		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));

			int i = 0;
            while ((line = reader.readLine()) != null) {
                data[i] = Integer.parseInt(line);
				i++;
			}
         
			/*for (int j = 0; j < data.length; j++) {
                System.out.println(data[j]);
            }*/

            System.out.println(solution.countInversion(data));
            
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
