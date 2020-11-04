package com.ewan;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FlardQuest {
    Scanner sc;
    Weapon weapon;
    int playerHP = 100;

    FlardQuest(Scanner sc) {
        this.sc = sc;
    }

    boolean fight(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            System.out.println("you see a(n) " + enemy.name);
        }
        boolean anyEnemyLiving = true;
        while (anyEnemyLiving && playerHP > 0) {
            System.out.println("fight or run? (1 = fight, 2 = run)");
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

            if (choice == 1) {
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
            } else if (choice == 2) {
                System.out.println("the enemies kills you! you lose");
                return false;
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
        ArrayList<Enemy> ogreFight = new ArrayList<>();
        ogreFight.add(new Slime());
        ogreFight.add(new Pillager());
        if (!fight(ogreFight)) {
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
