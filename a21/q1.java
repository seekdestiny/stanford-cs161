import java.util.Arrays;
import java.util.Comparator;
import java.io.*;

class job implements Comparable<job>{
    int weight;
	int length;
	int diff;
	float ratio;

	public job () {
        this.weight = 0;
		this.length = 0;
		this.diff = 0;
		float ratio = 0;
	}

	public job (int weight, int length) {
        this.weight = weight;
		this.length = length;
		this.diff = weight-length;
		this.ratio = (float) weight/length;
	}

	public int compareTo(job comparejob) {
		if (comparejob.diff == this.diff) {
            return comparejob.weight - this.weight;
		}
		else {
            return comparejob.diff - this.diff;
		}
	}

	public static Comparator<job> jobRatio = new Comparator<job>() {
	    public int compare(job job1, job job2) {
            if (job2.ratio - job1.ratio < 0) {
				return -1;
			}
			else if (job2.ratio - job1.ratio > 0) {
				return 1;
			}
			else {
				return 0;
			}
		}	
	};
}

public class q1 {  
	public static void main(String[] args) {
        String line = null;
		String filename = "jobs.txt";
		int num = 0;

        try {		
		   BufferedReader reader = new BufferedReader(new FileReader(filename));
           num = Integer.parseInt(reader.readLine());
           job[] info = new job[num];

		   int j = 0;
		   while((line = reader.readLine()) != null) {
			   String item[] = line.split(" ");
			   int weight = Integer.parseInt(item[0]);
			   int length = Integer.parseInt(item[1]);
               info[j] = new job(weight, length);
			   j++;
//			   if (j == 10000) break;
		   }
           System.out.println(j + " " + num);
        
//		    info[0] = new job(8, 50);
//		    info[1] = new job(74, 59);
//		    info[2] = new job(31, 73);
//	     	info[3] = new job(45, 79);

            Arrays.sort(info);
//            Arrays.sort(info, job.jobRatio);
//
            double sum = 0;
		    int acclength = 0;

            for (int m = 0; m < info.length; m ++) {
			    acclength = acclength + info[m].length;
 			    sum = sum + info[m].weight * acclength;
		    }
            System.out.println(sum + " " + acclength);

            System.out.println(sum);

            for (int i = 0; i < info.length; i ++) {
                System.out.println(info[i].weight + " " + info[i].length + " " +
					           info[i].diff   + " " + info[i].ratio);
		    }

			reader.close();
		}
		catch(FileNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
}
