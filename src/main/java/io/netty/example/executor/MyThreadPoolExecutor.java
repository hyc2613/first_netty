package io.netty.example.executor;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPoolExecutor extends AbstractExecutorService {

    private int corePoolSize;
    private int maxiumPoolSize;
    private BlockingQueue<Runnable> workQueue;
    private HashSet<Worker> workers;
    private ThreadFactory threadFactory;
    private AtomicInteger currentWorkerCount = new AtomicInteger(0);
    private final ReentrantLock mainLock = new ReentrantLock();

    public MyThreadPoolExecutor(int corePoolSize, int maxiumPoolSize, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this.corePoolSize = corePoolSize;
        this.maxiumPoolSize = maxiumPoolSize;
        this.workQueue = workQueue;
        this.workers = new HashSet<>();
        this.threadFactory = threadFactory;
    }

    class Worker implements Runnable {
        Runnable firstTask;
        Thread currentThread;

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.currentThread = threadFactory.newThread(this);
        }

        @Override
        public void run() {
            runWork(this);
        }
    }

    private void runWork(Worker w) {
        try {
            Runnable task = w.firstTask;
            // 当前有任务就执行，没任务就从队列中取。
            while (task != null || (task = getTask()) != null) {
                try {
                    task.run();
                } finally {
                    task = null;
                }
            }
        } finally {
            //关闭 work
            closeWork(w);
        }
    }

    private void closeWork(Worker w) {
        if (currentWorkerCount.get() <= corePoolSize) {
            return;
        }
        else {
            workers.remove(w);
            currentWorkerCount.decrementAndGet();
        }
    }

    private boolean addWorker(Runnable task, boolean isCorePool) {
        Worker w = new Worker(task);
        Thread t = w.currentThread;
        int currentWorkerCount = this.currentWorkerCount.get();
        int legalWorkerCount = isCorePool ? corePoolSize : maxiumPoolSize;
        if (currentWorkerCount >= legalWorkerCount) {
            return false;
        }
        else {
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                workers.add(w);
                this.currentWorkerCount.incrementAndGet();
            }finally {
                mainLock.unlock();
            }
            t.start();
        }
        return true;
    }

    private Runnable getTask() {
        try {
            return workQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void execute(@NotNull Runnable command) {
        if (command == null) {
            return;
        }
        if (currentWorkerCount.get() < corePoolSize && addWorker(command, true)) {
            // System.out.println("corePoolSize:"+command.toString());
            return;
        }
        else if (workQueue.offer(command)) {
            // System.out.println("put queue :"+command.toString());
            return;
        }
        else if (!addWorker(command, false)) {
            reject(command);
        }
    }

    private void reject(Runnable command) {
        System.out.println(command.toString()+" has been discarded");
    }

    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.currentThread;
                if (!t.isInterrupted()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                    }
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    @NotNull
    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }


}
