package io.netty.pattern.decorate;

public class Paper {

    private StringBuilder sb = new StringBuilder();

    public void add(String compoment) {
        sb.append(compoment).append("#");
    }

    public void print() {
        System.out.println(sb);
    }
}
