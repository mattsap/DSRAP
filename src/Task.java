public class Task implements Comparable {
	private static int countTasks = 0;
	protected int id;
	protected int duration;
	protected int timeRemaining;
	protected double rewardRate;
	protected int deadline;
	protected int arrivalTime;
	protected TaskType type;

	/**
	 * Constructor for task generation. 
	 * @param duration duration of the task (time units)
	 * @param timeRemaining the time remaining required to complete the task.
	 * @param rewardRate how much reward an agent gets for working on this task
	 * @param deadline when the task needs to be completed by
	 * @param arrivalTime when the task is first known about
	 * @param taskType the type of task that requires a specific capability to complete the task
	 */
	public Task(int duration, int timeRemaining, double rewardRate,
			int deadline, int arrivalTime, TaskType taskType) {
		this.id = countTasks;
		this.duration = duration;
		this.timeRemaining = timeRemaining;
		this.rewardRate = rewardRate;
		this.deadline = deadline;
		this.arrivalTime = arrivalTime;
		this.type = taskType;
		this.countTasks++;
	}
	

	/**
	 * Constructor for input file reads. The string array should be in the
	 * following format: {"id:val", "duration:val", "timeRemaining:val",
	 * "rewardRate:", "deadline:val", "arrivalTime:val", "type:val"}
	 * 
	 * @param aTaskStringArray
	 *            a String array representing all task characteristics, where
	 *            each string consists of "characteristics:value"
	 * @deprecated System exit upon read input failure.Possibly should throw exception and handle during task set construction.
	 */
	public Task(String[] aTaskStringArray) {// id,duration, timeRemaining,
											// rewardRate,deadline,arrivalTime,
											// Type
		String[] IDString = aTaskStringArray[0].split(":");
		if (IDString[0].toLowerCase().equals("id")) {
			this.id = Integer.parseInt(IDString[1]);
		} else {
			System.out.println("id corrupt");
			System.exit(0);
		}

		String[] durationString = aTaskStringArray[1].split(":");
		if (durationString[0].toLowerCase().equals("duration")) {
			this.duration = Integer.parseInt(durationString[1]);
		} else {
			System.out.println("duration corrupt");
			System.exit(0);
		}

		String[] timeRemainingString = aTaskStringArray[2].split(":");
		if (timeRemainingString[0].toLowerCase().equals("timeremaining")) {
			this.timeRemaining = Integer.parseInt(timeRemainingString[1]);
		} else {
			System.out.println("timeRemaining corrupt");
			System.exit(0);
		}

		String[] rewardRateString = aTaskStringArray[3].split(":");
		if (rewardRateString[0].toLowerCase().equals("rewardrate")) {
			this.rewardRate = Double.parseDouble(rewardRateString[1]);
		} else {
			System.out.println("rewardrate corrupt");
			System.exit(0);
		}

		String[] deadlineString = aTaskStringArray[4].split(":");
		if (deadlineString[0].toLowerCase().equals("deadline")) {
			this.deadline = Integer.parseInt(deadlineString[1]);
		} else {
			System.out.println("deadline corrupt");
			System.exit(0);
		}

		String[] arrivalTimeString = aTaskStringArray[5].split(":");
		if (arrivalTimeString[0].toLowerCase().equals("arrivaltime")) {
			this.arrivalTime = Integer.parseInt(arrivalTimeString[1]);
		} else {
			System.out.println("arrivalTime corrupt");
			System.exit(0);
		}

		String[] typeString = aTaskStringArray[6].split(":");
		if (typeString[0].equals("type")) {
			this.type = new TaskType(typeString[1]);
		} else {
			System.out.println("type corrupt");
			System.exit(0);
		}
	}

	/*
	 * public Task(int i,int d, int t, double r, int de, int a, TaskType tt) {
	 * id = i; duration = d; timeRemaining = t; rewardRate = r; deadline = de;
	 * arrivalTime = a; type = tt; countTasks++; }
	 */
	/**
	 * @return boolean true if task ID's are equal. 
	 */
	public boolean equals(Object o) {
		Task t = (Task) o;
		return t.getID() == this.id;
	}

	public static int getCountTasks() {
		return countTasks;
	}

	public void decrementTimeRemaining() {
		timeRemaining--;
	}

	public void incrementTimeRemaining() {
		timeRemaining++;
	}

	public int getID() {
		return id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int d) {
		this.duration = d;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	public double getRewardRate() {
		return rewardRate;
	}

	public void setRewardRate(double r) {
		this.rewardRate = r;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int d) {
		this.deadline = d;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int a) {
		this.arrivalTime = a;
	}

	public TaskType getType() {
		return type;
	}
	/**
	 * returns 1 if the caller's id is larger, 0 if equal, -1 if less than the input Task.
	 */
	@Override
	public int compareTo(Object arg0) {
		Task other = (Task) arg0;
		if (this.id > other.id) {
			return 1;
		} else if (this.id == other.id) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return "ID:" + id + ";" + "duration:" + duration + ";"
				+ "timeRemaining:" + timeRemaining + ";" + "rewardRate:"
				+ rewardRate + ";" + "deadline:" + deadline + ";"
				+ "arrivalTime:" + arrivalTime + ";" + "type:"
				+ type.toString();
	}

}
