
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculatePi {
	static NumberFormat formatter = new DecimalFormat("#0.000"); 
	public static void main(String[] args) {
		int numWorkers = 0;
		double numTotalDarts = 0;
		try {
			System.out.println("Enter the number of Workers: ");
			InputStreamReader inStream = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(inStream);
			numWorkers = Integer.parseInt(in.readLine());
			System.out.println("Enter the number of Dart Throws: ");
			numTotalDarts = Double.parseDouble(in.readLine());
			System.out.println();
		} catch (NumberFormatException e) {
			System.out.println("C'mon. Start again.");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    int count = 1;
		int successCount= 0;
		List<Double> pies = new ArrayList<Double>();
		// Master 
		// Loops through a number that represents
		// Number of workers 
		for (int x = 0; x < numWorkers; x++) {
			SecureRandom random = new SecureRandom();
			double seedNum = random.nextDouble() * numWorkers;
		// add to an array the value returned from
		// Sending seedNum, numTotalDarts to worker to calculate pi
			double currentPi = findPi(seedNum,numTotalDarts);
			pies.add(currentPi);
			if (Math.abs((Math.PI-currentPi)) <= .0314){
				successCount++;
			}
		// sum up all values in array and divide by array length	
		// to determine average result
			double sum = 0;
			for (double p : pies) {
				sum += p;
			}
			double average = 1.0d * sum / pies.size();	
			System.out.println("Results after " + count + " workers: ");
			System.out.println("Current value of average of master pi: " + formatter.format(average));
			System.out.println(successCount + " successful workers: " + "of " + count + " workers with " +numTotalDarts + " darts thrown.");	
			double diff = Math.abs((Math.PI-average));	
			System.out.println("Overall difference from pi: " + formatter.format(diff));
			if (diff <= .0314){
				System.out.println("Overall Calculated pi is within 1% accuracy");
			} else {
				System.out.println("Overall Calculated pi is not accurate");
			}
			System.out.println();
			count++;
		} //  end Master Loop
	} // end main function

	// worker function
	private static double findPi(double seedNum, double numTotalDarts) {
		int numDartsIn = 0;
		// Throwing darts and finding number hit in circle
		for (int i = 0; i < numTotalDarts; i++) {
			double xcoord = Math.random();
	        double ycoord = seedNum ;    
	        // if dart is in circle
	        if (xcoord*xcoord + ycoord*ycoord <= 1) { 
	        		numDartsIn++;
	        }
	        SecureRandom random = new SecureRandom();
			seedNum = random.nextDouble();
		  }
		double approx_pi = (numDartsIn * 4)/numTotalDarts;
		return approx_pi;
	}
}
