package classes;


public class Player {
    private int credits;
    private int money;
    private int chips;
    private Die dice;
    private Set location;
    private Roles role;
    

    int rollDice() {
        return dice.rollDie();
    };

    public boolean move() {
    };

    public boolean work() {
        return false;
    };
    public boolean upgradeRank(int rank) {
        //call the Deadwood method upgradeRank
        return System.upgradeRank(this, rank);
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
    
    public void addChips(int chips) {};
    public void removeChips(int chips) {};
    public void addCredits(int credits) {};
    public void removeCredits(int credits) {};
    public void takeRole(Set set, Scene scene) {};
    public void addMoney(int money) {};
    public void removeMoney(int money) {};
}