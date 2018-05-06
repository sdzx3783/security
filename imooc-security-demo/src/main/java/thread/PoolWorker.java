package thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PoolWorker extends Thread{
	private Log logger = LogFactory.getLog(PoolWorker.class);
	private ConcurrentLinkedQueue<Task> queue;
	private ConcurrentHashMap<Task, TaskResult> resultMap;
	private PoolWorker() {
	}
	public PoolWorker(ConcurrentLinkedQueue<Task> queue,ConcurrentHashMap<Task, TaskResult> resultMap){
		this.queue=queue;
		this.resultMap=resultMap;
	}
	
	public void run(){
		while(true){
			String taskid="";
			String taskName="";
			try {
				Task poll = queue.poll();
				if(poll==null) {
					try {
						logger.info(Thread.currentThread().getName()+"线程 ：worker处于空闲状态");
						Thread.sleep(200);
					} catch (InterruptedException e) {
						//work线程被请求中断
						logger.warn("worker线程被请求中断!");
					}
				}else {
					taskid=poll.getTaskId();
					taskName=poll.getTaskName();
					long startTime = System.currentTimeMillis();
					logger.info(Thread.currentThread().getName()+"线程 ：任务ID："+taskid+" 任务名称："+taskName+"任务开始执行");
					TaskResult run = poll.run();
					long endTime = System.currentTimeMillis();
					run.setExecuteTime((endTime-startTime)/1000);
					resultMap.put(poll, run);
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("任务ID："+taskid+" 任务名称："+taskName+"异步执行失败!",e);
				
			}
		}
	}

	public void setQueue(ConcurrentLinkedQueue<Task> queue) {
		this.queue = queue;
	}

	public void setResultMap(ConcurrentHashMap<Task, TaskResult> resultMap) {
		this.resultMap = resultMap;
	}
	
	
}
