package classes;

import java.awt.image.BufferedImage;
import java.util.*;
import classes.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class Player extends JLabel {
    private int credits;
    private int money;
    private int chips;
    private Die dice;
    private Set location;
    private Roles role;
    private int id;
    private static String[] idToColor = {
            "b",
            "c",
            "g",
            "o",
            "p",
            "r",
            "v",
            "w",
            "y"
    };

    public Player(int numCredits, int rank) {
        credits = numCredits;
        chips = 0;
        role = null;
        dice = new Die();
        dice.setRank(rank);
        location = null;
    }

    int rollDice() {
            return dice.rollDie() + chips;
    };

    public void setid(int id) {
        this.id = id;
        try {
            BufferedImage myPicture = ImageIO.read(new File("./images/dice/" + idToColor[id - 1] + "1.png"));
            setIcon(new ImageIcon(myPicture));
            setSize(40, 40);
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public int getid() {
        return id;
    }

    public boolean move(Set set) {
        // verify that set is adjacent to the location
        // if so, move the player to the set
        // if not, return false

        if (this.location.isAdjacent(set) && role == null) {
            return Systems.getInstance().move(this, set);
        }
        return false;
    };

    public boolean useTurn(int choice) {
        // if choice is 1, rehearse
        // if choice is 2, act
        // if choice is 3, upgrade
        // if choice is 4, move
        // if choice is 5, take role

        if (choice == 1) {
            return rehearse();
        } else if (choice == 2) {
            return act();
        } else if (choice == 3) {
            // ask the player if they want to upgrade with credits or dollars
            // if they choose credits, call upgradeRank with true
            // if they choose dollars, call upgradeRank with false
            System.out.println("Would you like to upgrade with credits or dollars?");
            System.out.println("1. Credits");
            System.out.println("2. Dollars");
            int choice2 = Systems.getIntFromUser();
            if (choice2 == 1) {
                // get the rank the player wants to upgrade to
                System.out.println("What rank would you like to upgrade to?");
                int rank = Systems.getIntFromUser();
                return Systems.upgradeRank(this, rank, true);
            } else if (choice2 == 2) {
                // get the rank the player wants to upgrade to
                System.out.println("What rank would you like to upgrade to?");
                int rank = Systems.getIntFromUser();
                return Systems.upgradeRank(this, rank, false);
            } else {
                return false;
            }
        } else if (choice == 4) {
            // show adjacent sets
            if (role != null) {
                System.out.println("Unable to move until set is wrapped");
                return false;
            }
            System.out.println("Where would you like to move?");
            for (int i = 0; i < this.location.getAdjacentSet().length; i++) {
                System.out.println(i + ". " + this.location.getAdjacentSet()[i].getName());
            }
            int choice2 = Systems.getIntFromUser();
            // sc.nextLine(); // consume next line or else we get errors later
            if (choice2 < 0 || choice2 >= this.location.getAdjacentSet().length) {
                return false;
            }
            System.out.println("Moving to " + this.location.getAdjacentSet()[choice2].getName() + "...");
            boolean check = move(this.location.getAdjacentSet()[choice2]);
            if (!check) {
                return false;
            } else {
                // check if player wants to take a role
                System.out.println("Would you like to take a role?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice3 = Systems.getIntFromUser();
                if (choice3 == 1) {
                    while(!takeRole()){
                        System.out.println("Would you like to take a role?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        choice3 = Systems.getIntFromUser();
                    }

                    return true;
                } else if (choice3 == 2) {
                    return true;
                } else {
                    System.out.println("Invalid input");
                    boolean check3 = false;
                    while (!check3) {
                        System.out.println("Would you like to take a role?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        choice3 = Systems.getIntFromUser();
                        if (choice3 == 1) {
                            check3 = takeRole();
                        } else if (choice3 == 2) {
                            check3 = true;
                        } else {
                            System.out.println("Invalid input");
                        }
                    }

                    return true;
                }
            }

        } else if (choice == 5) {
            return takeRole();
        } else if (choice == 6) {
            return true;
        } else if (choice == 7) {
            // end the game
            return false;
        } else {
            return false;
        }

    };

    public boolean rehearse() {
        // add to chips and return true

        // check if the player is on a role
        if (role == null) {
            return false;
        } else {
            // add to chips
            chips++;
            return true;
        }

    };

    public boolean act() {
        // roll die and call Systems act
        // check if the player is on a role
        if (role == null) {
            return false;
        } else {
            // roll die
            int roll = rollDice();
            return Systems.act(this, roll);
        }
    };

    public Die getDice() {
        return dice;
    };

    public int getCredits() {
        return credits;
    };

    public int getMoney() {
        return money;
    };

    public void removeChips() {
        this.chips = 0;
    };

    public void addCredits(int credits) {
        this.credits += credits;
    };

    public void removeCredits(int credits) {
        this.credits -= credits;
    };

    public boolean takeRole() {
        return Systems.getInstance().takeRole(this);
    };

    public void addMoney(int money) {
        this.money += money;
    };

    public void removeMoney(int money) {
        this.money -= money;
    };

    public Set getlocation() {
        return location;
    };

    public void setlocation(Set locations) {
        this.location = locations;
    };

    public Roles getRole() {
        return role;
    };

    public void setRole(Roles role) {
        this.role = role;
    };
}