import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TaskSet {
	private ArrayList<Task> tasks;
	private ArrayList<Schedule> allPossibleSchedules;
	private int taskSetID;
	private int endTime;
	private double AverageFlexibility;
	private double AverageOverlap;
	private double[] scoreDistribution;
	private double optimalScore;
	private ScheduleEvaluator evaluator;
public static int getTaskSetIDFromString(String taskSetString){
	String[] taskSetCharacteristics = taskSetString.split(";");

	// taskSetCharacteristics[0]
	String[] taskSetIDString = taskSetCharacteristics[0].split(":");
	int tSID = -1;
	if (taskSetIDString[0].toLowerCase().equals("tasksetid")) {
		tSID = Integer.parseInt(taskSetIDString[1]);
	} else {
		System.out.println("task set ID corrupt");
		System.exit(0);
	}
	return tSID;
}
	public static ArrayList<Task> getTasksFromString(String taskSetString){
		ArrayList<Task> t = new ArrayList<Task>();
		String[] taskSetCharacteristics = taskSetString.split(";");

		// taskSetCharacteristics[2]
		int numTasks = -1;
		String[] numTasksString = taskSetCharacteristics[2].split(":");
		if (numTasksString[0].toLowerCase().equals("numtasks")) {
			numTasks = Integer.parseInt(numTasksString[1]);
		} else {
			System.out.println("numTasks corrupt");
			System.exit(0);
		}
		int numTaskCharacteristics = 7; // ID, Duration, timeRemaining,
										// rewardRate, deadline, arrivalTime,
										// Type
		// task characteristic
		for (int i = 0; i < numTasks; i++) {
			int ID = -1; // (3 + (numTasks_i * numTaskCharacteristics))
			String[] IDString = taskSetCharacteristics[3 + (i * numTaskCharacteristics)]
					.split(":");
			if (IDString[0].toLowerCase().equals("id")) {
				ID = Integer.parseInt(IDString[1]);
			} else {
				System.out.println("id corrupt");
				System.exit(0);
			}

			int duration = -1; // (4 + (numTasks_i * numTaskCharacteristics))
			String[] durationString = taskSetCharacteristics[4 + (i * numTaskCharacteristics)]
					.split(":");
			if (durationString[0].toLowerCase().equals("duration")) {
				duration = Integer.parseInt(durationString[1]);
			} else {
				System.out.println("duration corrupt");
				System.exit(0);
			}

			int timeRemaining = -1;// (5 + (numTasks_i *
									// numTaskCharacteristics))
			String[] timeRemainingString = taskSetCharacteristics[5 + (i * numTaskCharacteristics)]
					.split(":");
			if (timeRemainingString[0].toLowerCase().equals("timeremaining")) {
				timeRemaining = Integer.parseInt(timeRemainingString[1]);
			} else {
				System.out.println("timeRemaining corrupt");
				System.exit(0);
			}
			double rewardRate = -1.0;// (6 + (numTasks_i *
										// numTaskCharacteristics))
			String[] rewardRateString = taskSetCharacteristics[6 + (i * numTaskCharacteristics)]
					.split(":");
			if (rewardRateString[0].toLowerCase().equals("rewardrate")) {
				rewardRate = Double.parseDouble(rewardRateString[1]);
			} else {
				System.out.println("rewardrate corrupt");
				System.exit(0);
			}
			int deadline = -1;// (7 + (numTasks_i * numTaskCharacteristics))
			String[] deadlineString = taskSetCharacteristics[7 + (i * numTaskCharacteristics)]
					.split(":");
			if (deadlineString[0].toLowerCase().equals("deadline")) {
				deadline = Integer.parseInt(deadlineString[1]);
			} else {
				System.out.println("deadline corrupt");
				System.exit(0);
			}
			int arrivalTime = -1;// (8 + (numTasks_i * numTaskCharacteristics))
			String[] arrivalTimeString = taskSetCharacteristics[8 + (i * numTaskCharacteristics)]
					.split(":");
			if (arrivalTimeString[0].toLowerCase().equals("arrivaltime")) {
				arrivalTime = Integer.parseInt(arrivalTimeString[1]);
			} else {
				System.out.println("arrivalTime corrupt");
				System.exit(0);
			}
			TaskType type = new TaskType();// (9 + (numTasks_i *
											// numTaskCharacteristics))
			String[] typeString = taskSetCharacteristics[9 + (i * numTaskCharacteristics)]
					.split(":");
			if (typeString[0].equals("type")) {
				type.setType(typeString[1]);
			} else {
				System.out.println("type corrupt");
				System.exit(0);
			}
			t.add(new Task(ID, duration, timeRemaining, rewardRate,
					deadline, arrivalTime, type));
		}
		return t;
	}
	public TaskSet(String taskSetString, String taskSetDirectory,
			ScheduleEvaluator e, boolean readAllSchedules) {
		tasks = new ArrayList<Task>();
		String[] taskSetCharacteristics = taskSetString.split(";");

		// taskSetCharacteristics[0]
		String[] taskSetIDString = taskSetCharacteristics[0].split(":");
		taskSetID = -1;
		if (taskSetIDString[0].toLowerCase().equals("tasksetid")) {
			taskSetID = Integer.parseInt(taskSetIDString[1]);
		} else {
			System.out.println("task set ID corrupt");
			System.exit(0);
		}
		// taskSetCharacteristics[1]
		endTime = -1;
		String[] endTimeString = taskSetCharacteristics[1].split(":");
		if (endTimeString[0].toLowerCase().equals("endtime")) {
			endTime = Integer.parseInt(endTimeString[1]);
		} else {
			System.out.println("endTime corrupt");
			System.exit(0);
		}
		// taskSetCharacteristics[2]
		int numTasks = -1;
		String[] numTasksString = taskSetCharacteristics[2].split(":");
		if (numTasksString[0].toLowerCase().equals("numtasks")) {
			numTasks = Integer.parseInt(numTasksString[1]);
		} else {
			System.out.println("numTasks corrupt");
			System.exit(0);
		}
		int numTaskCharacteristics = 7; // ID, Duration, timeRemaining,
										// rewardRate, deadline, arrivalTime,
										// Type
		// task characteristic
		for (int i = 0; i < numTasks; i++) {
			int ID = -1; // (3 + (numTasks_i * numTaskCharacteristics))
			String[] IDString = taskSetCharacteristics[3 + (i * numTaskCharacteristics)]
					.split(":");
			if (IDString[0].toLowerCase().equals("id")) {
				ID = Integer.parseInt(IDString[1]);
			} else {
				System.out.println("id corrupt");
				System.exit(0);
			}

			int duration = -1; // (4 + (numTasks_i * numTaskCharacteristics))
			String[] durationString = taskSetCharacteristics[4 + (i * numTaskCharacteristics)]
					.split(":");
			if (durationString[0].toLowerCase().equals("duration")) {
				duration = Integer.parseInt(durationString[1]);
			} else {
				System.out.println("duration corrupt");
				System.exit(0);
			}

			int timeRemaining = -1;// (5 + (numTasks_i *
									// numTaskCharacteristics))
			String[] timeRemainingString = taskSetCharacteristics[5 + (i * numTaskCharacteristics)]
					.split(":");
			if (timeRemainingString[0].toLowerCase().equals("timeremaining")) {
				timeRemaining = Integer.parseInt(timeRemainingString[1]);
			} else {
				System.out.println("timeRemaining corrupt");
				System.exit(0);
			}
			double rewardRate = -1.0;// (6 + (numTasks_i *
										// numTaskCharacteristics))
			String[] rewardRateString = taskSetCharacteristics[6 + (i * numTaskCharacteristics)]
					.split(":");
			if (rewardRateString[0].toLowerCase().equals("rewardrate")) {
				rewardRate = Double.parseDouble(rewardRateString[1]);
			} else {
				System.out.println("rewardrate corrupt");
				System.exit(0);
			}
			int deadline = -1;// (7 + (numTasks_i * numTaskCharacteristics))
			String[] deadlineString = taskSetCharacteristics[7 + (i * numTaskCharacteristics)]
					.split(":");
			if (deadlineString[0].toLowerCase().equals("deadline")) {
				deadline = Integer.parseInt(deadlineString[1]);
			} else {
				System.out.println("deadline corrupt");
				System.exit(0);
			}
			int arrivalTime = -1;// (8 + (numTasks_i * numTaskCharacteristics))
			String[] arrivalTimeString = taskSetCharacteristics[8 + (i * numTaskCharacteristics)]
					.split(":");
			if (arrivalTimeString[0].toLowerCase().equals("arrivaltime")) {
				arrivalTime = Integer.parseInt(arrivalTimeString[1]);
			} else {
				System.out.println("arrivalTime corrupt");
				System.exit(0);
			}
			TaskType type = new TaskType();// (9 + (numTasks_i *
											// numTaskCharacteristics))
			String[] typeString = taskSetCharacteristics[9 + (i * numTaskCharacteristics)]
					.split(":");
			if (typeString[0].equals("type")) {
				type.setType(typeString[1]);
			} else {
				System.out.println("type corrupt");
				System.exit(0);
			}
			tasks.add(new Task(ID, duration, timeRemaining, rewardRate,
					deadline, arrivalTime, type));
		}

		AverageFlexibility = flexibility(tasks); // 2 + (numTasks *
													// numTaskCharacteristcs)
		AverageOverlap = overlap(tasks);// 3 + (numTasks *
										// numTaskCharacteristcs)

		allPossibleSchedules = new ArrayList<Schedule>();
		evaluator = e;

		optimalScore = -1;
		scoreDistribution = new double[101];// 0,1,2,3..99,100
		if (readAllSchedules == true) {
			readAllPossibleSchedules(taskSetDirectory);
			optimalScore = findOptimalScore();
			for (Schedule currentSchedule : allPossibleSchedules) {
				scoreDistribution[(int) (100 * (currentSchedule
						.getScore(evaluator) / optimalScore))]++;
			}
		}
		System.out.println("finished reading task set properties");
	}

	public double findOptimalScore() {
		if (allPossibleSchedules.size() == 0) {

			System.out.println("no schedules");
			return 0;
		}

		double optimalScheduleValue = evaluator.evaluate(tasks,
				allPossibleSchedules.get(0));
		for (Schedule currentSchedule : allPossibleSchedules) {
			double currentScheduleValue = evaluator.evaluate(tasks,
					currentSchedule);
			if (currentScheduleValue > optimalScheduleValue) {
				optimalScheduleValue = currentScheduleValue;
			}
		}
		return optimalScheduleValue;
	}

	public void writeVisualToFile(String directory, int numTasks) {
		try {
			File file = new File(directory + "\\Visuals\\size" + numTasks
					+ "\\visual" + taskSetID + ".txt");

			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));
			output.write("TaskSetID:" + numTasks + "-" + taskSetID
					+ ";\nendTime:" + endTime + ";\nnumTasks:" + tasks.size()
					+ ";");
			output.write("\nAverageTimePressure:"
					+ String.valueOf(flexibility(tasks)) + ";");
			output.write("\nAverageOverlap:" + String.valueOf(overlap(tasks))
					+ ";");

			output.write("\n");
			output.write("tasks: \n" + tasks.toString().replace(",", "\n"));
			output.write("\n");
			String time = "t:";

			int maxScheduledTime = 0;
			for (Task t : tasks) {
				if (t.getDeadline() > maxScheduledTime) {
					maxScheduledTime = t.getDeadline();
				}
			}

			for (int i = 0; i < maxScheduledTime + 1; i++) {
				time += " " + i % 10;
			}
			output.write("  " + time + "\n");
			for (int i = 0; i < maxScheduledTime + 10; i++) {
				output.write("--");
			}
			output.write("\n");
			for (Task currentTask : tasks) {
				output.write(currentTask.getID() % 10 + "  | ");
				for (int t = 0; t < maxScheduledTime + 1; t++) {
					if (currentTask.getArrivalTime() == t) {
						output.write((currentTask.getDeadline()
								- currentTask.getArrivalTime() + 1)
								% 10 + " ");
					} else if (currentTask.getDeadline() == t) {
						output.write((currentTask.getDeadline()
								- currentTask.getArrivalTime() + 1)
								% 10 + " ");
					} else if (currentTask.getArrivalTime() <= t
							&& currentTask.getDeadline() >= t) {
						output.write(currentTask.getDuration() + " ");
					} else {
						output.write("  ");
					}
				}
				output.write("\n");
			}

			output.close();
			System.out.println("finished visual of " + taskSetID);
		} catch (IOException e) {
			System.out.println("failed to write visual to file ID: "
					+ taskSetID);
			e.printStackTrace();
		}
	}

	private static double flexibility(Task t) {
		return t.getDeadline() - t.getArrivalTime() - t.getDuration() + 1;
	}

	// flexibility(task) : Deadline - starttime - duration
	// flexibility
	public static double flexibility(ArrayList<Task> tasks) {

		// sum task flexibilities
		double totalTaskFlexibility = 0.0;
		for (Task currentTask : tasks) {
			totalTaskFlexibility += flexibility(currentTask);
		}

		/*
		 * return the average task flexibility
		 */
		if (tasks.size() == 0) {
			return 0.0;
		} else {
			double averageTaskFlexibility = totalTaskFlexibility / tasks.size();
			return averageTaskFlexibility;
		}
	}

	public static double overlap(ArrayList<Task> tasks) {
		if (tasks.size() == 0) {
			return 0.0;
		}
		int earliestArrivalTime = tasks.get(0).getArrivalTime();
		int latestDeadline = tasks.get(0).getDeadline();
		for (Task currentTask : tasks) {
			if (currentTask.getArrivalTime() < earliestArrivalTime) {
				earliestArrivalTime = currentTask.getArrivalTime();
			}
			if (currentTask.getDeadline() > latestDeadline) {
				latestDeadline = currentTask.getDeadline();
			}
		}
		int countTotalTaskOverlap = 0;
		int countActiveTimeSlots = 0;
		for (int i = earliestArrivalTime; i < latestDeadline; i++) {
			int countTasksAtTimei = 0;
			for (Task currentTask : tasks) {
				if (currentTask.getArrivalTime() <= i
						&& currentTask.getDeadline() >= i) {
					countTasksAtTimei++;
				}
			}
			countTotalTaskOverlap += countTasksAtTimei;
			if (countTasksAtTimei > 0) {
				countActiveTimeSlots++;
			}
		}
		if (countActiveTimeSlots == 0) {
			return 0;
		}
		return ((double) countTotalTaskOverlap) / countActiveTimeSlots;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public int getTaskSetID() {
		return taskSetID;
	}

	public int getEndTime() {
		return endTime;
	}

	public double getFlexibility() {
		return AverageFlexibility;
	}

	public double getOverlap() {
		return AverageOverlap;
	}

	public TaskSet(ArrayList<Task> t) {
		tasks = t;
	}

	public void writeStatisticsToFile(String fileName, Schedule s) {
		try {
			File file = new File(fileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));

			output.write("TaskSetID," + tasks.size() + "-" + getTaskSetID()
					+ ",TimePressure," + getFlexibility() + ",overlap,"
					+ getOverlap() + ",endTime," + getEndTime() + ",numTasks,"
					+ getTasks().size() + ",");
			if (optimalScore == -1) {
				output.write("OptimalScore, NA,");

			} else {
				output.write("OptimalScore," + optimalScore + ",");
			}
			output.write("ScheduleScore," + s.getScore(this.evaluator) + ",");
			output.write("schedule,");
			output.write(s.toString());

			output.write("\n");

			output.close();
			System.out.println("finished writing task set " + getTaskSetID()
					+ " results to file");
		} catch (IOException e) {
			System.out
					.println("could not write results for: " + getTaskSetID());
			e.printStackTrace();
		}

	}

	public void readAllPossibleSchedules(String taskSetDirectory) {
		String taskSetFileName = taskSetDirectory
				+ "\\AllPossibleSchedules\\size" + tasks.size() + "\\"
				+ getTaskSetID() + "schedules" + tasks.size() + ".txt";

		String currentTaskSetLine;
		try {
			BufferedReader taskSetDirectoryFile = new BufferedReader(
					new FileReader(taskSetFileName));

			int count = 0;
			while ((currentTaskSetLine = taskSetDirectoryFile.readLine()) != null) {
				if (count > 2) {

					int currentTaskSetLine_length = currentTaskSetLine.length();
					int[] schedule = new int[getEndTime()];
					for (int i = 6; i < currentTaskSetLine_length - 1; i += 3) {
						int scheduledTaskID = Character
								.getNumericValue(currentTaskSetLine.charAt(i));
						schedule[(i - 6) / 3] = scheduledTaskID;
					}
					Schedule currentLineSchedule = new Schedule(schedule,
							tasks, evaluator);
					allPossibleSchedules.add(currentLineSchedule);
				}
				count++;
			}
		} catch (IOException e) {
			System.out.println("could ALLPOSSIBLE SCHEDULES EXIST: "
					+ taskSetFileName);
			e.printStackTrace();
		}
	}

	public void writeToFileForTaskSetGeneration(int taskSetID, int endTime,
			String fileName) {
		try {
			File file = new File(fileName);

			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));
			output.write("TaskSetID:" + taskSetID + ";endTime:" + endTime
					+ ";numTasks:" + tasks.size() + ";");
			for (Task currentTask : tasks) {
				output.write(currentTask.toString() + ";");
			}
			output.write("AverageFlexibility:"
					+ String.valueOf(flexibility(tasks)) + ";");
			output.write("AverageOverlap:" + String.valueOf(overlap(tasks))
					+ ";");

			output.write("\n");

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
