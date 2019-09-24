package io.netty.pattern.command;

import io.netty.pattern.command.dianqi.Fan;
import io.netty.pattern.command.dianqi.Washer;

public class Slot {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command lastPressedCommand;

    public Slot(int count) {
        onCommands = new Command[count];
        offCommands = new Command[count];
        Command command = new DefaultCommand();
        for (int i = 0; i < count; i++) {
            onCommands[i] = command;
            offCommands[i] = command;
        }
    }

    public void setCommand(int index, Command onCommand, Command offCommand) {
        onCommands[index] = onCommand;
        offCommands[index] = offCommand;
    }

    public void onButtonPress(int i) {
        lastPressedCommand = onCommands[i];
        onCommands[i].execute();
    }
    public void offButtonPress(int i) {
        lastPressedCommand = offCommands[i];
        offCommands[i].execute();
    }
    public void cancelButtonPress(int i) {
        lastPressedCommand.unod();
    }

    public static void main(String[] args) {
        Slot slot = new Slot(5);
        Washer washer = new Washer();
        Command washerOn = new WashOnCommand(washer);
        Command washerOff = new WashOffCommand(washer);
        slot.setCommand(0, washerOn, washerOff);

        Fan fan = new Fan();
        Command fanOn = new FanOnHighCommand(fan);
        Command fanOff = new FanOffCommand(fan);
        slot.setCommand(1, fanOn, fanOff);

        slot.onButtonPress(0);
        slot.offButtonPress(0);
        slot.cancelButtonPress(0);
    }
}
