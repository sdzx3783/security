package threadpool;

public class BpmService {
	public void run(String taskid){
		boolean issuccess=true;
		System.out.println("bpmservice 任务"+taskid+"开始--------------------------");
		try {
			Thread.sleep(20*1000);//耗时操作
		} catch (InterruptedException e) {
			issuccess=false;
			System.out.println("bpmservice 任务"+taskid+"被中断--------------------------");
		}
		if(issuccess) {
			
			System.out.println("bpmservice 任务"+taskid+"结束--------------------------");
		}
	}
}
