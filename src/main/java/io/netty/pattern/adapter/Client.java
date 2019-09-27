package io.netty.pattern.adapter;

import org.junit.Test;

public class Client {

    private Adapter adapter;

    @Test(expected=UnsupportedOperationException.class)
    public void test() {
        Adaptee chicken = new Adaptee();
        adapter = new DuckAdaptee(chicken);
        adapter.guagua();
        adapter.swim();
    }

}
