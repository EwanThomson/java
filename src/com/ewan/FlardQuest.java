package com.ewan;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// make armor upgrade across stages
// 1.
// 2. give the new armor class an instance variable that tracks how upgraded it is
// 3. increment the armor's instance variable every stage

public class FlardQuest {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    Scanner sc;
    Weapon weapon;
    Armor armor = (damage) -> damage;
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
                    int damage = armor.reducedDamage(enemy.attackDamage());
                    if (damage < 0) {
                        damage = 0;
                    }
                    playerHP -= damage;
                    System.out.println("the " + enemy.name + " deals " + damage + " damage");
                }
            }
            if (playerHP < 0) {
                playerHP = 0;
            }
            System.out.println("you have " + playerHP + " HP");
            System.out.println(hpBar(playerHP, ANSI_GREEN_BACKGROUND));

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
                System.out.println(hpBar(enemy.HP, ANSI_RED_BACKGROUND));
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

    String hpBar(int hp, String color) {
        String bar = color;
        for (int i = 0; i < hp; i++) {
            bar += ' ';
        }
        return bar + ANSI_RESET;
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

        System.out.println("the guildmaster offers you a choice of armor.\n" +
                "do you accept? (1 = heavy [percentage damage reduction], 2 = light [flat damage reduction])");
        choice = this.sc.nextInt();
        if (choice == 1) {
            armor = new Heavy();
            System.out.println("the guildmaster gives you heavy armor");
        } else {
            armor = new Light();
            System.out.println("the guildmaster gives you light armor");
        }

        ArrayList<Enemy> pillagerFight = new ArrayList<>();
        pillagerFight.add(new Slime());
        pillagerFight.add(new Pillager());
        if (!fight(pillagerFight)) {
            return;
        }

        System.out.println("the guildmaster offers you a upgrade.\n" +
                "do you accept? (1 = yes, 2 = no)");
        choice = this.sc.nextInt();
        if (choice == 1) {
            weapon = new SwordWand();
            System.out.println("the guildmaster gives you a swordwand!");
        } else {
            System.out.println("you have no need for upgrades, so you carry on with your current gear");
        }

        ArrayList<Enemy> extremeFight = new ArrayList<>();
        extremeFight.add(new Ogre());
        extremeFight.add(new Pillager());
        if (!fight(extremeFight)) {
            return;
        }

        ArrayList<Class<? extends Enemy>> enemyTypes = new ArrayList<>();
        enemyTypes.add(Slime.class);
        enemyTypes.add(Pillager.class);
        enemyTypes.add(Ogre.class);
        Random rand = new Random();
        int stage = 0;
        while (true) {
            stage++;
            ArrayList<Enemy> fight = new ArrayList<>();
            for (int i = 0; i < stage; i++) {
                Enemy enemy;
                try {
                    enemy = enemyTypes.get(rand.nextInt(3)).getDeclaredConstructor().newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
                fight.add(enemy);
            }
            if (!fight(fight)) {
                return;
            }
            if (stage == 5) {
                System.out.println("the guildmaster gives you an upgrade to your armor");
                armor = new Ultimate();
            } else if (stage > 5) {
                System.out.println("the guildmaster offers you a upgrade.\n" +
                        "(1 = upgrade flat, 2 = upgrade percent)");
                choice = this.sc.nextInt();
                if (choice == 1) {
                    // start:   (damage * 0.85) - 0  => 10 -> 8
                    // stage 5: (damage * 0.85) - 5  => 10 -> 3
                    // stage 6: (damage * 0.85) - 11 => 10 -> -3
                    // stage 7: (damage * 0.85) - 12 => 10 -> -4
                    // stage 8: (damage * 0.05) - 13 => 10 -> -5
                    armor.upgradeFlat();
                    System.out.println("the guildmaster gives you the flat upgrade.");
                } else {
                    // start:   (damage * 1.00) - 5  => 10 -> 5
                    // stage 5: (damage * 0.85) - 5  => 10 -> 3
                    // stage 6: (damage * 0.14) - 5  => 10 -> -4
                    // stage 7: (damage * 0.12) - 5  => 10 -> -4
                    // stage 8: (damage * 0.11) - 5  => 10 -> -4
                    armor.upgradePercent();
                    System.out.println("the guildmaster gives you the percent upgrade.");
                }
                    // stage 5: (damage * 0.85) - 5  => 10 -> 3
                    // stage 6: (damage * 0.85) - 11 => 10 -> -3
                    // stage 7: (damage * 0.12) - 5
            }
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

class SwordWand extends Weapon {
    Random rand;

    SwordWand() {
        rand = new Random();
    }

    int attackDamage() {
        if (this.rand.nextInt(5) < 2) {
            return 26;
        } else {
            return 18;
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

interface Armor {
    int reducedDamage(int damage);
    default void upgradeFlat() {
        throw new IllegalStateException("cannot upgrade this armor type");
    }
    default void upgradePercent() {
        throw new IllegalStateException("cannot upgrade this armor type");
    }
}

class Heavy implements Armor {
    public int reducedDamage(int damage) {
        return (int) (damage * 0.85);
    }
}

class Light implements Armor {
    public int reducedDamage(int damage) {
        return damage - 5;
    }
}

class Ultimate implements Armor {
    public int flatUpgradeLevel = 0;
    public int percentUpgradeLevel = 0;
    public int reducedDamage(int damage) {
        float percentReduction = 0.15f + percentUpgradeLevel * 0.05f;
        return (int) (damage * (1.0f - percentReduction)) - (5 + this.flatUpgradeLevel);
    }
    public void upgradeFlat() {
        this.flatUpgradeLevel++;
    }
    public void upgradePercent() {
        if (this.percentUpgradeLevel >= 17) {
            return;
        }
        this.percentUpgradeLevel++;
    }
}