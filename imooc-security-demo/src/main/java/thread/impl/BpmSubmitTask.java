package thread.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import thread.SimpleTask;
import thread.TaskResult;
import util.ExceptionUtil;

public class BpmSubmitTask extends SimpleTask{
	private Log logger = LogFactory.getLog(BpmSubmitTask.class);
	public BpmSubmitTask(String taskid, String taskName) {
		super(taskid, taskName);
	}

	@Override
	public TaskResult run() {
		try {
			Thread.sleep(1000);//do something
		} catch (Exception e) {
			//出现异常
			String exceptionMessage = ExceptionUtil.getExceptionMessage(e);
			logger.error(Thread.currentThread().getName()+"线程 :任务ID："+taskid+" 任务名称："+taskName+"异步执行失败!",e);
			new TaskResult(TaskResult.FAIL,taskid,taskName,exceptionMessage);
		}
		logger.info(Thread.currentThread().getName()+"线程 :任务ID："+taskid+" 任务名称："+taskName+"执行成功!");
		return new TaskResult(TaskResult.SUCCESS,taskid,taskName,null);
	}
	

}
