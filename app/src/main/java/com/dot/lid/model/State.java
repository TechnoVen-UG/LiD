package com.dot.lid.model;

public class State {
    private int name;
    private int flag;

    public State(int name, int flag) {
        this.name = name;
        this.flag = flag;
    }

    public int getName() {
        return name;
    }

    public int getFlag() {
        return flag;
    }
}
