package com.tixon.planner;

/**
 * Created by tikhon on 22/01/16.
 */
public class Saving extends Money {
    public Saving(String name, double value) {
        super(name, value);
    }

    public Saving(String name, double value, long time) {
        super(name, value, time);
    }
}
