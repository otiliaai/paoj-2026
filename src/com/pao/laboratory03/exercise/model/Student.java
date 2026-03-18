package com.pao.laboratory03.exercise.model;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;
import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private int age;
    private Map<Subject, Double> grades;

    public Student(String name, int age){
        grades = new HashMap<>();
        if (age < 18 || age > 60) throw new InvalidStudentException("Vârsta trebuie să fie între 18 și 60");
        this.name=name;
        this.age=age;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public  Map<Subject, Double> getGrades(){
        return this.grades;
    }

    public void addGrade(Subject subject, double grade){
        if (grade < 1 || grade > 10)
            throw new InvalidGradeException("Nota trebuie să fie între 1 și 10");  // ➜ new

        grades.put(subject,grade);
    }

    public double getAverage() {
        int c=0;
        double s = 0;
        for (var i: grades.entrySet()){
            s+=i.getValue();
            c++;
        }
        if (c>0) return s/c;
        return 0;
    }

    @Override
    public String toString() {
        return "Student{name='" + this.name + "', age=" + this.age + ", avg=" + getAverage() + "}";
    }
}