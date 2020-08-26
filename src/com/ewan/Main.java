package com.ewan;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Guess a number");
        Scanner sc = new Scanner(System.in);
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
}
