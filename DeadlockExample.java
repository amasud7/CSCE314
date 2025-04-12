/*
Ayad Masud
4/11/25
CSCE 314
*/

public class DeadlockExample {

    // Two objects used as locks.
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        // Thread A locks lock1 and then tries to lock lock2.
        Thread threadA = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread A: Acquired lock1. Trying to acquire lock2...");
                // Sleep to help ensure that Thread B gets a chance to run.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // This will block if Thread B already holds lock2.
                synchronized (lock2) {
                    System.out.println("Thread A: Acquired lock2.");
                }
            }
        });

        // Thread B locks lock2 and then tries to lock lock1.
        Thread threadB = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread B: Acquired lock2. Trying to acquire lock1...");
                // Sleep to help ensure that Thread A gets a chance to run.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
            }
            synchronized (lock2) {
                System.out.println("Thread B: Acquired lock1.");
            }

        });

        // Start both threads.
        threadA.start();
        threadB.start();
    }
}