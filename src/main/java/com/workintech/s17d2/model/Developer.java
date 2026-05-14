package com.workintech.s17d2.model;
public class Developer {
    private int id;
    private String name;
    private double salary;
    private Experience experience;

    public Developer(int id, String name, double salary, Experience experience) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.experience = experience;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Experience getExperience() {
        return experience;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }
}

