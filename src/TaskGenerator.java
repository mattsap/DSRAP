import java.util.ArrayList;
import java.util.Random;
public class TaskGenerator {

	/*
	 * Max, min average load scheduleability: start, deadline, duration, max
	 * span, predictability: start time, delta time, Time Pressure: latest start
	 * time
	 */
	public static int random(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public static void generateTasks(int numberOfTasks, int endTime, String outputDirectory) {
		ArrayList<Task> tasks = new ArrayList<Task>();
		Random rand = new Random();
		
		int deadline = 1; // place holder
		int arrivalTime = 1; // place holder
		TaskType type = new TaskType();
		int taskSetID = 0;
		int flexibilityRepetitions = 200; // varying overlap
		
		for (int i = 0; i < numberOfTasks; i++) {
			int taskDuration = rand.nextInt(endTime / 3) + 1;
			double rewardRate = rand.nextDouble()* 10.0 + 1.0;
			int timeRemaining = taskDuration;
			tasks.add(new Task(taskDuration, timeRemaining, rewardRate,
					deadline, arrivalTime, type));
		}
		// uniform distributions of flexibility
		for (int flexibility = 2; flexibility < 10; flexibility += 2) {
			for (int i = 0; i < flexibilityRepetitions; i++) {
				for (Task currentTask : tasks) {
					
					int thisTaskStartTime = random(0, endTime - flexibility
							- currentTask.getDuration() - 1);
					currentTask.setArrivalTime(thisTaskStartTime);
					currentTask.setDeadline(thisTaskStartTime + currentTask.getDuration()
							+ flexibility);
				}
				TaskSet currentTaskSet = new TaskSet(tasks);
				currentTaskSet.writeToFileForTaskSetGeneration(taskSetID, endTime, outputDirectory + "\\taskSetsTrain"+numberOfTasks+".txt");
				currentTaskSet.writeVisualToFile(outputDirectory+"\\size"+numberOfTasks+"\\visual"+currentTaskSet.getTaskSetID()+".txt", numberOfTasks);
				taskSetID++;
			}
		}

		// gaussian distributions of flexibility
		for (int peak = 2; peak < 10; peak++) {
			for (int std = 2; std < 5; std++) {
				double[] distribution = gaussianDistribution(peak, std,
						tasks.size());
				for (int repetition = 0; repetition < flexibilityRepetitions; repetition++) {
					for (int i = 0; i < distribution.length; i++) {

						int thisTaskStartTime = random(0,
								(int) (endTime - distribution[i])
										- tasks.get(i).getDuration() - 1);
						tasks.get(i).setArrivalTime(thisTaskStartTime);
						tasks.get(i)
								.setDeadline(
										(int) (thisTaskStartTime + tasks.get(i).getDuration() + distribution[i]));
					}
					TaskSet currentTaskSet = new TaskSet(tasks);
					
					currentTaskSet.writeToFileForTaskSetGeneration(taskSetID, endTime, outputDirectory + "\\taskSetsTrain"+numberOfTasks+".txt");
					currentTaskSet.writeVisualToFile(outputDirectory+"\\size"+numberOfTasks+"\\visual"+currentTaskSet.getTaskSetID()+".txt", numberOfTasks);
					
					taskSetID++;
				}
			}
		}
		System.out.println("finished generating task set file");
	}

	public static double[] gaussianDistribution(double peak,
			double standardDev, int endTime) {
		double[] distribution = new double[endTime];
		int center = endTime / 2;
		for (int i = 0; i < endTime; i++) {
			distribution[i] = peak
					* Math.exp(-1 * (i - center) * (i - center)
							/ (2 * standardDev * standardDev));
		}
		return distribution;
	}

}
