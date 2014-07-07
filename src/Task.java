public class Task implements Comparable {
	private static int countTasks = 0;
	protected int id;
	protected int duration;
	protected int timeRemaining;
	protected double rewardRate;
	protected int deadline;
	protected int arrivalTime;
	protected TaskType type;

	public Task(int d, int t, double r, int de, int a, TaskType tt) {
		id = countTasks;
		duration = d;
		timeRemaining = t;
		rewardRate = r;
		deadline = de;
		arrivalTime = a;
		type = tt;
		countTasks++;
	}

	public Task(int i, int d, int t, double r, int de, int a, TaskType tt) {
		id = i;
		duration = d;
		timeRemaining = t;
		rewardRate = r;
		deadline = de;
		arrivalTime = a;
		type = tt;
		countTasks++;
	}

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
	public void incrementTimeRemaining(){
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
	public String toString(){
		return "ID:"+ id+";"+"duration:"+duration + ";" + "timeRemaining:"+ timeRemaining + ";" + "rewardRate:"+ rewardRate + ";" + "deadline:"+ deadline +";" + "arrivalTime:"+arrivalTime + ";"+ "type:"+ type.toString();
	}

}
