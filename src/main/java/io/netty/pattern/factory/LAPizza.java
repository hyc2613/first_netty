package io.netty.pattern.factory;

public class LAPizza extends Pizza {
    public LAPizza(VegetableFactory factory) {
        super(factory);
    }

    @Override
    public void prepare() {
        vegetable = factory.getVegetable();
    }
}
