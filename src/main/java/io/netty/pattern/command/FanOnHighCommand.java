package io.netty.pattern.command;

import io.netty.pattern.command.dianqi.Fan;

public class FanOnHighCommand implements Command {
    private Fan fan;
    private int preSpeed;

    public FanOnHighCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        preSpeed = fan.getSpeed();
        fan.onHigh();
    }

    @Override
    public void unod() {
        fan.onSpeed(preSpeed);
    }
}
