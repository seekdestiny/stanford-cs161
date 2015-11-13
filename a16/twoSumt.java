import java.util.*;
import java.io.*;

public class twoSumt {
    public static void main(String[] args) {
		String filename = "data.txt";
		String line = "";
		Set<Double> data = new HashSet<Double>();
		boolean[] t = new boolean[20001];

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
            
			while ((line = reader.readLine()) != null) {
				double temp = Double.parseDouble(line);
				data.add(temp);
			}

            Iterator itr = data.iterator();

		    while(itr.hasNext()) {
		        double current = (double) itr.next();

				for (int i = -10000; i <= 10000; i++) {
                    if (!t[i + 10000]) {
                        double rest = i - current;

						if (current != rest) {
                            if (data.contains(rest)) {
                                t[i + 10000] = true;
							}
						}
					}
				}
			}

			int count = 0;

			for (int j = 0; j < 20001; j++) {
                if (t[j]) {
					count++;
				}
			}
			System.out.println(count);
		} catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
