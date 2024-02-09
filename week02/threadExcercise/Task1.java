package threadExcercise;

import kotlin.jvm.Synchronized;

public class Task1 implements Runnable {
    private static char start = 'A';
    @Override
    public synchronized void run() {
        System.out.println(start+""+start+""+ start);
        start++;
    }
    public synchronized void printTribleChars(){
        System.out.println(start+""+start+""+ start);
        start++;
    }
}
