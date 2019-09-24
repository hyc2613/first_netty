package io.netty.pattern.factory;

public class LAPizzaStore implements PizzaStore {

    private VegetableFactory vegetableFactory = new LAVegetableFactory();

    @Override
    public Pizza createPizza() {
        Pizza p = new LAPizza(vegetableFactory);
        p.prepare();
        p.cook();
        return p;
    }
}
