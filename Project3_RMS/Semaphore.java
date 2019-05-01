class Sempahore{
    public int data;
    //initialize
    Semaphore(){
        this.data = 0;
    }
    public synchronized void semWait()
    {
        while(this.data <= 0)
        {
            wait();
        }
        this.data--;
    }
    public synchronized void semSignal()
    {
        notifyAll(); //wake up multiple waiting threads
        this.data++;
    }

}
