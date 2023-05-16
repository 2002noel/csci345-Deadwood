package classes;
import java.util.*;
import classes.Player;
import java.io.*;

public class Deadwood {
    private Player[] players;
    private int day;
    private Banker banker;
    private int curTurn;
    public Die die;
    public Board board;

    public static void main(String[] args) throws Exception {
        int numPlayers = 2;
        if (args.length < 2) {
            /*System.err.println("Number of players not provided");
            return;*/
            // UNCOMMENT AFTER TESTING
        } else {
            numPlayers = Integer.parseInt(args[1]);
        }
        //parse xml file
        //create board
        Deadwood gameState = new Deadwood(numPlayers);
    }

    public Deadwood(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 8) {
            System.err.println("Invalid number of players entered");
            return;
        }
        this.day = 1;
        this.die = new Die();
        this.board = new Board();
        this.banker = new Banker();
        players = new Player[numPlayers];
        System.out.println("Started Deadwood with " + numPlayers + " players");
    }

    public int getDay() {
        return day;
    }

    public void incrementDay() {
        day++;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDie(Die die) {
        this.die = die;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public Die getDie() {
        return die;
    }

    public Board getBoard() {
        return board;
    }

    public Banker getBanker() {
        return banker;
    }

}


