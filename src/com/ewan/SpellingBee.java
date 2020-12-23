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
            words = Files.readAllLines(Path.of("/usr/share/dict/words"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            String word = words.get(rand.nextInt(words.size()));
            System.out.println("try to spell this word");
            say(word);
            for (int i = 0; i < 3; i++) {
                String choice = this.sc.nextLine();
                if (choice.equals(word)) {
                    System.out.println("good job");
                } else if (i < 2) {
                    System.out.println("try again. this was attempt number " + i);
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
