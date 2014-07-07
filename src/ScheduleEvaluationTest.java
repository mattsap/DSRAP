import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class ScheduleEvaluationTest {
	@Test
	public void IncrementalUtilityEvaluatorTest() {
		
		IncrementalUtilityEvaluator iue = new IncrementalUtilityEvaluator();
		Agent one = new Agent(5, iue);
		Task t1 = new Task(3,3,5.0,10,0,new TaskType());
		Task t2 = new Task(3,3,15.0,5,0,new TaskType());
		Task t3 = new Task(3,3,10.0,1,0,new TaskType());
		Task t4 = new Task(3,3,1.0,10,0,new TaskType());
		
		int[] assignment1 = {0,0,0,1,1,1,2,2,2};
		int[] assignment2 = {-1, 1, 1, -1, 2, 2};
		int[] assignment3 = {2, -1, 1, 3, -1, 2};
		int[] assignment4 = {-1, 3, 3, -1, -1, -1};
		
		ArrayList<Task> empty = new ArrayList<Task>();
		Schedule s1 = new Schedule(assignment1,empty);
		Schedule s2 = new Schedule(assignment2, empty);
		Schedule s3 = new Schedule(assignment3, empty);
		Schedule s4 = new Schedule(assignment4, empty);
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
		tasks.add(t4);

		assertTrue(90.0 == iue.evaluate(tasks,s1));
		assertTrue(50.0 == iue.evaluate(tasks,s2));
		assertTrue(36.0 == iue.evaluate(tasks,s3));
		assertTrue( 2.0 == iue.evaluate(tasks,s4));
	}
}
