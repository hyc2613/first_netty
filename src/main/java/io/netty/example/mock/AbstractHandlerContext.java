package io.netty.example.mock;

public abstract class AbstractHandlerContext {

    volatile AbstractHandlerContext pre;
    volatile AbstractHandlerContext next;

    public abstract Handler handle();

    public void fireNext(String msg) {
        handle().handle(msg);
        if (next != null) {
            next.fireNext(msg);
        }
    }

}
