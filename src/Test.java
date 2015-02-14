import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Test {
	
	public static void main(String[] args){
		List<Process> arrivalList=new LinkedList<Process>();
		Process.setRandomSeek(0);
		for(int i=0; i<10;i++){
			Process p=Process.make();
			arrivalList.add(p);
		}
		Collections.sort(arrivalList);
		for(int i=0; i<arrivalList.size();i++){
			//update the process id with its index in arrivalList
			//it may need for some algorithms
			Process p=arrivalList.get(i);
			p.id=i;
			arrivalList.set(i,p);
		}
		
		for(Process p:arrivalList){
			p.display();
		}
		
		RR a4 = new RR(arrivalList);
		a4.run();
		//SRT a3=new SRT(arrivalList);
		//a3.run();
	}

}