import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomScheduler extends Scheduler {

	public RandomScheduler(ArrayList<Task> itasks, int maxScheduledTime) {
		super(itasks, maxScheduledTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	// add tasks to a queue by arrival time
	public void setSchedules(int[] schedule,
			ArrayList<Task> tasksToBeScheduled,
			ArrayList<Task> tasksAlreadyScheduled, int currentTime) {

		ArrayList<Task> knownPossibleTasks = new ArrayList<Task>();

		while (currentTime < schedule.length) {
			for (int i = 0; i < tasksToBeScheduled.size(); i++) {
				if (currentTime == tasksToBeScheduled.get(i).getArrivalTime()) {
					knownPossibleTasks.add(tasksToBeScheduled.get(i));
					tasksToBeScheduled.remove(i);
					i--;
				}
			}
			// filter by if tasks on queue are too late or if it's out of scope
			for (int i = 0; i < knownPossibleTasks.size(); i++) {
				if (knownPossibleTasks.get(i).getDeadline()
						- knownPossibleTasks.get(i).getDuration() + 2 <= currentTime
						|| knownPossibleTasks.get(i).getDuration() + currentTime - 1 >= schedule.length) {
					knownPossibleTasks.remove(i);
					i--;
				}
			}
			
			if (knownPossibleTasks.size() == 0) {
				schedule[currentTime] = -1;
				currentTime++;
			} else { // task exists and fits

				Random r = new Random();
				int randomTaskIndex = r.nextInt(knownPossibleTasks.size());
				
				Task currentTask = knownPossibleTasks.remove(randomTaskIndex);
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

	@Override
	public String getScheduleType() {
		// TODO Auto-generated method stub
		return "Random";
	}

	@Override
	public Schedule schedulerSchedule(ScheduleEvaluator se) {
		// TODO Auto-generated method stub
		return super.allPossibleSchedules.get(0);
	}

}
