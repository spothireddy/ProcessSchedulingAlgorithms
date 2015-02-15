import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SRT extends ProcessAlgorithm{
	private List<Process> arrival;
	private ArrayList<Process> ready;
	
	public SRT(List<Process> arrivalList){
		ready=new ArrayList<Process>();
		arrival=arrivalList;
		Collections.sort(arrivalList); //sort by arrival time
	}
	
	@Override
	public void runAlgorithm(){
		int processId=0; //it equals the process index in arrival, begin with 0
		//System.out.println("Timeline:");
		int i=0;
		for(; i<Process.MAX_QUANTA ; i++){
			if(i<Process.MAX_QUANTA && processId<arrival.size()){
				Process p=arrival.get(processId); //get the process which index=processId
				//put the arrival processes into the ready list
				boolean isSorted=true;
				while(p.arrivalTime<=i){
					//arrived
					p.id=processId; //save the process id into the process
					ready.add(p);
					processId++; //move to next process in arrival list
					if(processId<arrival.size())
						p=arrival.get(processId);
					else
						break;
					isSorted=false;
				}
				if(!isSorted){
					Collections.sort(ready, Process.comparatorByRemainTime());
				}
			}
				
				//execute the ready process
				boolean isFinished=false; //a flag to indicate the status of running process
				boolean isCPUIdle=true;
				for(int index=0; index<ready.size(); index++){
					Process p2=ready.get(index);
					if(index==0){
						//the executing process
						isCPUIdle=false;
						System.out.print("P"+(p2.id+1));
						if(!p2.runFlag){
							//the first time to run
							p2.runFlag=true;
							p2.waitingTime=i-p2.arrivalTime;
							p2.responseTime=p2.waitingTime;
						}
						if(p2.remainTime>1){
							p2.remainTime-=1;
						}
						else{
							p2.turnaroundTime=i+p2.remainTime-p2.arrivalTime;
							p2.remainTime=0;
							isFinished=true; //set the finish flag
						}
						
					}
					else{
						//the waiting processes
						if(p2.runFlag)
							p2.waitingTime+=1;
					}
					ready.set(index, p2); //save the changed process back to ready list
				}
				if(isCPUIdle)
					System.out.print("-");
				if(isFinished){
					//the process finish, remove it from ready list
					Process p2=ready.remove(0);
					arrival.set(p2.id, p2); //update the process in arrival list
				}
		}
		//remove the processes which have not begin yet in ready list
		int index=0;
		while(index<ready.size()){
			Process p1=ready.get(index);
			if(p1.runFlag)
				index++; //skip it
			else
				ready.remove(index);	
		}
		
		//handle the unfinished processes
		for(;ready.size()>0;i++){
			boolean isFinished=false;
			for(index=0; index<ready.size(); index++){
				Process p2=ready.get(index);
				if(index==0){
					//the executing process
					System.out.print("P"+(p2.id+1));
					if(p2.remainTime>1){
						p2.remainTime-=1;
					}
					else{
						p2.turnaroundTime=i+p2.remainTime-p2.arrivalTime;
						p2.remainTime=0;
						isFinished=true; //set the finish flag
					}
					
				}
				else{
					//the waiting processes
					if(p2.runFlag)
						p2.waitingTime+=1;
				}
				ready.set(index, p2); //save the changed process back to ready list
			}
			
			if(isFinished){
				//the process finish, remove it from ready list
				Process p2=ready.remove(0);
				arrival.set(p2.id, p2); //update the process in arrival list
			}
		}
	}

	@Override
	List<Process> getFinishedList() {
		List<Process> finishedList = new ArrayList<Process>();
		for(Process p: arrival){
			if(p.remainTime == 0){
				finishedList.add(p);
			}
		}
		return finishedList;
	}

	@Override
	List<Process> returnArrival() {
		return arrival;
	}
}