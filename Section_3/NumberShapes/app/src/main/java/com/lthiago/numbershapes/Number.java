package com.lthiago.numbershapes;

class Number {

    private int number;

    int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    boolean isTriangular() {
        int x = 1;
        int triangularNumber = 1;

        while (triangularNumber < number) {
            x++;
            triangularNumber = triangularNumber + x;
        }

        return triangularNumber == number;
    }

    boolean isSquare() {
        double squareRoot = Math.sqrt(number);

        return squareRoot == Math.floor(squareRoot);
    }


}
