import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FirstTaskFirstScheduler extends Scheduler {

	public FirstTaskFirstScheduler(ArrayList<Task> tasks, int maxScheduledTime) {
		super(tasks, maxScheduledTime);
		// TODO Auto-generated constructor stub
	}
	//add tasks to a queue by arrival time
	public void setSchedules(int[] schedule,
			ArrayList<Task> tasksToBeScheduled,
			ArrayList<Task> tasksAlreadyScheduled, int currentTime) {
		
		Queue<Task> tasksByArrivalTime = new LinkedList<Task>();
		for (int t = 0; t < schedule.length; t++) {
			for (int taskIndex = 0; taskIndex < tasksToBeScheduled.size(); taskIndex++) {
				Task atask = tasksToBeScheduled.get(taskIndex);
				if (atask.getArrivalTime() == t) {
					tasksByArrivalTime.add(atask);
					tasksToBeScheduled.remove(taskIndex);
					taskIndex--;
				}
			}
		}
		
		while (currentTime < schedule.length) {
			
			//filter by if tasks on queue are too late or if it's out of scope
			while (tasksByArrivalTime.isEmpty() == false
					&& tasksByArrivalTime.peek().getArrivalTime() <= currentTime &&(tasksByArrivalTime.peek().getDeadline()
							- tasksByArrivalTime.peek().getDuration() + 2 <= currentTime || tasksByArrivalTime
							.peek().getDuration() + currentTime - 1 >= schedule.length)) {
				tasksByArrivalTime.remove();
			}
			
			if (tasksByArrivalTime.isEmpty() || tasksByArrivalTime.peek().getArrivalTime() > currentTime) {
				schedule[currentTime] = -1;
				currentTime++;
			} else { // task exists and fits

				Task currentTask = tasksByArrivalTime.poll();
				while (currentTask.getTimeRemaining() > 0) {
					schedule[currentTime] = currentTask.getID();
					currentTask.decrementTimeRemaining();
					currentTime++;
				}
				tasksAlreadyScheduled.add(currentTask);
			}

		}
		super.allPossibleSchedules.add(new Schedule(schedule,
				tasksAlreadyScheduled));
	}

	public boolean fits(Task currentTask, int currentTime, int[] currentSchedule) {
		return super.fits(currentTask, currentTime, currentSchedule);
	}

	@Override
	public String getScheduleType() {
		// TODO Auto-generated method stub
		return "FirstComeFirstSchedules";
	}

	@Override
	public Schedule schedulerSchedule(ScheduleEvaluator se) {
		// TODO Auto-generated method stub
		return super.allPossibleSchedules.get(0);
	}
}
