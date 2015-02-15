import java.util.Comparator;
import java.util.Random;



public class Process implements Comparable<Process>{
	public static final int MAX_QUANTA=100;
	
	private static Random randomGenerator;
	int id;  //the order index by arrival time
	float arrivalTime;
	float runTime;
	int priority;
	float remainTime; //the remain running time 
	float waitingTime;
	float turnaroundTime; //the time in ready list to completed execute
	float responseTime; //the time in ready list to first response
	boolean runFlag=false; //true: the process have executed
	int ageCount = 0;
	
	/**
	 * Compare two Processes by arrival time
	 * @param other The other process
	 * @return -1, if the arrival time less than other;
	 * 			1, if the arrival time large than other;
	 * 			0, equal
	 */
	public int compareTo(Process other){
		if(arrivalTime<other.arrivalTime)
			return -1;
		if(arrivalTime>other.arrivalTime)
			return 1;
		return 0;
	}
	
	/**
	 * Compare two Processes by remain time
	 * @return -1 p1<p2; 0 p1=p2; 1 p1>p2
	 */
	public static Comparator<Process> comparatorByRemainTime(){
		return new Comparator<Process>(){
			public int compare(Process p1, Process p2){
				if(p1.remainTime<p2.remainTime)
					return -1;
				if(p1.remainTime>p2.remainTime)
					return 1;
				return 0;
			}
			
		};
	}


	/**
	 * Compare two Processes by Priority
	 * @return negative p1<p2; 0 p1=p2; positive p1>p2
	 */
	public static Comparator<Process> comparatorByPriority(){
		return new Comparator<Process>(){
			public int compare(Process p1, Process p2){
				return p2.priority-p1.priority;
			}
		};
	}

	/**
	 * Display some message in the process
	 */
	public void display(){
		System.out.println("P"+(id+1)+"  Arrival Time: "+arrivalTime+"  Run Time: "+runTime+
				"  Priority: "+priority);
		//System.out.println(""+id+" remain time: "+ remainTime+" waiting time: "+waitingTime+" turnaround time: "+turnaroundTime+" response time: "+responseTime);
	}
	
	/**
	 * Make a new process
	 * @return a new process object
	 */
	public static Process make(){
		if(randomGenerator==null){
			randomGenerator=new Random();
		}
		Process p=new Process();
		p.arrivalTime=(float)(randomGenerator.nextInt(991)/10.0);
		p.priority=randomGenerator.nextInt(4)+1;
		p.runTime=(float)(randomGenerator.nextInt(100)/10.0+0.1);
		p.remainTime=p.runTime;
		return p;
	}
	
	/**
	 * set the random seek, only use for debug
	 * @param l
	 */
	public static void setRandomSeek(long l){
		randomGenerator=new Random(l);
	}
}