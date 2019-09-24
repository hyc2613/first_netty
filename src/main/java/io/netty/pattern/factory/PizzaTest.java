package io.netty.pattern.factory;

import org.junit.Test;

public class PizzaTest {

    @Test
    public void testPizzaStore() {
        PizzaStore laStore = new LAPizzaStore();
        Pizza laPizza = laStore.createPizza();
        System.out.println(laPizza);

        PizzaStore nyStore = new NYPizzaStore();
        Pizza nyPizza = nyStore.createPizza();
        System.out.println(nyPizza);
    }

}
