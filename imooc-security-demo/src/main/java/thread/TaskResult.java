package thread;

public class TaskResult {
	public static final int SUCCESS=1;
	public static final int FAIL=0;
	private Object data;
	private int status;
	private String taskid;//任务ID
	private String taskName;//任务名称
	private long executeTime;//任务执行时间
	private String failError;//任务执行失败错误信息
	
	
	
	public TaskResult(Object data, int status, String taskid, String taskName,long executeTime, String failError) {
		super();
		this.data = data;
		this.status = status;
		this.taskid = taskid;
		this.taskName=taskName;
		this.executeTime = executeTime;
		this.failError = failError;
	}
	
	public TaskResult(int status, String taskid,String taskName, long executeTime, String failError) {
		this.status = status;
		this.taskid = taskid;
		this.taskName=taskName;
		this.executeTime = executeTime;
		this.failError = failError;
	}
	
	public TaskResult(int status, String taskid,String taskName, String failError) {
		this.status = status;
		this.taskid = taskid;
		this.taskName=taskName;
		this.failError = failError;
	}
	
	public TaskResult(int status,Object data) {
		this.status=status;
		this.data=data;
	}
	public TaskResult(int status) {
		this.status=status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}


	public long getExecuteTime() {
		return executeTime;
	}


	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

	public String getFailError() {
		return failError;
	}

	public void setFailError(String failError) {
		this.failError = failError;
	}

	@Override
	public String toString() {
		return "TaskResult [data=" + data + ", status=" + status + ", taskid=" + taskid + ", taskName=" + taskName
				+ ", executeTime=" + executeTime + ", failError=" + failError + "]";
	}
	
}
