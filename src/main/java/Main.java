import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<Future> futures = new ArrayList<>();

        for(int i = 0; i<8; i++){
           Future<Integer> item = executor.submit(()-> {
                Random random = new Random();
                int num = random.nextInt();
                return num;
            });
           futures.add(item);
        }
        
        futures.get(5).cancel(true);
        futures.get(7).cancel(true);

        for(Future f:futures){
            if(!f.isCancelled()){
                f.get();
            }
        }
        System.out.println(countFinishedFutures(futures));
    }
    public static int countFinishedFutures(List<Future> futures) {
        return (int) futures.stream().filter(i -> !i.isCancelled()).count();
        // your code here
    }
}