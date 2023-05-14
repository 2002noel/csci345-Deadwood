package classes;
import java.util.*;
import classes.Player;
import java.io.*;

public class Deadwood {
    private Player[] players;
    private int day;
    public Die die;
    public Board board;
    private Banker banker;



    public Deadwood() {
        this.day = 1;
        this.die = new Die();
        this.board = new Board();
        this.banker = new Banker();
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


