package com.csu.tankbattle.util;

public class Integrator {
    private static Integrator integrator = new Integrator();
    private String username;
    private int score;
    private String result;

    private Integrator() {}

    public static Integrator getInstance(){
        return integrator;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {return this.username;}

    public void addScore(int s) {
        score += s;
    }

    public int getScore() {
        return score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
