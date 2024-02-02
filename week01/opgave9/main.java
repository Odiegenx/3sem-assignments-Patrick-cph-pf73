package opgave9;

import java.util.concurrent.*;

public class main {
    public static void main(String[] args) {
        //// 2
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                new Task().run();
            } catch (InterruptedException e) {
                System.out.println("Completable future got an exception: " + e.getMessage());
            }
        }).exceptionally(e -> {
            //System.out.println("Completable future got an exception: " + e.getMessage());
            return null;
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                new Task().run();
            } catch (InterruptedException e) {
                System.out.println("Completable future got an exception: " + e.getMessage());
            }
        }).exceptionally(e -> {
            //System.out.println("Completable future got an exception: " + e.getMessage());
            return null;
        });
        CompletableFuture<Void> futures = CompletableFuture.allOf(future1,future2);
        /*
        need to add something that makes sure main waits for all the tasks to complete
        or main method will complete before the tasks are done, and nothing is printed.
        */
        // crude method to make sure main waits.
        //Thread.sleep(2000);
        // waits for all futures to complete
        try{
          futures.get();
        }catch (InterruptedException | ExecutionException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        futures.thenRun(() -> System.out.println("All CompletableFutures completed"));
        //// 3.
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                new Task().run();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                new Task().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // the shutdown wait for all tasks to complete first.
        executorService.shutdown();
        try{
            // stops the main thread til all tasks are done, or shuts them down forcefully(if able) after 10 seconds.
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("All executor tasks completed");
    }
}
