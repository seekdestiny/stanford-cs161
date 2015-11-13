import java.util.*;
import java.io.*;

public class twoSum {












    public static void main(String[] args) {
		String filename = "data.txt";
		String line = "";
		Set<Double> data = new HashSet<Double>();		

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
            
			while ((line = reader.readLine()) != null) {
				double temp = Double.parseDouble(line);
				data.add(temp);
			}

			int count = 0;
			for (double target = -10000; target <= 10000; target++) {
                Iterator itr = data.iterator();

				while(itr.hasNext()) {
				   double current = (double) itr.next();
                   double rest = target - current;
				   //System.out.println(current + " " + rest + " " + target);
                   
				   if (current != rest) { 
				       if (data.contains(rest)) {
                           count++;
					       break;
				       }
				   }
				}

				//break;
			}

			System.out.println(count);
		} catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
