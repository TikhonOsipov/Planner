package com.tixon.planner;

/**
 * Created by tikhon on 18/01/16.
 */
public class Cost extends Money {

    public Cost(String name, double value) {
        super(name, value);
    }

    public Cost(String name, double value, long time) {
        super(name, value, time);
    }
}
