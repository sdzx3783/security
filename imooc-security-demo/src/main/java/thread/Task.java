package thread;

public interface Task {
	public TaskResult run();
	
	public String getTaskId();
	
	public String getTaskName();
	
}
