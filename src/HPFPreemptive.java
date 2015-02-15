import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class HPFPreemptive extends ProcessAlgorithm {

	private List<Process> arrival;
	private List<Process> finishedList;
	private PriorityQueue<Process> readyQueue1; // priority 1 queue
	private PriorityQueue<Process> readyQueue2; // priority 2 queue
	private PriorityQueue<Process> readyQueue3; // priority 3 queue
	private PriorityQueue<Process> readyQueue4; // priority 4 queue
	Process currP;

	public HPFPreemptive(List<Process> arrivalList) {
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
		// TODO Auto-generated method stub

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

	@Override
	List<Process> getFinishedList() {
		// TODO Auto-generated method stub
		return null;
	}

}
