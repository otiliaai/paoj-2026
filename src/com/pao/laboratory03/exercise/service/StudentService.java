package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private static StudentService instance;
    private List<Student> students;

    private StudentService() {
        students= new ArrayList<>();
    }

    public static StudentService getInstance() {
        if (instance==null) {
            instance= new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student student: students) {
            if (student.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Exista deja un student cu numele " + name);
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        throw new StudentNotFoundException("Studentul cu numele " + name + " nu a fost gasit");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
            if (student.getGrades().isEmpty()) {
                System.out.println("  Note: nu are note");
            } else {
                System.out.println("  Note:");
                for (Map.Entry<Subject, Double> entry : student.getGrades().entrySet()) {
                    System.out.println("    " + entry.getKey().name() + " -> " + entry.getValue());
                }
            }
        }
    }

    public void printTopStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti.");
            return;
        }

        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort(Comparator.comparingDouble(Student::getAverage).reversed());

        for (Student student : sortedStudents) {
            System.out.println(student);
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> sums = new EnumMap<>(Subject.class);
        Map<Subject, Integer> counts = new EnumMap<>(Subject.class);
        Map<Subject, Double> averages = new EnumMap<>(Subject.class);

        for (Student student : students) {
            for (Map.Entry<Subject, Double> entry : student.getGrades().entrySet()) {
                Subject subject = entry.getKey();
                Double grade = entry.getValue();
                sums.put(subject, sums.getOrDefault(subject, 0.0) + grade);
                counts.put(subject, counts.getOrDefault(subject, 0) + 1);
            }
        }
        for (Subject subject:sums.keySet()) {
            averages.put(subject, sums.get(subject) / counts.get(subject));
        }

        return averages;
    }
}