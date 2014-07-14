import java.util.ArrayList;

public class IncrementalUtilityEvaluator implements ScheduleEvaluator {
	
	@Override
	/** 
	 * The score consists of the reward rate * time working on a task.
	 * @return double that represents the score of the schedule. 
	 */
	public double evaluate(Schedule aSchedule) {
		//return variable
		double scheduledNetUtility = 0;
		
		ArrayList<Task> tasksConsidered = aSchedule.getConsideredTasks();
		int[] assignments = aSchedule.getAssignments();
		boolean changedCurrentTask = true;
		Task currentTask = null;
		int currentTaskID = -1;
		
		//iterate over tasks:
		// 1) access the task being assigned at the time slot t, (store in a variable, currentTask, to reduce memory accesses)
		// 2) increment utility by the reward of the task
		for (int t = 0; t < assignments.length; t++) {
			changedCurrentTask = currentTaskID != assignments[t];
			currentTaskID = assignments[t];
			
			// 1) access the task being assigned at the time slot t, (store in a variable, currentTask, to reduce memory accesses)
			if (changedCurrentTask == true) {
				for (Task ta : tasksConsidered) {
					if (currentTaskID == ta.getID()) {
						currentTask = ta;
						break;
					}
				}
			}
			// 2) increment utility by the reward of the task
			if(currentTaskID != -1){
				scheduledNetUtility += currentTask.getRewardRate();
			}
		}

		return scheduledNetUtility;
	}

}
