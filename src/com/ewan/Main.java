package com.ewan;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("what do you want to do today");
            System.out.println("1 play Guess the number 1-100");
            System.out.println("2 sales tax calculator");
            int response = sc.nextInt();
            switch (response) {
                case 1:
                    numberGuessing(sc);
                    break;
                case 2:
                    salesTax(sc);
                    break;
                default:
                    System.out.println("that is not a valid response");
            }
        }
    }

    static void numberGuessing(Scanner sc) {
        System.out.println("Guess a number");
        Random rand = new Random();
        int answer = rand.nextInt(100) + 1;
        int g = 0;
        while (g != answer) {
            g = sc.nextInt();
            if (answer < g) {
                System.out.println("less");
            } else if (answer > g) {
                System.out.println("more");
            }
        }
        System.out.println("you win!!! it was " + answer + " yay!!!");

    }
    static void salesTax(Scanner sc){
        System.out.println("how much is the cost?");
        float cost = sc.nextFloat();
        float tax =cost * 0.0725f;
        System.out.println("the sales tax is "+ tax);
        System.out.println("the total is "+ (cost+tax));

    }
}
