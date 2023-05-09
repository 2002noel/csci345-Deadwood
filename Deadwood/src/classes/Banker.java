package classes;

public class Banker extends Player {
    
    public void transferfunds(Player ply, int amount) {
        ply.addCredits(amount);
    }

    public void givemoneytobank(Player ply, int amount) {
        ply.removeMoney(amount);
    }

}
    