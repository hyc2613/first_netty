package io.netty.pattern.decorate;

import org.junit.Test;

public class Client {

    @Test
    public void testDrawer() {
        Paper paper = new Paper();
        Drawer d1 = new PersonDrawer();
        Drawer d2 = new CloudDrawer(d1);
        Drawer d3 = new SunDrawer(d2);
        d3.draw(paper);
        paper.print();



    }

}
