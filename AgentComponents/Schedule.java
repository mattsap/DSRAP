import java.util.ArrayList;
import java.util.TreeMap;
public class Schedule{
	//assignment: an array of taskID's for the agent to execute
	private int[] assignment;
	private ArrayList<Task> scheduledTasks;
	private double evaluation;
	public Schedule(int[] a, ArrayList<Task> st){
		assignment = a.clone();
		scheduledTasks  = new ArrayList<Task>();
		scheduledTasks.addAll(st);
	}
	public Schedule(int[] a, ArrayList<Task> st, ScheduleEvaluator evaluator){
		assignment = a.clone();
		
		scheduledTasks  = new ArrayList<Task>();
		scheduledTasks.addAll(st);
		
		evaluation = evaluator.evaluate(st, this);
	}
	public double getScore(ScheduleEvaluator evaluator){
		evaluation = evaluator.evaluate(scheduledTasks, this);
		return evaluation;
	}
	public Task getTask(int taskID){
		if(taskID == -1){ return null;}
		else{
		return scheduledTasks.get((Integer) taskID);}
	}
	public String toString(){
		String result = "";
		for(int i = 0; i < assignment.length; i++){
			String s = "  ";
			if (assignment[i] >= 0){
				s = assignment[i] + ",";
			}
			else{
				s = " " + ",";
			}
			result +=  s;
		}
		return result;
	}
	public int[] getAssignments(){
		return assignment;
	}
	
	public boolean equals(Object o){
		Schedule other = (Schedule) o;
		for(int i = 0; i < assignment.length; i++){
			if(assignment[i] != other.assignment[i]){
				return false;
			}
		}
		return true;
	}

}
