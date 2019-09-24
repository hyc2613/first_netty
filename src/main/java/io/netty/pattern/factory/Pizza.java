package io.netty.pattern.factory;

public abstract class Pizza {
    protected Vegetable vegetable;
    protected VegetableFactory factory;

    public Pizza(VegetableFactory factory) {
        this.factory = factory;
    }

    public abstract void prepare();

    public void cook() {
        System.out.println("cook" );
    }

    public void onDish() {
        System.out.println("onDish");
    }

    public String toString() {
        return "food's "+vegetable;
    }

}
