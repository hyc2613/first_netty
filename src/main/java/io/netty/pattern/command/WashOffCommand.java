package io.netty.pattern.command;

import io.netty.pattern.command.dianqi.Washer;

public class WashOffCommand implements Command {
    private Washer washer;

    public WashOffCommand(Washer washer) {
        this.washer = washer;
    }

    @Override
    public void execute() {
        washer.off();
    }

    @Override
    public void unod() {
        washer.on();
    }
}
