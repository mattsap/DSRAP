import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
	
public class Experiment {
	public int ticks;
	public int maxTicks;
	public ArrayList<Agent> agents;
	public ArrayList<Task> tasks;
	
	public Experiment(){
		ticks = 0;
		maxTicks = 100;	
		
		/*
		String currentTaskSetLine;
		BufferedReader taskSetDirectoryFile = new BufferedReader(
				new FileReader(taskSetFileName));
		ScheduleEvaluator scheduleEvaluator = new IncrementalUtilityEvaluator();
		while ((currentTaskSetLine = taskSetDirectoryFile.readLine()) != null) {
				
				TaskSet currentTaskSet = new TaskSet(currentTaskSetLine);
				System.out.println(currentTaskSet.getTaskSetID());
				currentTaskSet.writeVisualToFile(taskSetDirectory,
						numberOfTasks);

				Scheduler schedules = new OptimalScheduler(
						currentTaskSet.getTasks(),
						currentTaskSet.getEndTime());
				System.out.println("finished finding all schedules");
				Schedule currentTaskSetOptimalSchedule = schedules
						.schedulerSchedule(scheduleEvaluator);
				System.out.println("finished finding optimal schedule");


*/
	}
	public void run(){
		while(this.ticks < maxTicks){
			step();
			this.ticks++;
		}
	}
	public void step(){
		for(Agent currentAgent: agents){
			currentAgent.act(ticks);
		}
	}
}
