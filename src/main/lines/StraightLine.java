package main.lines;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 04.05.16.
 */
public class StraightLine implements Serializable{

    private int a;
    private int b;
    private int c;

    public StraightLine(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public StraightLine(int x1, int y1, int x2, int y2) {
        a = y1 - y2;
        b = x2 - x1;
        c = a * x1 + b * y1;
    }

    public Point intersect(StraightLine line) {
        int numeratorX = getC() * line.getB() - line.getC() * getB();
        int numeratorY = getA() * line.getC() - line.getA() * getC();
        int denominator = getA() * line.getB() - getB() * line.getA();
        if (denominator == 0) {
            return new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        int x = numeratorX / denominator;
        int y = numeratorY / denominator;
        return new Point(x, y);
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

}
