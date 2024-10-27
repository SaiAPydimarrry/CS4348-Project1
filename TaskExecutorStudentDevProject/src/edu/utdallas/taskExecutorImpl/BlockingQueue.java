package edu.utdallas.taskExecutorImpl;
import edu.utdallas.taskExecutor.Task;

public class BlockingQueue {
	
	private Task[] buffer;
    private int nextIn;
    private int nextOut;
    private int count;
    private final Object notFull;
    private final Object notEmpty;
    
    
    public BlockingQueue(int capacity) {
        this.buffer = new Task[capacity];
        this.nextIn = 0;
        this.nextOut = 0;
        this.count = 0;
        notFull = new Object();
        notEmpty = new Object();
    }
    
    public void put(Task task) throws InterruptedException {
    	
        while (true) {
            synchronized (notFull) {
            	if (count >= buffer.length) {
                    notFull.wait();
                }
            }

            synchronized (this) {
            	if (count >= buffer.length) {
                    continue;
                }
            	
                buffer[nextIn] = task;
                nextIn = (nextIn + 1) % buffer.length;
                count++;

                synchronized (notEmpty) {
                    notEmpty.notify();
                }
                return;
            }
        }
    }
    
    
    public Task take() throws InterruptedException {
    	
        while (true) {
            synchronized (notEmpty) {

                if (count == 0) {
                    notEmpty.wait();
                }
            }

            synchronized (this) {
                if (count == 0) {
                    continue;
                }
                Task task = buffer[nextOut];
                nextOut = (nextOut + 1) % buffer.length;
                count--;

                synchronized (notFull) {
                    notFull.notify();
                }
                return task;
            }
        }
    }

}

