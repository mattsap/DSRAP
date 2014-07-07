
public class Prediction extends Task {
	private static int countPredictions = 0;
	private Agent owner;
	
	public Prediction(int d, int t, double r, int de, int a, TaskType tt, Agent m) {
		super(d, t, r, de, a, tt);
		super.id = countPredictions;
		owner = m;
		countPredictions++;
	}

	public Prediction(Task T, Agent m) {
		super(T.getDuration(), T.getTimeRemaining(), T.getRewardRate(), T.getDeadline(), T.getArrivalTime(), T.getType());
		super.id = countPredictions;
		owner = m;
		countPredictions++;
	}
	public int getWait(int time){
		return super.arrivalTime - time;
	}
	public Agent getOwner(){
		return owner;
	}

}
