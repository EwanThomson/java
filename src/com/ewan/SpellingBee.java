package com.ewan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SpellingBee {
    static final Runtime runtime = Runtime.getRuntime();
    final Scanner sc;

    SpellingBee(Scanner sc) {
        this.sc = sc;
    }

    void run() {
        Random rand = new Random();
        List<String> words;
        try {
            words = Files.readAllLines(Path.of("/Users/ewan/Documents/GitHub/java/spelling_bee.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            String word = words.get(rand.nextInt(words.size()));
            String hint = "";
            for (int i = 0; i < word.length(); i++) {
                hint += '_';
            }
            System.out.println("try to spell this word");
            if (this.sc.hasNextLine()) {
                this.sc.nextLine();
            }
            for (int i = 0; i < 3; i++) {
                say(word);
                System.out.println(hint);
                String choice = this.sc.nextLine();
                if (choice.equals(word)) {
                    System.out.println("good job");
                } else if (i < 2) {
                    System.out.println("try again. this was attempt number " + (i + 1));
                    boolean foundUnrevealedIndex = false;
                    int index;
                    do {
                        index = rand.nextInt(word.length());
                        foundUnrevealedIndex = hint.charAt(index) != '_';
                    } while (foundUnrevealedIndex);
                    String newHint = "";
                    for (int j = 0; j < word.length(); j++) {
                        if (j == index) {
                            newHint += word.charAt(j);
                        } else {
                            newHint += hint.charAt(j);
                        }
                    }
                    hint = newHint;
                } else {
                    System.out.println("sorry you couldn't spell " + word);
                }
            }
        }
    }

    static void say(String word) {
        try {
            runtime.exec(new String[]{"say", word});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
