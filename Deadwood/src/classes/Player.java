package classes;

import javax.management.relation.Role;

public class Player {
    private int credits;
    private int rank;
    private int money;
    private int chips;
    private Set location;
    private Roles role;
    
    public boolean move() {
        return false;
    };
    public boolean work() {
        return false;
    };
    public boolean upgradeRank(int rank) {
        return false;
    };
    public int calcScore() {
        return -1;
    };
    
    public void addChips(int chips) {};
    public void removeChips(int chips) {};
    public void addCredits(int credits) {};
    public void removeCredits(int credits) {};
    public void takeRole(Set set, Scene scene) {};
    public void addMoney(int money) {};
    public void removeMoney(int money) {};
}