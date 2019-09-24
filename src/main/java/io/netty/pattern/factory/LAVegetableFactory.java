package io.netty.pattern.factory;

public class LAVegetableFactory implements VegetableFactory {
    @Override
    public Vegetable getVegetable() {
        return new LAVegetable();
    }

    class LAVegetable implements Vegetable {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
