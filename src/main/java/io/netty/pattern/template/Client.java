package io.netty.pattern.template;

import org.junit.Test;

public class Client {

    @Test
    public void testTemplateMethod() {
        Tea tea = new Tea();
        Coffee coffee = new Coffee();

        tea.prepareNow();
        coffee.prepareNow();
    }

}
