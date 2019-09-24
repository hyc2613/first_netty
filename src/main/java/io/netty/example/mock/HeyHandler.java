package io.netty.example.mock;

public class HeyHandler implements Handler {
    @Override
    public void handle(String msg) {
        System.out.println("hey," + msg);
    }
}
