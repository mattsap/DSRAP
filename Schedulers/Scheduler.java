import java.util.ArrayList;

public abstract class Scheduler {
	public int depth = 0;
	public int nodes = 0;
	public ArrayList<Schedule> allPossibleSchedules;
	public ArrayList<Task> consideredTasks;
	public ArrayList<Task> tasks;
	protected int scheduleTime = 0;

	public Scheduler(ArrayList<Task> itasks, int maxScheduledTime) {
		// assigns task to a start time.
		// saves memory/time on smart assignment--considers tasks that a
		scheduleTime = maxScheduledTime;
		allPossibleSchedules = new ArrayList<Schedule>();
		ArrayList<Task> noScheduledTasks = new ArrayList<Task>();
		tasks = new ArrayList<Task>();
		tasks.addAll(itasks);

		// Initialize an Empty schedule
		int[] schedule = new int[maxScheduledTime + 1];
		for (int t = 0; t < maxScheduledTime + 1; t++) {
			schedule[t] = -1;
		}
		try{
		setSchedules(schedule, tasks, noScheduledTasks, 0);
		}
		catch(Error e){
			while(allPossibleSchedules.size() > 0){ // free up all memory, solution couldn't be solved
				allPossibleSchedules.remove(0);
			}
		}
		consideredTasks = tasks;
	}

	public abstract String getScheduleType();

	public abstract Schedule schedulerSchedule(ScheduleEvaluator se);

	public abstract void setSchedules(int[] schedule,
			ArrayList<Task> tasksToBeScheduled,
			ArrayList<Task> tasksAlreadyScheduled, int currentTime) throws Error;

	// fits if the all the spaces from currentTime to currentTime + duration is
	// -1. otherwise doesn't fit
	public boolean fits(Task currentTask, int currentTime, int[] currentSchedule) {
		// return currentTime < currentSchedule.length &&
		// currentSchedule[currentTime] == -1 && currentTime >=
		// currentTask.getArrivalTime() && currentTask.getDeadline() >=
		// currentTime;
		for (int i = currentTime; i < currentTime + currentTask.getDuration(); i++) {
			if (i < currentSchedule.length && currentSchedule[i] != -1) {
				return false;
			}
		}

		return currentTime >= currentTask.getArrivalTime()
				&& currentTime + currentTask.getDuration() - 1 <= currentTask
						.getDeadline()
				&& currentTime + currentTask.getDuration() - 1 < currentSchedule.length;
	}

}
