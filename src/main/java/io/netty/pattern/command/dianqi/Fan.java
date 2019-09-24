package io.netty.pattern.command.dianqi;

public class Fan {

    public static final int HIGH = 3;
    public static final int MIDDLE = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;
    private int speed;

    public void onHigh() {
        this.speed = HIGH;
        System.out.println("fan on speed:"+speed);
    }

    public void onMiddel() {
        this.speed = MIDDLE;
        System.out.println("fan on speed:"+speed);
    }

    public void onLow() {
        this.speed = LOW;
        System.out.println("fan on speed:"+speed);
    }

    public void off() {
        this.speed = OFF;
        System.out.println("fan off ");
    }

    public void onSpeed(int preSpeed) {
        switch (preSpeed) {
            case Fan.HIGH:
                this.onHigh();
                break;
            case Fan.MIDDLE:
                this.onMiddel();
                break;
            case Fan.LOW:
                this.onLow();
                break;
            default:
                this.off();
        }
    }

    public int getSpeed() {
        return speed;
    }
}
