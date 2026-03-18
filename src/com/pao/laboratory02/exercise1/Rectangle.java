package com.pao.laboratory02.exercise1;

/**
 * TODO: Implementează Rectangle extends Shape.
 * - Atribute: private double width, height
 * - Constructor: super("Rectangle"), this.width, this.height
 * - area() = width * height
 * - perimeter() = 2 * (width + height)
 */
public class Rectangle extends Shape {

    private double width, height;

    public Rectangle(double width, double height) {
        super("Rectangle");
        this.width = width; this.height = height;
    }

    @Override
    public double area() {
        double a =  width * height;
        return a; //
    }

    @Override
    public double perimeter() {
        double p = 2 * (width + height);
        return p;
    }
}
