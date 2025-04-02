// define the interface

import java.util.Scanner;

public interface calcApp {
    void add(double value);
    void subtract(double value);
    void multiply(double value);
    void divide(double value);
    void mod (double value);
    double getResult();
}

// implement the interface
class simpleCalc implements calcApp {
    private double result;

    public simpleCalc() {
        this.result = 0;
    }

    @Override
    public void add (double value) {
        result += value;
    }

    @Override
    public void subtract (double value) {
        result -= value;
    }

    @Override
    public void multiply (double value) {
        result *= value;
    }

    @Override
    public void divide (double value) {
        if (value != 0) {
            result /= value;
        } else {
            System.out.println("Error: Division by zero");
        }
    }

    @Override
    public void mod (double value) {
        if (value != 0) {
            result %= value;
        } else {
            System.out.println("Error: Division by zero");
        }
    }

    @Override
    public double getResult () {
        return result;
    }

}

// main class to test the calculator
class CalcAppMain {
    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);
        simpleCalc calc = new simpleCalc();
        boolean running = true;

        System.out.println("Simple Calculator - Type Operations (+5, -3, *2, /4, %5) or = to display result.");
        
        while (running) {
            System.out.println("Enter operation:");
            String input = scan.next();

            if (input.equals("=")) {
                System.out.println("Final Result: " + calc.getResult());
                running = false;
            }
            else {
                double value = scan.nextDouble();
                switch (input) {
                    case "+":
                        calc.add(value);
                        break;
                    case "-":
                        calc.subtract(value);
                        break;
                    case "*":
                        calc.multiply(value);
                        break;
                    case "/":
                        calc.divide(value);
                        break;
                    case "%":
                        calc.mod(value);
                        break;
                    default:
                        System.out.println("Invalid operation. Please use +, -, *, or /.");
                        break;
                }
                System.out.println("Current Result: " + calc.getResult());
            }
        }

    }
}