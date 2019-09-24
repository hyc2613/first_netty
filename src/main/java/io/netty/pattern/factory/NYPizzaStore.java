package io.netty.pattern.factory;

public class NYPizzaStore implements PizzaStore {

    private VegetableFactory vegetableFactory = new NYVegetableFactory();

    @Override
    public Pizza createPizza() {
        Pizza p = new NYPizza(vegetableFactory);
        p.prepare();
        p.cook();
        return p;
    }
}
