import java.util.*;
import java.io.*;
import classes.*;


public class Deadwood {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //create a new game
        int numPlayers = 0;
        //ask for number of players
        System.out.println("How many players are playing?");
        Scanner sc = new Scanner(System.in);
        numPlayers = sc.nextInt();
        //create a new game

        Systems game = Systems.getInstance();

        //assign a random player to be the banker
        Random rand = new Random();
        int banker = rand.nextInt(numPlayers);
        //create an array of players
        Player[] players = new Player[numPlayers];
        //create the players, except for the banker index, then create a banker
        for( int i = 0; i < numPlayers; i++){
            if(i != banker){
                players[i] = new Player(0, 1);
            }
            else{
                players[i] = new Banker(0, 1);
                //set the banker in the game
                game.setBanker((Banker)players[i]);
                //print out the player num that is the banker
                System.out.println("Player " + (i+1) + " is the banker");
            }
        }

        //set the players in the game
        game.setPlayers(players);



        
    }

}
