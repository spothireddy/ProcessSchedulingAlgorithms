import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class HPFNonpreemptive extends ProcessAlgorithm {

	private List<Process> arrival;
	private List<Process> finishedList;
	private Queue<Process> readyQueue1; // priority 1 queue
	private Queue<Process> readyQueue2; // priority 2 queue
	private Queue<Process> readyQueue3; // priority 3 queue
	private Queue<Process> readyQueue4; // priority 4 queue
	Process currP;

	public HPFNonpreemptive(List<Process> arrivalList) {
		readyQueue1 = new PriorityQueue<Process>();
		readyQueue2 = new PriorityQueue<Process>();
		readyQueue3 = new PriorityQueue<Process>();
		readyQueue4 = new PriorityQueue<Process>();
		finishedList = new ArrayList<Process>();
		arrival = arrivalList;
		Collections.sort(arrivalList); // sort by arrival time
	}

	@Override
	List<Process> returnArrival() {
		return arrival;
	}

	@Override
	void runAlgorithm() {
		int i = 0;
		for(; i<Process.MAX_QUANTA ; i++){
			addToReadyQueue(i);
			
			if(!readyQueue1.isEmpty()){
				currP = readyQueue1.peek();
				addWaittimes();
				runOneQuantum(currP, i, 1, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else if(!readyQueue2.isEmpty()){
				currP = readyQueue2.peek();
				addWaittimes();
				runOneQuantum(currP, i, 2, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else if(!readyQueue3.isEmpty()){
				currP = readyQueue3.peek();
				addWaittimes();
				runOneQuantum(currP, i, 3, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else if(!readyQueue4.isEmpty()){
				currP = readyQueue4.peek();
				addWaittimes();
				runOneQuantum(currP, i, 4, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else{
				System.out.print("-");
			}
	
		}
		
		//handle unfinished processes
		int processOvertime = Process.MAX_QUANTA;
		
		//get rid of processes that haven't started yet (run time = remaining time)
		removeExtraProcesses();
		
		while(!readyQueue4.isEmpty() | !readyQueue3.isEmpty() | !readyQueue2.isEmpty() | !readyQueue1.isEmpty()){
			if(!readyQueue1.isEmpty()){
				currP = readyQueue1.peek();
				addWaittimes();
				runOneQuantum(currP, processOvertime, 1, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else if(!readyQueue2.isEmpty()){
				currP = readyQueue2.peek();
				addWaittimes();
				runOneQuantum(currP, processOvertime, 2, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else if(!readyQueue3.isEmpty()){
				currP = readyQueue3.peek();
				addWaittimes();
				runOneQuantum(currP, processOvertime, 3, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else if(!readyQueue4.isEmpty()){
				currP = readyQueue4.peek();
				addWaittimes();
				runOneQuantum(currP, processOvertime, 4, true);
				System.out.print("P"+ (currP.id + 1));
			}
			else{
				System.out.print("-");
			} 
			processOvertime++;
		}
	}

	/**
	 * Runs a particular process in one quantum
	 * @param currentP the process to be executed
	 * @param timeSlice the time at which it is executed
	 * @param priorityLevel the priority level of the process
	 */
	private void runOneQuantum(Process currentP, int timeSlice, int priorityLevel, boolean addMore) {
		if(currP.remainTime - 1 > 0){
			currP.remainTime = currP.remainTime - 1;
			
			if(!currP.runFlag){
				//the first time to run
				currP.runFlag=true;
				currP.waitingTime=timeSlice- currP.arrivalTime;
				currP.responseTime=currP.waitingTime;
			}
			if(addMore){
				addToReadyQueue(timeSlice + 1); //check if any arrived in between run
			}
			
			
		}
		else{
			currP.turnaroundTime=timeSlice + currP.remainTime - currP.arrivalTime;
			currP.remainTime = 0;
			finishedList.add(currP);
			
			switch(priorityLevel){
			case 1:
				readyQueue1.remove(currentP);
				break;
			case 2:
				readyQueue2.remove(currentP);
				break;
			case 3:
				readyQueue3.remove(currentP);
				break;
			case 4:
				readyQueue4.remove(currentP);
				break;
			}
		}
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
				int priorityLevel = arrival.get(0).priority;
				switch (priorityLevel) {
					case 1:
						readyQueue1.add(arrival.remove(0));
						break;
					case 2:
						readyQueue2.add(arrival.remove(0));
						break;
					case 3:
						readyQueue3.add(arrival.remove(0));
						break;
					case 4:
						readyQueue4.add(arrival.remove(0));
						break;
				}
			} else {
				keepAdding = false;
			}
		}
	}
	
	/**
	 * Add wait times to all the processes in the ready queue
	 */
	private void addWaittimes(){
		for(Process p: readyQueue1){
			p.waitingTime = (float) (p.waitingTime + 1);
		}
		for(Process p: readyQueue2){
			p.waitingTime = (float) (p.waitingTime + 1);
		}
		for(Process p: readyQueue3){
			p.waitingTime = (float) (p.waitingTime + 1);
		}
		for(Process p: readyQueue4){
			p.waitingTime = (float) (p.waitingTime + 1);
		}
	}
	
	/**
	 * Remove processes that haven't started yet.
	 */
	private void removeExtraProcesses(){
		Queue<Process> tempQueue = new LinkedList<Process>();
		for(Process p: readyQueue1){
			if(p.runTime == p.remainTime){
				tempQueue.add(p);
			}
		}
		readyQueue1.clear();
		readyQueue1.addAll(tempQueue);
		tempQueue.clear();
		
		for(Process p: readyQueue2){
			if(p.runTime == p.remainTime){
				tempQueue.add(p);
			}
		}
		readyQueue2.clear();
		readyQueue2.addAll(tempQueue);
		tempQueue.clear();
		
		for(Process p: readyQueue3){
			if(p.runTime == p.remainTime){
				tempQueue.add(p);
			}
		}
		readyQueue3.clear();
		readyQueue3.addAll(tempQueue);
		tempQueue.clear();
		
		for(Process p: readyQueue4){
			if(p.runTime == p.remainTime){
				tempQueue.add(p);
			}
		}
		readyQueue4.clear();
		readyQueue4.addAll(tempQueue);
		tempQueue.clear();
	}

	@Override
	List<Process> getFinishedList() {
		return finishedList;
	}

}
