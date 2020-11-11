package com.ewan;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// 1. add more fights
// 2. add items (healing potions, weapon upgrades)

public class FlardQuest {
    Scanner sc;
    Weapon weapon;
    int playerHP = 100;

    FlardQuest(Scanner sc) {
        this.sc = sc;
    }


    boolean fight(ArrayList<Enemy> enemies) {
        int healingTime = 3;
        for (Enemy enemy : enemies) {
            System.out.println("you see a(n) " + enemy.name);
        }

        boolean anyEnemyLiving = true;
        while (anyEnemyLiving && playerHP > 0) {
            healingTime++;
            System.out.println("fight or heal? (1 = fight, 2 = heal)");
            int choice = this.sc.nextInt();

            for (Enemy enemy : enemies) {
                if (enemy.HP > 0) {
                    int damage = enemy.attackDamage();
                    playerHP -= damage;
                    System.out.println("the " + enemy.name + " deals " + damage + " damage");
                }
            }
            if (playerHP < 0) {
                playerHP = 0;
            }
            System.out.println("you have " + playerHP + " HP");

            // a: you deal damage (choice == 1|| choice == 2 && healingTime<3)
            // b: you heal (choice == 2 && healingTime >= 3)
            if (choice == 2 && healingTime >= 3) {
                playerHP = 100;
            } else {
                for (Enemy enemy : enemies) {
                    if (enemy.HP > 0) {
                        int damage = weapon.attackDamage();
                        enemy.HP -= damage;
                        if (enemy.HP < 0) {
                            enemy.HP = 0;
                        }
                        System.out.println("you deal " + damage + " damage to the " + enemy.name + ". it now has " + enemy.HP + " HP.");
                        break;
                    }
                }
            }

            anyEnemyLiving = false;
            for (Enemy enemy : enemies) {
                if (enemy.HP > 0) {
                    anyEnemyLiving = true;
                }
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
        ArrayList<Enemy> slimeFight = new ArrayList<>();
        slimeFight.add(new Slime());
        if (!fight(slimeFight)) {
            return;
        }
        ArrayList<Enemy> pillagerFight = new ArrayList<>();
        pillagerFight.add(new Slime());
        pillagerFight.add(new Pillager());
        if (!fight(pillagerFight)) {
            return;
        }
        ArrayList<Enemy> extremeFight = new ArrayList<>();
        extremeFight.add(new Ogre());
        extremeFight.add(new Pillager());
        if (!fight(extremeFight)) {
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
        return 5;
    }
}

class Pillager extends Enemy {
    Pillager() {
        this.name = "pillager";
        this.HP = 15;
    }

    int attackDamage() {
        return 15;
    }
}

class Ogre extends Enemy {
    Ogre() {
        this.name = "ogre";
        this.HP = 30;
    }

    int attackDamage() {
        return 10;
    }
}
