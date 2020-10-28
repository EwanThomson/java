package com.ewan;

import java.util.Random;
import java.util.Scanner;

public class FlardQuest {
    Scanner sc;
    Weapon weapon;
    int playerHP = 100;

    FlardQuest(Scanner sc) {
        this.sc = sc;
    }

    boolean fight(Enemy enemy) {
        while (enemy.HP > 0 && playerHP > 0) {
            System.out.println("fight or run? (1 = fight, 2 = run)");
            int choice = this.sc.nextInt();
            playerHP -= 15;
            if (choice == 1) {
                int damage = weapon.attackDamage();
                enemy.HP -= damage;
                if (enemy.HP < 0) {
                    enemy.HP = 0;
                }
                System.out.println("you deal " + damage + " damage to the " + enemy.name + ". it now has " + enemy.HP + " HP.\n" +
                        "You have " + playerHP + " HP");
            } else if (choice == 2) {
                System.out.println("the " + enemy.name + " kills you! you lose");
                return false;
            }
        }
        if (playerHP > 0) {
            return true;
        } else {
            System.out.println("you lose.\n");
            return false;
        }
    }

    void run() {
        System.out.println("you are at the adventurer's guild\n" +
                "the guildmaster offers you a sword or a wand\n" +
                "which do you choose? (1 = sword, 2 = wand)");
        int choice = this.sc.nextInt();

        if (choice == 1) {
            weapon = new Sword();
        } else if (choice == 2) {
            weapon = new Wand();
        } else {
            System.out.println("the guildmaster is very disappointed in you");
            return;
        }

        System.out.println("you head to the woods and find a slime");
        if (!fight(new Slime())) {
            return;
        };
        if (!fight(new Ogre())) {
            return;
        }
        System.out.println("you win!\n");
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

abstract class Enemy {
    String name;
    int HP;
    abstract int attackDamage();
}

class Slime extends Enemy {
    Slime() {
        this.name = "slime";
        this.HP = 24;
    }
    int attackDamage() {
        return 1;
    }
}
class Ogre extends Enemy {
    Ogre() {
        this.name = "ogre";
        this.HP = 30;
    }
    int attackDamage() {
        return 15;
    }
}
