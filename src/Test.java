import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		/**
		 * for(int i = 1; i<= 5; i++){
		 * 
		 * }
		 * 
		 * for(int i = 1; i<= 5; i++){
		 * 
		 * }
		 **/

		System.out.println("SHORTEST REMAINING TIME: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("SRT Run #" + i + ": ");
			SRT srt = new SRT(createProcessList());
			runAlgorithm(srt);
		}

		System.out.println("ROUND ROBIN: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("RR Run #" + i + ": ");
			RR rr = new RR(createProcessList());
			runAlgorithm(rr);
		}

	}

	/**
	 * Creates a list of 30 new random processes
	 * @return list of processes
	 */
	public static List<Process> createProcessList() {
		List<Process> arrivalList = new LinkedList<Process>();
		Process.setRandomSeek(System.currentTimeMillis()); //
		for (int i = 0; i < 30; i++) {
			Process p = Process.make();
			arrivalList.add(p);
		}
		Collections.sort(arrivalList);
		for (int i = 0; i < arrivalList.size(); i++) {
			// update the process id with its index in arrivalList
			// it may need for some algorithms
			Process p = arrivalList.get(i);
			p.id = i;
			arrivalList.set(i, p);
		}

		return arrivalList;
	}

	/**
	 * Calculate averages for waiting time, turnaround time and response time
	 * 
	 * @param finishedProcesses
	 */
	private static void calculateAverageTimes(List<Process> finishedProcesses) {
		float waitingTime = 0;
		float turnaroundTime = 0; // the time in ready list to completed execute
		float responseTime = 0;

		for (Process p : finishedProcesses) {
			waitingTime += p.waitingTime;
			turnaroundTime += p.turnaroundTime;
			responseTime += p.responseTime;

		}

		waitingTime = waitingTime / finishedProcesses.size();
		turnaroundTime = turnaroundTime / finishedProcesses.size();
		responseTime = responseTime / finishedProcesses.size();

		System.out.println("Average Waiting Time: " + waitingTime);
		System.out.println("Average Turnaround Time: " + turnaroundTime);
		System.out.println("Average Response Time: " + responseTime);

	}

	/**
	 * Runs the algorithm and prints timeline and averages for each run
	 * 
	 * @param algo
	 */
	public static void runAlgorithm(ProcessAlgorithm algo) {

		for (Process p : algo.returnArrival()) {
			p.display();
		}

		algo.run();
		System.out.println();
		calculateAverageTimes(algo.getFinishedList());
		System.out.println("**********************************************");

	}

}