package com.project.laborientation.Quiz;

public class QuizTracker {

    public static final int OSCILLOSCOPE = 1;
    public static final int MULTIMETER = 2;
    public static final int POWER_SUPPLY = 3;
    public static final int WAVEFORM_GENERATOR = 4;
    public static final int PHOEBE = 5;


    private int id;
    private String name;
    private int totalQuestions;
    private int correctAnswered;

    public QuizTracker(){}

    public QuizTracker(int categoryID, String name, int totalQuestions) {
        this.id = categoryID;
        this.name = name;
        this.totalQuestions = totalQuestions;
        this.correctAnswered = 0;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswered() {
        return correctAnswered;
    }

    public void setCorrectAnswered(int correctAnswered) {
        this.correctAnswered = correctAnswered;
    }
}
