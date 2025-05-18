package com.webserver.async;

public abstract class ServerThread {

    protected SharedResource sharedResource;
    protected ThreadController threadController;
    protected boolean taskRunning;
    private Runnable task;
    private Thread thread;

    public ServerThread(SharedResource sharedResource, ThreadController threadController){
        this.sharedResource = sharedResource;
        this.threadController = threadController;
        setTask(() -> {
            task();
        });
    }
    
    protected abstract void task();

    /**
     * sets the Runnable task. This method MUST be called before invoking
     * initThread().
     *
     * @param task Runnable task that the thread will execute. Preferably a lambda
     *             expression
     */
    protected void setTask(Runnable task){
        this.task = task;
    }

    /**
     * This method is not intended to be invoked directly. This method should be
     * invoked by one of ServerThread's Children. The child
     * must also call setTask() to initialize the task. If task has not been
     * initialized before this method was invoked, this method will return -1
     * indicating an error.
     *
     * @return success status of the thread creation. -1 if task is null
     */
    public int initThread(){
        if(task == null){
            return -1;
        }
        thread = new Thread(task);
        return 0;
    }

    /**
     * This method should be used to start the execution of a thread.
     */
    public void start() {
        taskRunning = true;
        initThread();
        thread.start();
    }

    public void stop(){
        taskRunning = false;
    }

}
