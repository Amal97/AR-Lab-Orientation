package com.project.laborientation.Quiz;

public class Category {
    public static final int OSCILLOSCOPE = 1;
    public static final int MULTIMETER = 2;
    public static final int POWER_SUPPLY = 3;
    public static final int WAVEFORM_GENERATOR = 4;

    private int id;
    private String name;
    private int score;

    public Category(){}

    public Category(String name) {
        this.name = name;
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

    public void setScore(int score) {this.score = score;}

    public int getScore() {return score;}
    @Override
    public String toString(){
        return getName();
    }
}
