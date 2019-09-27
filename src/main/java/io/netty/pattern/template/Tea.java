package io.netty.pattern.template;

public class Tea extends CoffeeinBeverage {
    @Override
    protected void rushed() {
        System.out.println("rush tea with water");
    }

    @Override
    protected void addCondiments() {
        System.out.println("add lemon ...");
    }

    @Override
    protected boolean isNeedAddCondiments() {
        return false;
    }
}
