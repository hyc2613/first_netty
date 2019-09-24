package io.netty.example.mock;

public class DefaultHandlerContext extends AbstractHandlerContext {

    private Handler handlerInstance;

    public DefaultHandlerContext(Handler handlerInstance) {
        this.handlerInstance = handlerInstance;
    }

    @Override
    public Handler handle() {
        return handlerInstance;
    }
}
