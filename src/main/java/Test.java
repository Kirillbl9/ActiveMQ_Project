import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Test {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        List<Integer> list = new Vector<>();
        list.add(1);
        list.add(1);



        es.submit(Test::hello);
        es.shutdown();
    }

    static void hello() {
        int i = (int) (1 + Math.random() * 5);
        i = i * 200;
        System.out.println("hello " + i);
        try {
            sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
