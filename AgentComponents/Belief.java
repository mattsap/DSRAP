public class Belief extends Task {
	private static int countBeliefs = 0;
	private Agent owner;
	
	public Belief(int d, int t, double r, int de, int a, TaskType tt, Agent m) {
		super(d, t, r, de, a, tt);
		super.id = countBeliefs;
		owner = m;
		countBeliefs++;
	}
/*
	public Belief(Task T, Agent m) {
		super(T.getDuration(), T.getTimeRemaining(), T.getRewardRate(), T.getDeadline(), T.getArrivalTime(), T.getType());
		super.id = countBeliefs;
		owner = m;
		countBeliefs++;
	}
	*/
	public Agent getOwner(){
		return owner;
	}

}
