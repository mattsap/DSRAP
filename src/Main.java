import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * sensor quality: percieve existing tasks in environment. For example,
		 * in lesser's paper, everyone can't perceieve all the tasks, only
		 * perceieve what's given to them Predictions quality: Ability to
		 * predict task characteristics in future Schedule quality: Ability to
		 * assign an optimal schedule Actuator Quality: Ability to perform Tasks
		 * and prereqs of tasks (if geospatial) ie task throughput?
		 * 
		 * Environment variables: task distribution, task types. described in
		 * lesser's paper.
		 */

		int maxScheduledTime = 35;
		int numberOfTasks = 10;
		String taskSetDirectory = "C:\\Users\\Matthew\\Dropbox\\School\\Research\\DSRAPResources";
		String taskSetFileName = taskSetDirectory + "\\taskSetsTrain"
				+ numberOfTasks + ".txt";
		
		// TaskGenerator.generateTasks(numberOfTasks,maxScheduledTime,taskSetFileName);
		int count = 0;
		try {
			String currentTaskSetLine;
			BufferedReader taskSetDirectoryFile = new BufferedReader(
					new FileReader(taskSetFileName));
			ScheduleEvaluator scheduleEvaluator = new IncrementalUtilityEvaluator();
			while ((currentTaskSetLine = taskSetDirectoryFile.readLine()) != null) {
				
				Scheduler scheduler = new OptimalOnlineScheduler(TaskSet.getTasksFromString(currentTaskSetLine), maxScheduledTime);
				
				if(scheduler.getScheduleType().equals("OptimalSchedules")){
					OptimalScheduler.writeAllPossibleSchedulesToFile(scheduler, TaskSet.getTaskSetIDFromString(currentTaskSetLine), taskSetDirectory);
				}
				
				TaskSet currentTaskSet = new TaskSet(currentTaskSetLine,
						taskSetDirectory, scheduleEvaluator, true);
				
					
				
				currentTaskSet.writeStatisticsToFile(taskSetDirectory
						+ "\\"+scheduler.getScheduleType()+"_TotalStats"+numberOfTasks+".csv", scheduler.schedulerSchedule(scheduleEvaluator));
				
			}
		} catch (IOException e) {
			System.out.println("could not read file " + taskSetFileName);
			e.printStackTrace();
		}
		
		System.out.println("system exit");
	}

}
