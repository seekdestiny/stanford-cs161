import java.io.*;

public class knack {
    public static int max(int i1, int i2) {
        if (i1 > i2) {
            return i1;
		}
		else {
            return i2;
		}
	}

    public static void main(String[] args) {
        String filename = "q2.txt";
		String line = null;
        int num;
	    int W;
	    int[] value;
		int[] weight;
		int[][] dp;

		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			line = reader.readLine();
			String field[] = line.split(" ");
			W = Integer.parseInt(field[0]);
			num = Integer.parseInt(field[1]);

			value = new int[num+1];
			weight = new int[num+1];

			int n= 1;
			while((line = reader.readLine()) != null) {
				String item[] = line.split(" ");
				value[n] = Integer.parseInt(item[0]);
				weight[n] = Integer.parseInt(item[1]);
				n++;
			}

			/*dp = new int[num+1][W + 1];

			for (int i = 1; i<=num; i++) { 
			    for (int x = 0; x <= W; x++) {
					if (weight[i] > x) {
                        dp[i][x] = dp[i-1][x];
					}
					else {
                        dp[i][x] = max(dp[i-1][x], dp[i-1][x-weight[i]] + value[i]);
					}
			    }
			}*/

            dp = new int[2][W + 1];

			for(int count = 1; count<=num; count++) {
			    for (int x = 0; x <= W; x++) {
					int current = count%2;
					int prev = (count+1)%2;

					//System.out.println(current + " " + prev);

					if (weight[count] > x) {
                        dp[current][x] = dp[prev][x];
					}
					else {
                        dp[current][x] = max(dp[prev][x], dp[prev][x-weight[count]] + value[count]);
					}
				}
			}

			System.out.println(dp[num%2][W]);
		}
		catch(FileNotFoundException e) {
            System.err.println("Error:" + e.getMessage());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
