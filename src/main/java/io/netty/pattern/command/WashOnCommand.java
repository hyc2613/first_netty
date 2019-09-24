package io.netty.pattern.command;

import io.netty.pattern.command.Command;
import io.netty.pattern.command.dianqi.Washer;

public class WashOnCommand implements Command {
    private Washer washer;

    public WashOnCommand(Washer washer) {
        this.washer = washer;
    }

    @Override
    public void execute() {
        washer.putWater();
        washer.on();
    }

    @Override
    public void unod() {
        washer.off();
    }
}
