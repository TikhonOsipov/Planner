package com.tixon.planner;

/**
 * Created by tikhon on 18/01/16.
 */
public class Money {
    private String name;
    private double value;
    private long time;

    public Money(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public Money(String name, double value, long time) {
        this(name, value);
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
