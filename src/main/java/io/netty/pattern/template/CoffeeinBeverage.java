package io.netty.pattern.template;

public abstract class CoffeeinBeverage {

    /**
     * 这是模版方法，这里会调用一系列的方法
     */
    public final void prepareNow() {
        boilWater();
        rushed();
        pushOnDish();
        if (isNeedAddCondiments()) {
            addCondiments();
        }
    }

    // 烧开水，这个是统一的，由父类来实现，且不允许修改继承
    protected final void boilWater() {
        System.out.println("boil water...");
    }
    // 同样的，不允许继承修改
    protected final void pushOnDish() {
        System.out.println("push on dish");
    }

    // 冲泡，让子类自己去实现
    protected abstract void rushed();

    // 增加调味品，让子类自己去实现
    protected abstract void addCondiments();

    // 钩子方法，父类提供一个默认实现，通常会影响模版方法的逻辑。由子类自行决定是否要覆写
    protected boolean isNeedAddCondiments() {
        return true;
    }
}
