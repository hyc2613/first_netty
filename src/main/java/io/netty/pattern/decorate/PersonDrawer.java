package io.netty.pattern.decorate;

public class PersonDrawer implements Drawer {
    @Override
    public Paper draw(Paper paper) {
        paper.add("a person");
        return paper;
    }
}
