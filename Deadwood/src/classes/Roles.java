package classes;

import javax.swing.*;

public class Roles extends JComponent {
    private int rank;
    private String name;
    private Player takenBy;

    public Roles(String name, int rank) {
        this.name = name;
        this.rank = rank;
        takenBy = null;
    }

    public String getName(){
        return name;
    }

    public int getRank(){
        return rank;
    }

    public void setIsTaken(Player take){
        takenBy = take;
    }

    public Player getIsTaken(){
        return takenBy;
    }
}