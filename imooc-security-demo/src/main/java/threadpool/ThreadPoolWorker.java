package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import thread.TaskResult;
/**
 * 用于某个模块下的任务处理
 * @author Administrator
 *
 */
public class ThreadPoolWorker {
	private static ThreadPoolExecutor executorService=new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
	private static int timeout=6;
	
	private static Future<TaskResult> submit(Callable<TaskResult> task) {
		return executorService.submit(task);
	}
	
	private static void execute(Runnable task) {
		executorService.execute(task);
	}
	//注释掉main方法是为了maven 打war包
	/*public static void main(String[] args) {
		List<Future<TaskResult>> list=new ArrayList<>();
		for(int i=0;i<=30;i++) {
			Task task = new Task(i+"", "task_"+i);
			Future<TaskResult> submit = null;
			try {
				submit = executorService.submit(task);
			} catch (RejectedExecutionException e) {
				System.out.println("系统繁忙，请稍后再试！");
			}
			if(submit!=null) {
				list.add(submit);
			}
		}
		System.out.println("list size: "+list.size());
		
		for (Future<TaskResult> future : list) {
			TaskResult taskResult =null;
			try {
				taskResult = future.get(timeout, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				//如果超时 中断线程
				taskResult=new TaskResult(TaskResult.FAIL, "任务处理超时，请稍后再试！");
			}
			System.out.println(taskResult);
		}
	}*/
	
}

class  Task implements Callable<TaskResult>{
	private String id;
	private String name;
	
	private BpmService bpmService=new  BpmService();
	public Task(String id,String name) {
		this.id=id;
		this.name=name;
	}

	@Override
	public TaskResult call(){
		try {
			bpmService.run(id);
		} catch (Exception e) {
			new TaskResult(TaskResult.FAIL, "任务处理异常");
		}
		TaskResult taskResult = new TaskResult(TaskResult.SUCCESS, null);
		taskResult.setTaskid(id);
		taskResult.setTaskName(name);
		return taskResult;
	}
	
}
