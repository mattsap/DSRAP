import java.util.ArrayList;

public class OptimalOnlineScheduler extends Scheduler {
	public OptimalOnlineScheduler(ArrayList<Task> itasks, int maxScheduledTime) {
		super(itasks, maxScheduledTime);
		// TODO Auto-generated constructor stub
	}

	public Schedule schedulerSchedule(ScheduleEvaluator evaluator) {
		//System.out.println(allPossibleSchedules);
		if (super.allPossibleSchedules.size() == 0) {

			System.out.println("no schedules");
			int[] emptySchedule = new int[super.scheduleTime + 1];
			for (int i = 0; i < emptySchedule.length; i++) {
				emptySchedule[i] = -1;
			}
			Schedule empty = new Schedule(emptySchedule, super.consideredTasks);
			return empty;
		}
		Schedule optimalSchedule = super.allPossibleSchedules.get(0);
		double optimalScheduleValue = evaluator.evaluate(//CHanged: super.consideredTasks,
				optimalSchedule);
		for (Schedule currentSchedule : super.allPossibleSchedules) {
			double currentScheduleValue = evaluator.evaluate(//changed: super.consideredTasks,
					currentSchedule);
			if (currentScheduleValue > optimalScheduleValue) {
				optimalSchedule = currentSchedule;
				optimalScheduleValue = currentScheduleValue;
			}
		}
		//System.out.println("OS"+optimalSchedule);
		return optimalSchedule;
	}

	@Override
	public void setSchedules(int[] schedule,
			ArrayList<Task> tasksToBeScheduled,
			ArrayList<Task> tasksAlreadyScheduled, int currentTime)
			throws Error {

		try {
			
			setOnlineSchedules(schedule, tasksToBeScheduled,
					tasksAlreadyScheduled, currentTime);
		} catch (Error e) {
			throw e;
		}
	}

	public void setOnlineSchedules(int[] schedule,
			ArrayList<Task> tasksToBeScheduled,
			ArrayList<Task> tasksAlreadyScheduled, int currentTime)
			throws Error {
		try {
			// if no more tasks to are available to assign
			if (currentTime == schedule.length) {

				Schedule newFinalSchedule = new Schedule(schedule,
						tasksAlreadyScheduled);
				
				if (super.allPossibleSchedules.contains(newFinalSchedule) == false) {// don't
																				// allow
																				// duplicates!
					super.allPossibleSchedules.add(newFinalSchedule);
					
				}
			} else {// if more tasks exist to be assigned
			
				ArrayList<Task> knownPossibleTasks = new ArrayList<Task>();
				for (Task currentTask : tasksToBeScheduled) {
					if (tasksAlreadyScheduled.contains(currentTask) == false &&currentTime >= currentTask.getArrivalTime() && fits(currentTask, currentTime, schedule)) {
						knownPossibleTasks.add(currentTask);
						
					}
				}
				if (knownPossibleTasks.size() > 0) {
					for (Task currentTask : knownPossibleTasks) {
	
							// assigns currentTask from time t, to t + j to
							// the
							// running
							// schedule

							// comment out for loop for interuptable tasks

							for (int i = currentTime; i < currentTime
									+ currentTask.getDuration(); i++) {
								schedule[i] = currentTask.getID();
								currentTask.decrementTimeRemaining();
							}

							// schedule[currentTime] = currentTask.getID();
							// currentTask.decrementTimeRemaining();

							ArrayList<Task> moreTasksScheduled = new ArrayList<Task>();
							ArrayList<Task> TaskSubset = new ArrayList<Task>();
							moreTasksScheduled.addAll(tasksAlreadyScheduled);
							TaskSubset.addAll(tasksToBeScheduled);

							if (currentTask.timeRemaining == 0) {
								TaskSubset.remove(currentTask);
								moreTasksScheduled.add(currentTask);
							}

							setOnlineSchedules(schedule, TaskSubset,
									moreTasksScheduled, currentTime
											+ currentTask.getDuration()); // change
																			// to
							// currentTime +
							// 1
							// for
							// interruptable
							// schedules
							for (int i = currentTime; i < currentTime
									+ currentTask.getDuration(); i++) {
								schedule[i] = -1;
								currentTask.incrementTimeRemaining();
							}

						}
					
				} else {
					int[] newSchedule = schedule.clone();
					newSchedule[currentTime] = -1;
					setOnlineSchedules(newSchedule, tasksToBeScheduled,
							tasksAlreadyScheduled, currentTime + 1);
				}
			}
		} catch (Error e) {

			throw e;
		}
	}

	// fits if the all the spaces from currentTime to currentTime + duration is
	// -1. otherwise doesn't fit
	public boolean fits(Task currentTask, int currentTime, int[] currentSchedule) {
		return super.fits(currentTask, currentTime, currentSchedule);
	}

	@Override
	public String getScheduleType() {
		// TODO Auto-generated method stub
		return "OptimalOnlineSchedules";
	}
}
