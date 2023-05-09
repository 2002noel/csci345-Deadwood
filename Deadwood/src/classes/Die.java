package classes;

import java.util.Random;

public class Die {
    private int rank;
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public int rollDie() {
        //roll a random number from 1 to 6
        
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        return roll;

    }
}
