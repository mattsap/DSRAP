import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class OptimalScheduler extends Scheduler{

	public OptimalScheduler(ArrayList<Task> tasks, int maxScheduledTime) {
		super(tasks, maxScheduledTime);
	}
	

	public Schedule schedulerSchedule(ScheduleEvaluator evaluator){
		if(allPossibleSchedules.size() == 0){

			System.out.println("no schedules");
			int[] emptySchedule = new int[super.scheduleTime+1];
			for(int i = 0; i < emptySchedule.length; i++){
				emptySchedule[i] = -1;
			}
			Schedule empty = new Schedule(emptySchedule, super.consideredTasks);
			return empty;
		}
		Schedule optimalSchedule = allPossibleSchedules.get(0);
		double optimalScheduleValue = evaluator.evaluate(consideredTasks, optimalSchedule);
		for(Schedule currentSchedule: allPossibleSchedules){
			double currentScheduleValue = evaluator.evaluate(consideredTasks, currentSchedule);
			if(currentScheduleValue > optimalScheduleValue){
				optimalSchedule = currentSchedule;
				optimalScheduleValue = currentScheduleValue;
			}
		}
		
		return optimalSchedule;
	}
	//NOTE: NEED TO CONSIDER 
	public void setSchedules(int[] schedule, ArrayList<Task> tasksToBeScheduled, ArrayList<Task> tasksAlreadyScheduled, int currentTime) throws Error {
		try{
		// if no more tasks to are available to assign	
			if (currentTime == schedule.length) {

					Schedule newFinalSchedule = new Schedule(schedule,
							tasksAlreadyScheduled);
					if (allPossibleSchedules.contains(newFinalSchedule) == false) {// don't
																					// allow
																					// duplicates!
						
						allPossibleSchedules.add(newFinalSchedule);
					}
				} else {// if more tasks exist to be assigned
					for (Task currentTask : tasksToBeScheduled) {
						if (currentTask.timeRemaining > 0 && fits(currentTask, currentTime, schedule)) {
							Schedule newFinalSchedule = new Schedule(schedule,
									tasksAlreadyScheduled);
							if (allPossibleSchedules.contains(newFinalSchedule) == false) {// don't
								allPossibleSchedules.add(newFinalSchedule);
							}

							// assigns currentTask from time t, to t + j to the running
							// schedule

							// comment out for loop for interuptable tasks
							
						for (int i = currentTime; i < currentTime + currentTask.getDuration(); i++) {
								schedule[i] = currentTask.getID();
								currentTask.decrementTimeRemaining();
							}
							
							//schedule[currentTime] = currentTask.getID();
							//currentTask.decrementTimeRemaining();

							ArrayList<Task> moreTasksScheduled = new ArrayList<Task>();
							ArrayList<Task> TaskSubset = new ArrayList<Task>();
							moreTasksScheduled.addAll(tasksAlreadyScheduled);
							TaskSubset.addAll(tasksToBeScheduled);

							if (currentTask.timeRemaining == 0) {
								//TaskSubset.remove(currentTask);
								moreTasksScheduled.add(currentTask);
							}
							
							setSchedules(schedule, TaskSubset, moreTasksScheduled,
									currentTime + currentTask.getDuration()); //change to currentTime + 1 for interruptable schedules
							for (int i = currentTime; i < currentTime + currentTask.getDuration(); i++) {
								schedule[i] = -1;
								currentTask.incrementTimeRemaining();
							}
							/*
							setSchedules(schedule, TaskSubset, moreTasksScheduled,
									currentTime + 1); //change to currentTime + 1 for interruptable schedules
						
							 schedule[currentTime] = -1;
							 currentTask.incrementTimeRemaining();
		*/
						}
					}
					int[] newSchedule = schedule.clone();
					newSchedule[currentTime] = -1;
					setSchedules(newSchedule, tasksToBeScheduled,
							tasksAlreadyScheduled, currentTime + 1);
				}
		}
		catch(Error e){
			
			throw e;
		}
	}
	//fits if the all the spaces from currentTime to currentTime + duration is -1. otherwise doesn't fit
	public boolean fits(Task currentTask, int currentTime, int[] currentSchedule){
		return super.fits(currentTask, currentTime, currentSchedule);
	}


	@Override
	public String getScheduleType() {
		// TODO Auto-generated method stub
		return "OptimalSchedules";
	}
	
	public static void writeAllPossibleSchedulesToFile(Scheduler scheduler, int taskSetID, String taskSetDirectory) {
		
		if (scheduler.allPossibleSchedules.size() != 0) {
			try {
				PrintWriter out = new PrintWriter(taskSetDirectory // +
																	// schedules.getScheduleType()
						+ "\\AllPossibleSchedules\\size"
						+ scheduler.tasks.size()
						+ "\\"
						+ taskSetID + "schedules" + scheduler.tasks.size() + ".txt");
				out.println("schedules:");

				String time = "t:";
				for (int i = 0; i < scheduler.scheduleTime; i++) {
					time += "  " + i % 10;
				}
				out.println("  " + time);
				out.println("-------------------------------");
				for (Schedule p : scheduler.allPossibleSchedules) {
					out.println("   |" + p);
				}
				out.close();
			} catch (IOException e) {
				System.out.println("could not write results for: "
						+ taskSetID);
				e.printStackTrace();
			}
		}
	}

}
