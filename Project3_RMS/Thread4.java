import java.util.concurrent.*;

class Thread1 extends Thread{

    public Sempahore semaphore;
    public Scheduler scheduler;
    public static int counter;
    public static final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
    public static boolean isRunning;

    Thread1(Semaphore sem, Scheduler sch)
    {
        this.semaphore = sem;
        this.scheduler = sch;
        counter = 0;
        isRunning = false;
    }
    public void run()
    {
        while(isRunning){
            try{
                this.semaphore.semWait();
            }
            catch(Exception err){
                System.out.println("Thread1: " + err);
            }
            final Runnable send = new Runnable()
            {
                public void run(){
                    this.scheduler.semSignal();
                }
            };
            timer.schedule(send, 10, TimeUnit.MILLISECONDS);
            setRunning(true);
            //Thread4 executes 16 times
            for(int i=0; i<1; i++)
            {
                doWork();
            }
            setCounter(1);
            setRunning(false);
        }
    }
    public void start()
    {
        Thread thr = new Thread(new Thread1(this.semaphore, this.scheduler));
        thr.start();
    }

    public void setCounter(int newCounter)
    {
        this.counter += newCounter;
    }

    public void setRunning(boolean newRunning)
    {
        this.isRunning = newRunning; //newRunning should be false if doWork overruns
    }

    public static void doWork()
    {
        int[][] matrix = new int[10][10] matrix;
        for(int j=0; j<10; j++)
        {
            for(int k=0; k<10; k++)
            {
                matrix[j][k] = 1;
            }
        }
        int result = 1;
        //column order of execution
        int matrixCol = {0, 5, 1, 6, 2, 7, 3, 8, 4, 9};
        for(int j=0; j<10; j++)
        {
            for(int k=0; k<10; k++)
            {
                result = result * matrix[j][k];
            }
        }
    }
}
