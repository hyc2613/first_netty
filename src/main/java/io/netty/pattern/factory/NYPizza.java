package io.netty.pattern.factory;

public class NYPizza extends Pizza {

    public NYPizza(VegetableFactory factory) {
        super(factory);
    }

    @Override
    public void prepare() {
        vegetable = super.factory.getVegetable();
    }

    public void cook() {
        System.out.println("cook NEWYORK" );
    }
}
