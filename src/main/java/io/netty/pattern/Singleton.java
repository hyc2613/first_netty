package io.netty.pattern;

public class Singleton {
    private volatile static Singleton instance;

    private Singleton() {}

    private static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                // double check
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
