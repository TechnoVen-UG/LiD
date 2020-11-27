package com.dot.lid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CacheQuestion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String categoryName;
    private int questionNo;
    private int selectedNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public int getSelectedNo() {
        return selectedNo;
    }

    public void setSelectedNo(int selectedNo) {
        this.selectedNo = selectedNo;
    }
}
