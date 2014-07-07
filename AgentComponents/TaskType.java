
public class TaskType {
	public String type;
	public TaskType(){
		type = "type";
	}
	public TaskType(String t){
		type = t;
	}
	public void setType(String astr){
		type = astr;
	}
	@Override
	public String toString(){
		return "type";
	}
}
