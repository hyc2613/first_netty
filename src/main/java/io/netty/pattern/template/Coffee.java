package io.netty.pattern.template;

public class Coffee extends CoffeeinBeverage {
    @Override
    protected void rushed() {
        System.out.println("rush coffee bean...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("add sugar and milk...");
    }


}
