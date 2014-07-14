import java.util.ArrayList;
import java.util.TreeMap;


public class Agent {
	public static int countAgents = 0;
	public int id;
	protected TreeMap<Integer, Belief> beliefs;
	protected TreeMap<Integer, Prediction> predictions;
	protected ArrayList<TaskType> capabilities;
	
	protected ScheduleEvaluator evaluator;
	protected Schedule schedule;
	protected int[] actualSchedule;
	protected double utility;
	
	//protected int numAllocatableTasks;
	/**
	 * @deprecated Replaced In future versions to accommodate multi agent coordination mechanism
	 * @param e
	 */
	public Agent(ScheduleEvaluator e){
		id = countAgents;
		beliefs = new TreeMap<Integer, Belief>();
		predictions = new TreeMap<Integer, Prediction>();
		capabilities = new ArrayList<TaskType>();
		
		evaluator = e;
		utility = 0;
		countAgents++;
	}
	public void setSchedule(Schedule schedule){
		this.schedule = schedule;
	}
	/*
	public void act(int ticks){
		Task currentTask = schedule.getTask(ticks);
		if(currentTask != null){
			this.addUtility(currentTask.getRewardRate());
		}
	}
	*/
	public void addBelief(Belief b){
		beliefs.put(b.getID(),b);
	}
	public void addPrediction(Prediction p){
		predictions.put(p.getID(), p);
	}
	/*
	public void incrementAllocatable(){
		numAllocatableTasks++;
	}
	public void decrementAllocatable(){
		if (numAllocatableTasks == 0){
			System.out.println("ERROR CAN'T DECREMENT ALLOCATABLE TASKS");
			System.exit(0);
		}
		numAllocatableTasks--;
	}
	*/
	public void addUtility(double u){
		utility += u;
	}
	public Belief getBelief(int beliefID){
		return beliefs.get((Integer) beliefID);
		
	}
	public Prediction getPrediction(int predictionID){
		return predictions.get((Integer) predictionID);
	}
}

