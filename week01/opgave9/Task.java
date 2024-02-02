package opgave9;

public class Task {
    void run() throws InterruptedException {
            Thread.sleep(1000);
            double random = Math.random();
            // made it throw an exception half of the times.
            if(random<0.5){
                throw new InterruptedException("Simulated exception!");
            }
            System.out.println("Task completed");
    }
}
