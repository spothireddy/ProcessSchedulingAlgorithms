import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class SJF extends ProcessAlgorithm{

	private List<Process> arrival;
	private List<Process> finishedList; //When a process is done, put it in this list
	private List<Process> readyQueue; 
	
	Process currP;
	int totalTime = Process.MAX_QUANTA;
	
	public SJF(List<Process> arrivalList){
		readyQueue =new ArrayList<Process>();
		finishedList = new ArrayList<Process>();
		arrival=arrivalList;
		Collections.sort(arrivalList); //sort by arrival time

	}
	
	public List<Process> returnArrival(){
		return arrival;
	}
	public void runAlgorithm(){
		int i = 0;
		for(; i<Process.MAX_QUANTA ; i++){
			addToReadyQueue(i);
			//Process currP;
			if(!readyQueue.isEmpty()){
				currP = readyQueue.remove(0);
				
				runOneQuantum(currP, i, true);
				
				//Add waiting time to processes currently not running
				for(Process p: readyQueue){
					p.waitingTime = (float) (p.waitingTime + 1);
							
				}
				
				System.out.print("P"+ (currP.id + 1)); 
			}
			else{
				System.out.print("-");
			}
		}
		
		//handle unfinished processes
		totalTime = Process.MAX_QUANTA;
		List<Process> tempQueue = new ArrayList<Process>();
		//get rid of processes that haven't started yet (run time = remaining time)
		for(Process p: readyQueue){
			if(p.runFlag){
				tempQueue.add(p); //contains processes that should be finished
			}
		}
		
		readyQueue.clear();
		readyQueue.addAll(tempQueue);
		
		while(!readyQueue.isEmpty()){
			currP = readyQueue.remove(0);
			
			
			runOneQuantum(currP, totalTime, false); //Use Process overtime instead of i
			//Add waiting time to processes currently not running
			for(Process p: readyQueue){
				p.waitingTime = (float) (p.waitingTime + 1);
						
			}
			System.out.print("P"+ (currP.id + 1)); 
			totalTime++;
		}
		
		
	}
	
	/**
	 * Adds processes to the ready queue when they arrive
	 * @param aTime the arrival time
	 */
	public void addToReadyQueue(int aTime){
		boolean keepAdding = true;
		while(keepAdding){
			if(!arrival.isEmpty() && arrival.get(0).arrivalTime <= aTime){
				readyQueue.add(arrival.get(0));
				arrival.remove(0);
					
			}
			else{
				keepAdding = false;
			}
		}
	}
	
	/**
	 * Runs a particular process in one quantum
	 * @param currP the process to be executed
	 * @param timeSlice the time at which it is executed
	 */
	public void runOneQuantum(Process currP, int timeSlice, boolean addMore){
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
			
			readyQueue.add(0, currP);//add it to the start of the list since it is nonpreemptive.
				
			
		}
		else{
			currP.turnaroundTime=timeSlice + currP.remainTime - currP.arrivalTime;
			currP.remainTime = 0;
			finishedList.add(currP);
			
			//Sort readyQueue by shortest remaining time:
			Collections.sort(readyQueue, Process.comparatorByRemainTime());
		}
		
		
	}
	
	public List<Process> getFinishedList(){
		Collections.sort(finishedList);
		return finishedList;
	}

	@Override
	int getTotalRuntime() {
		
		return totalTime - 1;
	}
	
	
}
