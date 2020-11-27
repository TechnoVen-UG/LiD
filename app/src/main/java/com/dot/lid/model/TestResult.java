package com.dot.lid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TestResult {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int mark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
