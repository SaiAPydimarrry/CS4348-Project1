package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
	private final BlockingQueue taskQueue; 
	private final Thread[] workerThreads; //threadpool array

	public TaskExecutorImpl(int threadPoolSize)
	{
		// TODO Complete the implementation
		taskQueue = new BlockingQueue(100); //max size of queue is 100
		workerThreads = new Thread [threadPoolSize]; //create n space for thread pool

		for(int i = 0; i<threadPoolSize; i++){ //iterate over threadpool
			workerThreads[i] = new Thread(new TaskRunner(taskQueue)); //create new thread and  fetch task
			workerThreads[i].start(); //start thread
		}
	}
	
	@Override
	public void addTask(Task task)
	{
		// TODO Complete the implementation
		try{
			taskQueue.put(task); //add task to queue, block if queue is full
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
	}

}
