package com.pao.laboratory02.exercise1;

/**
 * TODO: Implementează Circle extends Shape.
 * - Atribut: private double radius
 * - Constructor: super("Circle"), this.radius = radius
 * - area() = Math.PI * radius * radius
 * - perimeter() = 2 * Math.PI * radius
 */
public class Circle extends Shape {

    private double radius;

    public Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }

    @Override
    public double area() {
        double a = Math.PI * radius * radius;
        return a;
    }

    @Override
    public double perimeter() {
        double p = 2 * Math.PI * radius;
        return p;
    }
}
