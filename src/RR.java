import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class RR {

	private List<Process> arrival;
	private List<Process> finishedList;
	private Queue<Process> readyQueue; 
	Process currP;
	
	public RR(List<Process> arrivalList){
		readyQueue =new LinkedList<Process>();
		finishedList = new ArrayList<Process>();
		arrival=arrivalList;
		Collections.sort(arrivalList); //sort by arrival time
	}
	
	public void run(){
		System.out.println("Timeline:");
		int i = 0;
		for(; i<Process.MAX_QUANTA ; i++){
			addToReadyQueue(i);
			//Process currP;
			if(!readyQueue.isEmpty()){
				currP = readyQueue.remove();
				for(Process p: readyQueue){
					p.waitingTime += 1;
							
				}
				runOneQuantum(currP, i);
				
				
				System.out.println(i + ": " + "P"+ (currP.id + 1)); 
			}
			else{
				System.out.println(i + ": -");
			}
			
			
			
			
		}
		
		//handle unfinished processes
		int processOvertime = Process.MAX_QUANTA;
		
		//get rid of processes that haven't started yet (run time = remaining time)
		Queue<Process> readyQueue1 = new LinkedList<Process>();
		for(Process p: readyQueue){
			if(p.runTime != p.remainTime){
				readyQueue1.add(p);
			}
		}
		while(!readyQueue1.isEmpty()){
			currP = readyQueue1.remove();
			if(currP.remainTime - 1 > 0){
				currP.remainTime = currP.remainTime - 1;
				readyQueue1.add(currP);
			}
			else{
				currP.turnaroundTime=processOvertime + currP.remainTime - currP.arrivalTime;
				currP.remainTime = 0;
				finishedList.add(currP);
			}
			System.out.println(processOvertime + ": " + "P"+ (currP.id + 1)); 
			processOvertime++;
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
	public void runOneQuantum(Process currP, int timeSlice){
		if(currP.remainTime - 1 > 0){
			currP.remainTime = currP.remainTime - 1;
			
			if(!currP.runFlag){
				//the first time to run
				currP.runFlag=true;
				currP.waitingTime=timeSlice- currP.arrivalTime;
				currP.responseTime=currP.waitingTime;
			}
			
			addToReadyQueue(timeSlice + 1); //check if any arrived in between run
			readyQueue.add(currP);
		}
		else{
			currP.turnaroundTime=timeSlice + currP.remainTime - currP.arrivalTime;
			currP.remainTime = 0;
			finishedList.add(currP);
		}
	}
	
	public List<Process> getFinishedList(){
		Collections.sort(finishedList);
		return finishedList;
	}
	
	
}
