import io.netty.example.executor.MyThreadPoolExecutor;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReturnBlock {

    public static final int count_bits = Integer.SIZE - 3;
    public static final int capaticy = (1 << count_bits) - 1;
    public static final int running = -1 << count_bits;
    public static final int shutdown = 1 << count_bits;
    public static final int stop = 2 << count_bits;
    public static final int dydl = 3 << count_bits;
    public static final AtomicInteger ctl = new AtomicInteger(ctlOf(running, 0));

    @Test
    public void testReturn() throws InterruptedException {
        // weiyi();
        poolExecute();
//        xx(null, "dd");
    }

    static void xx(@NotNull String i, String j) {
        System.out.println(i+"_"+j);
    }

    public static void main(String[] args) {
        ReturnBlock lock = new ReturnBlock();
        lock.poolExecute();
    }

    void poolExecute() {
        long beginTime = System.currentTimeMillis();
        ThreadFactory factory = new ThreadFactory() {
            AtomicInteger i = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("thread_pool_"+i.getAndIncrement());
                return t;
            }
        };

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), factory, new DiscardTaskHandler());
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(5, 10, new LinkedBlockingQueue<>(10), factory);

        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Runnable r = new MyRunnable(i);
            executor.execute(r);
        };
        System.out.println(System.currentTimeMillis() - beginTime);
    }

    class MyRunnable implements Runnable {
            private int i = 0;
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"_begin_runnable_"+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"_end_runnable_"+i);
            }

            @Override
            public String toString() {
                return "runnable_"+i;
            }

        public MyRunnable(int i) {
            this.i = i;
        }
    }

    class DiscardTaskHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("discard:"+r.toString());
        }
    }

    static void queue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(20);
        for (int i = 0; i < 10; i++) {
            queue.offer(i);
        }
        while (!queue.isEmpty()) {
            int dd = queue.remove();
            System.out.println(dd);
        }

    }



    static void weiyi() {

        System.out.println("count_bits="+count_bits+"_capaticy="+capaticy);

        System.out.println("running="+running+"shutdown="+shutdown+"stop="+stop+"dydl="+dydl);

        System.out.println("ctl="+ctl+"runWorkers="+runWorkers(ctl.get())+"stateNum="+stateNum(ctl.get()));
        ctl.incrementAndGet();
        ctl.incrementAndGet();
        ctl.incrementAndGet();
        System.out.println("ctl="+ctl+"runWorkers="+runWorkers(ctl.get())+"stateNum="+stateNum(ctl.get()));
    }

    private static int ctlOf(int rs, int wc) { return rs | wc; }

    private static int runWorkers(int c) {
        return c & capaticy;
    }

    private static int stateNum(int c) {
        return c & ~capaticy;
    }

    public interface NotNullInterface{
        void execute(@NotNull Runnable r);
    }
}
