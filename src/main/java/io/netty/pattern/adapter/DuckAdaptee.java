package io.netty.pattern.adapter;

import org.jetbrains.annotations.NotNull;

/**
 * 适配器类。
 * 持有一个被适配的对象，并且实现适配器接口。将接口方法委托给被适配者。
 */
public class DuckAdaptee implements Adapter {
    private Adaptee chicken;

    public DuckAdaptee(@NotNull Adaptee chicken) {
        this.chicken = chicken;
    }

    @Override
    public void guagua() {
        chicken.jiji();
    }

    @Override
    public void swim() {
        throw new UnsupportedOperationException("can't swim!");
    }
}
