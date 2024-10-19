package edu.utdallas.taskExecutorImpl;
import edu.utdallas.taskExecutor.Task;


public class TaskRunner implements Runnable {
  private final BlockingQueue taskQueue;
 
  //constructor
  public TaskRunner(BlockingQueue taskQueue) {
		this.taskQueue=taskQueue;
	}
  
  //take tasks from queue -> execute them
  public void run(){
    while(true){
      try{
        Task task = taskQueue.take() //take task if avaliable, block if no tasks in queue
        task.execute(); 
      }catch(InterruptedException e){
        Thread.currentThread().interrupt();
			  break;
      }catch(Throwable th){
        System.err.println("Task execution failed: " + th.getMessage());
        th.printStackTrace(); 
      }
    }
  }
}
