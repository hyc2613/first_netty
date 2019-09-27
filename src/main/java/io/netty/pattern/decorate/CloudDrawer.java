package io.netty.pattern.decorate;

public class CloudDrawer extends DetailDrawer {

    Drawer drawer;

    public CloudDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public Paper draw(Paper paper) {
        drawer.draw(paper).add("cloud");
        return paper;
    }
}
