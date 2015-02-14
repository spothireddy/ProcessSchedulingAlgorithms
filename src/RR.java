import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class RR {

	private List<Process> arrival;
	private Queue<Process> readyQueue; 
	
	public RR(List<Process> arrivalList){
		readyQueue =new LinkedList<Process>();
		arrival=arrivalList;
		Collections.sort(arrivalList); //sort by arrival time
	}
	
	public void run(){
		System.out.println("Timeline:");
		int i = 0;
		for(; i<Process.MAX_QUANTA ; i++){
			addToReadyQueue(i);
			Process currP;
			if(!readyQueue.isEmpty()){
				currP = readyQueue.remove();
				runOneQuantum(currP, i);
				
				
				System.out.println(i + ": " + "P"+ (currP.id + 1)); 
			}
			else{
				System.out.println(i + ": ");
			}
			
			
			
			
		}
	}
	
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
	
	public void runOneQuantum(Process currP, int timeSlice){
		if(currP.remainTime - 1 >= 0){
			currP.remainTime = currP.remainTime - 1;
			addToReadyQueue(timeSlice + 1); //check if any arrived in between run
			readyQueue.add(currP);
		}
		else{
			currP.remainTime = 0;
		}
	}
	
	
}
