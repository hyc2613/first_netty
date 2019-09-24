package io.netty.pattern.command;

public class DefaultCommand implements Command {
    @Override
    public void execute() {
        System.out.println("do nothing");
    }

    @Override
    public void unod() {
        System.out.println("undo nothing");
    }
}
