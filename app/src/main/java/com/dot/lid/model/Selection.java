package com.dot.lid.model;

public class Selection {
    private int selectionNumber;
    private Question question;

    public Selection() {
    }

    public Selection(int selectionNumber, Question question) {
        this.selectionNumber = selectionNumber;
        this.question = question;
    }

    public int getSelectionNumber() {
        return selectionNumber;
    }

    public void setSelectionNumber(int selectionNumber) {
        this.selectionNumber = selectionNumber;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "selectionNumber=" + selectionNumber +
                ", question=" + question +
                '}';
    }
}
