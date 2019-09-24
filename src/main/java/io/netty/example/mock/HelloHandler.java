package io.netty.example.mock;

public class HelloHandler implements Handler {
    @Override
    public void handle(String msg) {
        System.out.println("hello, "+ msg);
    }
}
