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

    public boolean move(Set set) {
        // verify that set is adjacent to current set
        return System.move(this, set);
    };

    public boolean work(Set set) {
        return System.work(this, set);
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
        return System.takeRole(this, set, scene);
    };
    public void addMoney(int money) {
        this.money += money;
    };
    public void removeMoney(int money) {
        this.money -= money;
    };
}