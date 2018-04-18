package com.imooc.thread.task;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import thread.MasterWorker;
import thread.Task;
import thread.TaskResult;
import thread.impl.BpmSubmitTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MasterWorkerTest {
	@Test
	public void testTaskExe() throws InterruptedException {
		MasterWorker master=new MasterWorker(10);
		String taskName="流程提交任务";
		List<Task> list=new ArrayList<>();
		List<TaskResult> taskResults=new ArrayList<>();
		for(int i=1;i<50;i++) {
			BpmSubmitTask t=new BpmSubmitTask(i+"", taskName);
			list.add(t);
			master.execute(t);
		}
		System.out.println("共提交任务："+list.size());
		while(true) {
			boolean flag=true;
			for (Task task : list) {
				if(!master.isComplete(task)) {
					flag=false;
					break;
				}
			}
			if(flag) {
				break;
			}
		}
		System.out.println("任务 已全部执行完毕！");
		for (Task task : list) {
			taskResults.add(master.getResult(task));
		}
		System.out.println("任务执行结果："+taskResults);
		
		
		//阻塞主线程 单元测试主线程关闭 会导致子线程也关闭
		//Thread.sleep(300000);
	}
}
