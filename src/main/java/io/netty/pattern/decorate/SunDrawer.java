package io.netty.pattern.decorate;


public class SunDrawer extends DetailDrawer {
    Drawer drawer;

    public SunDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public Paper draw(Paper paper) {
        drawer.draw(paper).add("sum");
        return paper;
    }
}
