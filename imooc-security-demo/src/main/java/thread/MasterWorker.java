package thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作业队列
 */
public class MasterWorker{
	private Log logger = LogFactory.getLog(MasterWorker.class);
	private final int nThreads;
	private final PoolWorker[] threads;
	ConcurrentLinkedQueue<Task> queue=new ConcurrentLinkedQueue<Task>();
	ConcurrentHashMap<Task, TaskResult> resultMap=new ConcurrentHashMap<>();
	public MasterWorker(int nThreads){
		this.nThreads=nThreads;
		threads = new PoolWorker[nThreads];
		for(int i=0;i<this.nThreads;i++){
			threads[i] = new PoolWorker(queue,resultMap);
			threads[i].start();
		}
	}
	
	public void execute(Task t){
		queue.add(t);
		logger.info("masterworker任务待办数："+queue.size());;
	}
	
	public boolean isComplete(Task t){
		return resultMap.containsKey(t);
	}
	
	public TaskResult getResult(Task t){
		return resultMap.get(t);
	}
}