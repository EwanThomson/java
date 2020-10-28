package com.ewan;

import java.util.Random;
import java.util.Scanner;

public class FlardQuest {
    Scanner sc;

    FlardQuest(Scanner sc) {
        this.sc = sc;
    }

    void run() {
        System.out.println("you are at the adventurer's guild\n" +
                "the guildmaster offers you a sword or a wand\n" +
                "which do you choose? (1 = sword, 2 = wand)");
        int choice = this.sc.nextInt();
        Weapon weapon;
        if (choice == 1) {
            weapon = new Sword();
        } else if (choice == 2) {
            weapon = new Wand();
        } else {
            System.out.println("the guildmaster is very disappointed in you");
            return;
        }
        int playerHP = 100;
        int slimeHP = 24;
        System.out.println("you head to the woods and find a slime");
        while (slimeHP > 0) {
            System.out.println("fight or run? (1 = fight, 2 = run)");
            choice = this.sc.nextInt();
            if (choice == 1) {
                int damage = weapon.attackDamage();
                slimeHP -= damage;
                if (slimeHP < 0) {
                    slimeHP = 0;
                }
                System.out.println("you deal " + damage + " damage to the slime. it now has " + slimeHP + " HP");
            } else if (choice == 2) {
                System.out.println("the slime kills you! you lose");
                return;
            }
        }
        int ogreHP = 30;
        System.out.println("as you venture farther into the woods, you stumble upon an ogre");
        while (ogreHP > 0 && playerHP > 0) {
            System.out.println("fight or run? (1 = fight, 2 = run)");
            choice = this.sc.nextInt();
            playerHP -= 15;
            if (choice == 1) {
                int damage = weapon.attackDamage();
                ogreHP -= damage;
                if (ogreHP < 0) {
                    ogreHP = 0;
                }
                System.out.println("you deal " + damage + " damage to the ogre. it now has " + ogreHP + " HP.\n" +
                        "You have " + playerHP + " HP");
            } else if (choice == 2) {
                System.out.println("the ogre kills you! you lose");
                return;
            }
        }
        if (playerHP > 0) {
            System.out.println("you win!\n");
        } else {
            System.out.println("you lose.\n");
        }
    }
}

abstract class Weapon {
    abstract int attackDamage();
}

class Sword extends Weapon {
    int attackDamage() {
        return 10;
    }
}

class Wand extends Weapon {
    Random rand;

    Wand() {
        rand = new Random();
    }

    int attackDamage() {
        if (this.rand.nextInt(5) < 2) {
            return 16;
        } else {
            return 8;
        }
    }
}