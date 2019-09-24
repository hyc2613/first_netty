package io.netty.pattern.factory;

public class NYVegetableFactory implements VegetableFactory {
    @Override
    public Vegetable getVegetable() {
        return new NYVegetable();
    }

    class NYVegetable implements Vegetable {

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
