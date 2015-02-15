import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Test {

	//Holds avg data for 5 runs of an algorithm
	private static float tempAverageWaitingTime = 0;
	private static float tempAverageTurnaroundTime = 0;
	private static float tempAverageResponseTime =0;
	private static float tempAverageThroughputTime = 0;
	
	public static void main(String[] args) {

		System.out.println("FIRST-COME FIRST-SERVED: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("FCFS Run #" + i + ": ");
			FCFS fcfs = new FCFS(createProcessList());
			runAlgorithm(fcfs);
		}
		printAveragesForAlgorithm();
		
		System.out.println("SHORTEST JOB FIRST: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("SJF Run #" + i + ": ");
			SJF sjf = new SJF(createProcessList());
			runAlgorithm(sjf);
		}
		printAveragesForAlgorithm();
		
		System.out.println("SHORTEST REMAINING TIME: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("SRT Run #" + i + ": ");
			SRT srt = new SRT(createProcessList());
			runAlgorithm(srt);
		}
		printAveragesForAlgorithm();

		System.out.println("ROUND ROBIN: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("RR Run #" + i + ": ");
			RR rr = new RR(createProcessList());
			runAlgorithm(rr);
		}
		printAveragesForAlgorithm();
		
		System.out.println("HPF Preemptive: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("HPF Preemptive Run #" + i + ": ");
			HPFPreemptive hpfp = new HPFPreemptive(createProcessList());
			for (Process p : hpfp.returnArrival()) {
				p.display();
			}
			System.out.println("Timeline: ");
			hpfp.runAlgorithm();
			System.out.println();
			calculateAveragesOnPriority(hpfp.getFinishedList());
			System.out.println("Overall: ");
			calculateAverageTimes(hpfp.getFinishedList());
			System.out.println("**********************************************");
			
		}
		printAveragesForAlgorithm();
		
		
		System.out.println("HPF Nonpreemptive: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("HPF Nonpreemptive Run #" + i + ": ");
			HPFNonpreemptive hpfnp = new HPFNonpreemptive(createProcessList());
			for (Process p : hpfnp.returnArrival()) {
				p.display();
			}
			System.out.println("Timeline: ");
			hpfnp.runAlgorithm();
			System.out.println();
			calculateAveragesOnPriority(hpfnp.getFinishedList());
			System.out.println("Overall: ");
			calculateAverageTimes(hpfnp.getFinishedList());
			System.out.println("**********************************************");
			
		}
		printAveragesForAlgorithm();
		
		System.out.println("HPFNonpreemptiveAging: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("HPFNonpreemptiveAging Run #" + i + ": ");
			HPFNonpreemptiveAging hpfnpA = new HPFNonpreemptiveAging(createProcessList());
			runAlgorithm(hpfnpA);
			
		}
		
		System.out.println("HPF Preemptive Aging: ");
		for (int i = 1; i <= 5; i++) {
			System.out.println("HPF Preemptive Aging Run #" + i + ": ");
			HPFPreemptiveAging hpfpA = new HPFPreemptiveAging(createProcessList());
			runAlgorithm(hpfpA);
		}
		printAveragesForAlgorithm();

	}

	/**
	 * Creates a list of 30 new random processes
	 * 
	 * @return list of processes
	 */
	public static List<Process> createProcessList() {
		List<Process> arrivalList = new LinkedList<Process>();
		Process.setRandomSeek(0); // test with 0 for debugging otherwise use System.currentTimeMillis()
															
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
		float throughput = 0; //TODO

		for (Process p : finishedProcesses) {
			waitingTime += p.waitingTime;
			turnaroundTime += p.turnaroundTime;
			responseTime += p.responseTime;

		}

		waitingTime = waitingTime / finishedProcesses.size();
		turnaroundTime = turnaroundTime / finishedProcesses.size();
		responseTime = responseTime / finishedProcesses.size();
		
		tempAverageWaitingTime += waitingTime;
		tempAverageTurnaroundTime += turnaroundTime;
		tempAverageResponseTime += responseTime;

		System.out.println("Average Waiting Time: " + waitingTime);
		System.out.println("Average Turnaround Time: " + turnaroundTime);
		System.out.println("Average Response Time: " + responseTime);
		System.out.println("Throughput: "+ throughput);

	}
	
	/**
	 * Calculate averages based on priority
	 */
	private static void calculateAveragesOnPriority(List<Process> priorityProcesses){
		List<Process> priority1 = new LinkedList<Process>();
		List<Process> priority2 = new LinkedList<Process>();
		List<Process> priority3 = new LinkedList<Process>();
		List<Process> priority4 = new LinkedList<Process>();
		
		for(Process p: priorityProcesses){
			int priorityLevel = p.priority;
			switch(priorityLevel){
			case 1:
				priority1.add(p);
				break;
			case 2:
				priority2.add(p);
				break;
			case 3:
				priority3.add(p);
				break;
			case 4:
				priority4.add(p);
				break;
			}
		}
		
		System.out.println("Priority 1: ");
		calculateAverageTimes(priority1);
		
		System.out.println("Priority 2: ");
		calculateAverageTimes(priority2);
		
		System.out.println("Priority 3: ");
		calculateAverageTimes(priority3);
		
		System.out.println("Priority 4: ");
		calculateAverageTimes(priority4);
		
		
		
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
		System.out.println("Timeline: ");
		algo.runAlgorithm();
		System.out.println();
		calculateAverageTimes(algo.getFinishedList());
		System.out.println("**********************************************");

	}
	
	/**
	 * Reset temp averages
	 */
	public static void resetTempAverages(){
		tempAverageWaitingTime = 0;
		tempAverageTurnaroundTime = 0;
		tempAverageResponseTime =0;
		tempAverageThroughputTime = 0;
	}
	
	/**
	 * Print out averages for 5 runs of the algorithm
	 */
	public static void printAveragesForAlgorithm(){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Averages for the 5 runs: ");
		System.out.println("Average Waiting Time: " + (tempAverageWaitingTime/5));
		System.out.println("Average Turnaround Time: " + (tempAverageTurnaroundTime/5));
		System.out.println("Average Response Time: " + (tempAverageResponseTime/5));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		resetTempAverages(); //reset values to zero;
	}

}