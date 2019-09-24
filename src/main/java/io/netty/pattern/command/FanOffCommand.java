package io.netty.pattern.command;

import io.netty.pattern.command.dianqi.Fan;

public class FanOffCommand implements Command {
    private Fan fan;
    private int preSpeed;

    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        preSpeed = fan.getSpeed();
        fan.off();
    }

    @Override
    public void unod() {
        fan.onSpeed(preSpeed);
    }
}
