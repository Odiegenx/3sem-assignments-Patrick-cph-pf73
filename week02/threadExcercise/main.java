package threadExcercise;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) {
        // task 1:
        Task1 task1 = new Task1();
        ExecutorService myService = Executors.newFixedThreadPool(4);

        for(char c = 'A';c <= 'Z';c++){
            myService.execute(task1);
        }
        // task 2:
        for(int i = 0;i < 100;i++){
            myService.submit(() ->{
                Counter.increment();
            });
        }
        myService.shutdown();
        try {
            myService.awaitTermination(15000, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Counter.getCount());
    }
    private static class Counter {
        private static int count = 0;

        // Method to increment the count, synchronized to ensure thread safety
        public static synchronized void increment() {
            count++;
        }

        // Method to retrieve the current count value
        public static int getCount() {
            return count;
        }
    }
}
