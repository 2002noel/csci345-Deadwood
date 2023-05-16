package classes;
import java.util.*;
import java.io.*;


public class GameState {
    private Player[] players;
    private int day;
    private int lastDay;
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
        GameState gameState = new GameState(numPlayers);
    }
    
    

    public GameState(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 8) {
            System.err.println("Invalid number of players entered");
            return;
        }
        this.day = 1;
        lastDay = 4;
        if (numPlayers < 4) {
            lastDay = 3;
        }
        int startingCredits = 2;
        int startingRank = 1;
        this.die = new Die();
        this.board = new Board();
        this.banker = new Banker(startingCredits, startingRank);
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(startingCredits, startingRank);
        }
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


