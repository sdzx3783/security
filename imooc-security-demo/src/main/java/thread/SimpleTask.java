package thread;

import util.ExceptionUtil;

public abstract class SimpleTask implements Task {
	protected String taskid;
	protected String taskName;
	
	@Override
	public abstract TaskResult run();
	
	public SimpleTask(String taskid, String taskName) {
		super();
		this.taskid = taskid;
		this.taskName = taskName;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public void settaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String getTaskId() {
		return taskid;
	}

	@Override
	public String getTaskName() {
		return taskName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskid == null) ? 0 : taskid.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleTask other = (SimpleTask) obj;
		if (taskid == null) {
			if (other.taskid != null)
				return false;
		} else if (!taskid.equals(other.taskid))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}
	
	
	
}
