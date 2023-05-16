package classes;

public class Banker extends Player {
    
    public Banker(int numCredits, int rank) {
        super(numCredits, rank);
    }
    
    public void transferfunds(Player ply, int amount) {
        ply.addCredits(amount);
    }

    public void givemoneytobank(Player ply, int amount) {
        ply.removeMoney(amount);
    }

}
    