package com.tixon.planner;

/**
 * Created by tikhon on 18/01/16.
 */
public class Income extends Money {

    public Income(String name, double value) {
        super(name, value);
    }

    public Income(String name, double value, long time) {
        super(name, value, time);
    }
}
