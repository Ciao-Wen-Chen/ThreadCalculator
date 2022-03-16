

public class SlowCalculator implements Runnable {

    private final long N;
    private int result;
    private boolean ifFinished = false;
    private boolean ifCancelled = false;
    private Thread thread;

    public SlowCalculator(final long N) {
        this.N = N;
        thread = new Thread( this );
    }

    public void run() {
        this.result = calculate( N );
        ifFinished = true;
    }

    public void setThread( Thread t ) {
        this.thread = t;
    }
    public Thread getThread() {
        return this.thread;
    }

    public boolean getIfFinished(){
        return ifFinished;
    }
    public boolean getIfCancelled() {
        return ifCancelled;
    }
    public void setIfCancelled( boolean b ) {
        this.ifCancelled = b;
    }

    public int getResult(){
        return this.result;
    }

    public String toString() {
        return null;
    }

    private static int calculate(final long N) {
        // This (very inefficiently) finds and returns the number of unique prime factors of |N|
        // You don't need to think about the mathematical details; what's important is that it does some slow calculation taking N as input
        // You should not modify the calculation performed by this class, but you may want to add support for interruption
        int count = 0;
        for (long candidate = 2; candidate < N; ++candidate) {
            
            // implement cancel function
            if( Solution.threadCalMap.get(N).getIfCancelled() ){
                System.out.println( "the " + N + " is been cancelled" );
                return -1;
            }

            if (isPrime(candidate)) {
                if (Math.abs(N) % candidate == 0) {
                    count++;
                }
            }
        }
        System.out.println( N + " is finished" );
        return count;
    }

    private static boolean isPrime(final long n) {
        // This (very inefficiently) checks whether n is prime
        // You should not modify this method
        for (long candidate = 2; candidate < Math.sqrt(n); ++candidate) {
            if (n % candidate == 0) {
                return false;
            }
        }
        return true;
    }
}
