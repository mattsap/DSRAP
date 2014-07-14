import java.util.ArrayList;
import java.util.TreeMap;

public class Schedule {
	// assignment: an array of taskID's for the agent to execute
	private int[] assignment;
	private ArrayList<Task> consideredTasks;
	private ArrayList<Task> scheduledTasks;
	
	/**
	 * 
	 * @param assignment
	 *            an int array which represents a task's id (int) scheduled
	 *            execution time (place in the array).
	 * @param scheduledTasks
	 *            tasks that were considered to be executed within the assigned
	 *            time box. (Note: superset of what was actually scheduled)
	 */
	public Schedule(int[] assignment, ArrayList<Task> consideredTasks) {
		this.assignment = assignment.clone();
		this.consideredTasks = new ArrayList<Task>();
		this.consideredTasks.addAll(consideredTasks);
		this.scheduledTasks = new ArrayList<Task>();
		int currentTaskID = -1;

		for (int i = 0; i < assignment.length; i++) {
			if (assignment[i] != -1 && currentTaskID != assignment[i]) {
				Task currentTask = getTaskWithID(assignment[i]);
				if (currentTask != null && scheduledTasks.contains(currentTask)) {
					scheduledTasks.add(currentTask);
				}
			}
		}
	}

	/**
	 * @deprecated Could be changed to more efficient look up (possibly TreeMap?)
	 * @param taskID the tasks id of the task
	 * @return the task with the corresponding taskID of all considered tasks. 
	 */
	public Task getTaskWithID(int taskID) {
		for (int i = 0; i < consideredTasks.size(); i++) {
			Task currentTask = consideredTasks.get(i);
			if (currentTask.getID() == taskID) {
				return currentTask;
			}
		}
		System.out.println("Schedule.java;getTaskWithID- No such id exists: "
				+ taskID);
		return null;
	}

	/*
	 * public Task getTask(int taskID){ if(taskID == -1){ return null;} else{
	 * return scheduledTasks.get((Integer) taskID);} }
	 */
	public ArrayList<Task> getScheduledTasks() {
		return scheduledTasks;
	}

	public ArrayList<Task> getConsideredTasks() {
		return consideredTasks;
	}
	public int[] getAssignments() {
		return assignment;
	}
/**
 * @return a string in the format "TaskID3,TaskID1, , ,TaskID4,..." where the taskID's (spaces if no task is assigned) with respect to their time slots are separated by comma's.
 */
	public String toString() {
		String result = "";
		for (int i = 0; i < assignment.length; i++) {
			String s = "  ";
			if (assignment[i] >= 0) {
				s = assignment[i] + ",";
			} else {
				s = " " + ",";
			}
			result += s;
		}
		return result;
	}

	/**
	 * A Schedule equals another schedule when all assignments are equal: That is when assignment[1] ==...== assigment[i] ==assignment[n]
	 */
	public boolean equals(Object o) {
		Schedule other = (Schedule) o;
		for (int i = 0; i < assignment.length; i++) {
			if (assignment[i] != other.assignment[i]) {
				return false;
			}
		}
		return true;
	}

}
