import java.io.*;

class Scheduler extends Thread
{
    Semaphore sem;
    FileWriter output;
    Thread1 thread1;
    Thread2 thread2;
    Thread3 thread3;
    Thread4 thread4;
    //record thread overruns
    int overrun1, overrun2, overrun3, overrun4 = 0;
    int period = 1;

    Scheduler()
    {
        this.thread1 = new Thread1(new Sempahore(), this);
        this.thread2 = new Thread2(new Semaphore(), this);
        this.thread3 = new Thread3(new Semaphore(), this);
        this.thread4 = new Thread4(new Semaphore(), this);
    }
    public printResults()
    {
        output.write("Thread1 ran: " + thread1.counter + "times\n");
        output.write("Thread2 ran: " + thread2.counter + "times\n");
        output.write("Thread3 ran: " + thread3.counter + "times\n");
        output.write("Thread4 ran: " + thread4.counter + "times\n");
        output.write("Thread1 overran: " overrun1 + "times\n");
        output.write("Thread2 overran: " + overrun2 + "times\n");
        output.write("Thread3 overran: " + overrun3 + "times\n");
        output.write("Thread4 overran: " + overrun4 + "times\n");
        ouput.close();
    }

    public void start()
    {
        Thread thread = new Thread(new Scheduler());
        thread.start();
    }
    
    public void run()
    {
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        //Scheduler has a major frame period of 16 units of time
        //All threads run every 16 units
        while(period<=160)
        {
            try{
                Thread.sleep(20);
            }
            catch(Exception err)
            {
                System.println("Error: " + err);
            }
            if(period%16==0) //check for end of period
            {
                if(!thread1.isRunning){
                    thread1.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun1++;

                if(!thread2.isRunning){
                    thread2.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun2++;

                if(!thread3.isRunning){
                    thread3.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun3++;

                if(!thread1.isRunning){
                    thread1.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun4++;
                }
            }
            else if (period%4==0)
            {  //thread1, thread2, thread3 only
                if(!thread1.isRunning)
                {
                    thread1.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun1++;

                if(!thread2.isRunning)
                {
                    thread2.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun2++;

                if(!thread3.isRunning)
                {
                    thread3.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun3++;
            }
            else if(period%2==0)
            { //thread1, thread2 only
                if(!thread1.isRunning)
                {
                    thread1.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun1++;

                if(!thread2.isRunning)
                {
                    thread2.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.println("Error: " + err);
                    }
                }
                else overrun2++;
            }
            else
            {//thread1 only
                if(!thread1.isRunning)
                {
                    thread1.semaphore.semSignal();
                    try{
                        this.sem.semWait();
                    }
                    catch(Exception err)
                    {
                        System.out.println("Error: " + err);
                    }
                }
                else overrun1++;
            }
            period++;
        }
        //Create output file
        try{
            output = new FileWriter("output.txt");
            printResults();
        }
        catch(Exception err)
        {
            System.println("Error: " + err);
        }
        //terminate threads after 160 periods of execution
        System.exit(0);

        }
    }

    public static void main(String[] args)
    {
        Scheduler sched = new Scheduler();
        sched.semaphore = new Semaphore();
        sched.start();
    }
}
