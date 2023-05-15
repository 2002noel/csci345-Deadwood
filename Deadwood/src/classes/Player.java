package classes;
import java.util.*;
import classes.Player;
import java.io.*;


public class Player {
    private int credits;
    private int money;
    private int chips;
    private Die dice;
    private Set location;
    private Roles role;
    

    int rollDice() {
        //if there are chips, add chips to roll, else just roll
        if(chips > 0){
            return dice.rollDie() + chips;
        }
        else{
            return dice.rollDie();
        }

    };

    public boolean move(Set set) {
        // verify that set is adjacent to current set
        return Systems.move(this, set);
    };

    public boolean useTurn(int choice, Set set, Scene scene) {
        //if choice is 1, rehearse
        //if choice is 2, act
        //if choice is 3, upgrade
        //if choice is 4, move
        //if choice is 5, take role

        if(choice == 1){
            return rehearse();
        }
        else if(choice == 2){
            return act();
        }
        else if(choice == 3){
            //ask the player if they want to upgrade with credits or dollars
            //if they choose credits, call upgradeRank with true
            //if they choose dollars, call upgradeRank with false
            System.out.println("Would you like to upgrade with credits or dollars?");
            System.out.println("1. Credits");
            System.out.println("2. Dollars");
            Scanner sc = new Scanner(System.in);
            int choice2 = sc.nextInt();
            if(choice2 == 1){
                //get the rank the player wants to upgrade to
                System.out.println("What rank would you like to upgrade to?");
                int rank = sc.nextInt();
                return Systems.upgradeRank(this, rank, true);
            }
            else if(choice2 == 2){
                //get the rank the player wants to upgrade to
                System.out.println("What rank would you like to upgrade to?");
                int rank = sc.nextInt();
                return Systems.upgradeRank(this, rank, false);
            }
            else{
                return false;
            }
        }
        else if(choice == 4){
            return this.move(set);
        }
        else if(choice == 5){
            return takeRole(set, scene);
        }
        else{
            return false;
        }
    };

    public boolean rehearse() {
        //add to chips and return true

        //check if the player is on a role
        if(role == null){
            return false;
        }
        else{
            //add to chips
            chips++;
            return true;
        }
        
    };

    public boolean act() {
        //roll die and call Systems act 
        //check if the player is on a role
        if(role == null){
            return false;
        }
        else{
            //roll die
            int roll = rollDice();
            return Systems.act(this, roll);
        }
    };


    
    public boolean upgradeRank(int rank, boolean withcredits) {
        //call the Deadwood method upgradeRank
        return Systems.upgradeRank(this, rank, withcredits);
    };

    public int calcScore() {
        return -1;
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
    
    public void addChips(int chips) {
        this.chips += chips;
    };
    public void removeChips(int chips) {
        this.chips -= chips;
    };
    public void addCredits(int credits) {
        this.credits += credits;
    };
    public void removeCredits(int credits) {
        this.credits -= credits;
    };
    public boolean takeRole(Set set, Scene scene) {
        return Systems.takeRole(this, set, scene);
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

    public void setlocation(Set location) {
        this.location = location;
    };

    public Roles getRole() {
        return role;
    };

    public void setRole(Roles role) {
        this.role = role;
    };
}