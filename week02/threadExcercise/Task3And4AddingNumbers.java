package threadExcercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task3And4AddingNumbers {

    public static void main(String[] args) {
        //ExecutorService workingJack = Executors.newFixedThreadPool(17);

        /*
            To solve task 4. I changed the executorService to a newCachedThreadPool. newCachedThreadPool allows
            the Executor Service to take the necessary resources it needs to complete its task as fast as possible.
         */
        ExecutorService workingJack = Executors.newCachedThreadPool();
        System.out.println("Main starts");
        IntegerList integerList = new IntegerList();
        for (int count = 0; count < 100000; count++) {
            workingJack.submit(new TaskToAddCount(integerList, count));
        }
        System.out.println("Main is done");
        workingJack.shutdown();
    }

    private static class IntegerList {
        private static List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        /*
        The issue is that the threads tries adds their produced integer at the same time or almost at the same time to the same list
        which in some chases ends up added the product to the same index.
        Using synchronized list is a way of resolving the issue as it's thread safe.

        Another solution is to make sure, that only one thread is allowed to deliver their product to the list at a time
        using the synchronized key word on the method.
        */
        public /*synchronized*/ void addCount(int count) {
            list.add(count);
            System.out.println("Task: " + count + ": List size = " + list.size());
        }
    }
    private static class TaskToAddCount implements Runnable {
        // Gets a reference to the shared list and the count to add
        private IntegerList integerList;
        private int count;

        TaskToAddCount(IntegerList integerList, int count) {
            this.integerList = integerList;
            this.count = count;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((int) Math.random()*800+200);
                integerList.addCount(count);
            } catch (InterruptedException ex) {
                System.out.println("Thread was interrupted");
            }
        }
    }
}
