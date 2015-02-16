import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class FCFS extends ProcessAlgorithm {
	
	private List<Process> arrival;
	private List<Process> finishedList;
	private Queue<Process> readyQueue;
	Process currP;
	int totalTime = Process.MAX_QUANTA;
	
	public FCFS(List<Process> arrivalList) {
		arrival = arrivalList;
		Collections.sort(arrival); // sort by arrival time
		finishedList = new ArrayList<Process>();
		readyQueue = new LinkedList<Process>();
	}
	
	
	/**
	 * Adds processes to the ready queue when they arrive
	 * 
	 * @param aTime
	 *            the arrival time
	 */
	public void addToReadyQueue(int aTime) {
		boolean keepAdding = true;
		while (keepAdding) {
			if (!arrival.isEmpty() && arrival.get(0).arrivalTime <= aTime) {
				readyQueue.add(arrival.remove(0));
			} else {
				keepAdding = false;
			}
		}
	}
	
	/**
	 * Runs a particular process in one quantum
	 * @param currentP the process to be executed
	 * @param timeSlice the time at which it is executed
	 * @param priorityLevel the priority level of the process
	 */
	private void runOneQuantum(Process currentP, int timeSlice, int priorityLevel, boolean addMore) {
		if(currentP.remainTime - 1 > 0){
			currentP.remainTime = currentP.remainTime - 1;
			
			if(!currentP.runFlag){
				//the first time to run
				currentP.runFlag=true;
				currentP.waitingTime=timeSlice- currentP.arrivalTime;
				currentP.responseTime=currentP.waitingTime;
			}
			if(addMore){
				addToReadyQueue(timeSlice + 1); //check if any arrived in between run
			}
		}
		else{
			currentP.turnaroundTime=timeSlice + currentP.remainTime - currentP.arrivalTime;
			currentP.remainTime = 0;
			finishedList.add(currentP);
			readyQueue.remove(currentP);
		}
	}
	
	/**
	 * Add wait times to all the processes in the ready queue
	 */
	private void addWaittimes(){
		for(Process p: readyQueue){
			p.waitingTime = (float) (p.waitingTime + 1);
		}
	}
	
	/**
	 * Remove processes that haven't started yet.
	 */
	private void removeExtraProcesses() {
		Iterator<Process> iterator = readyQueue.iterator();
		while (iterator.hasNext()) {
			Process process = (Process) iterator.next();
			if (!process.runFlag) {
				iterator.remove();
			}
		}
	}
	
	@Override
	void runAlgorithm() {
		for(int i = 0; i < Process.MAX_QUANTA ; i++) {
			addToReadyQueue(i);
			if (!readyQueue.isEmpty()) {
				Process currP = readyQueue.peek();
				runOneQuantum(currP, i, currP.priority, true);
				addWaittimes();
				System.out.print("P"+ (currP.id + 1));
			} else {
				System.out.print("-");
			}
		}
		
		//handle unfinished processes
		totalTime = Process.MAX_QUANTA;
		
		removeExtraProcesses();

		while (!readyQueue.isEmpty()) {
			currP = readyQueue.peek();
			runOneQuantum(currP, totalTime, 1, false);
			addWaittimes();
			System.out.print("P"+ (currP.id + 1));	
			totalTime++;
		}
	}

	@Override
	List<Process> returnArrival() {
		return arrival;
	}

	@Override
	List<Process> getFinishedList() {
		return finishedList;
	}


	@Override
	int getTotalRuntime() {
		return totalTime;
	}

}
