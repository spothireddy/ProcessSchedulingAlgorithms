import java.util.List;


public abstract class ProcessAlgorithm {
	protected List<Process> arrival;
	/**
	 * reutrn initial processes
	 * @return the process list that is passed when object is initialized
	 */
	abstract List<Process> returnArrival(); 
	/**
	 * Run the algorithm
	 */
	abstract void run(); 
	/**
	 * Get the list of processes that were completed
	 * @return list of all processes that were completed
	 */
	abstract List<Process> getFinishedList(); //outputs the list of processes that finished
}
