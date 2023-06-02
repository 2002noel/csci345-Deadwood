import java.awt.*;
import java.awt.image.BufferedImage;
import java.rmi.server.ExportException;
import java.util.*;
import java.io.*;
import classes.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;


public class Deadwood {
    static JPanel gamePanel;
    static JFrame gameFrame;
    public static void main(String[] args) {
        //create a new game
        int numPlayers = 0;
        //ask for number of players
        JFrame frame = new JFrame("Deadwood");//creating instance of JFrame
        gameFrame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, 900, 400);
        frame.getContentPane().add(gamePanel);
        String[] numPlayerSelection = {"2", "3", "4", "5", "6", "7", "8"};
        JSpinner select = new JSpinner(new SpinnerListModel(numPlayerSelection));//creating instance of JButton
        select.setBounds(80, 00, 50, 40); //x axis, y axis, width, height
        select.setMaximumSize(new Dimension(50, 40));
        gamePanel.add(select);//adding button in JFrame
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(select.getValue());
                gamePanel.removeAll();
                startGame(Integer.parseInt((String)select.getValue()));
            }
        });
        startButton.setBounds(-100, 60, 100, 30);
        gamePanel.add(startButton);
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.pack();
        frame.setVisible(true);
        /*Scanner sc = new Scanner(System.in);
        if (args.length < 1) {
            System.out.println("How many players are playing?");
            numPlayers = sc.nextInt();
            sc.nextLine();
        } else {
            numPlayers = Integer.parseInt(args[0]);
        }

        if (numPlayers < 2 || numPlayers > 8) {
            System.err.println("Invalid number of players");
            return;
        }*/
    }
    static private void startGame(int numPlayers) {
        Systems game = Systems.getInstance();

        //assign a random player to be the banker
        Random rand = new Random();
        int banker = rand.nextInt(numPlayers);
        //create an array of players
        Player[] players = new Player[numPlayers];
        int startingCredits = 0;
        int startingRank = 1;
        if (numPlayers == 5) {
            startingCredits = 2;
        } else if (numPlayers == 6) {
            startingCredits = 4;
        } else if (numPlayers > 6) {
            startingRank = 2;
        }
        //create the players, except for the banker index, then create a banker
        for (int i = 0; i < numPlayers; i++) {
            if(i != banker) {
                players[i] = new Player(startingCredits, startingRank);
                players[i].setid(i+1);
            } else {
                players[i] = new Banker(startingCredits, startingRank);
                //set the banker in the game
                game.setBanker((Banker)players[i]);
                players[i].setid(i+1);
                //print out the player num that is the banker
                System.out.println("Player " + (i+1) + " is the banker");
            }
        }

        game.setGamePanel(gamePanel, gameFrame);

        //set the players in the game
        game.setPlayers(players);
        //set the board in the game
        game.setBoard(new Board());

        //print all the scenes on the board
        //set the board with the players
        game.getBoard().setPlayers(players);
        //set all the locations to Trailers
        for(int i = 0; i < numPlayers; i++) {
            game.getBoardPanel().add(players[i]);
            game.getBoard().setLocation(players[i], game.getBoard().getSetByName("Trailers"));
        }
        //set the board with the banker
        //set the day in the game



        int lastday = 4;
        if(numPlayers < 4){
            lastday = 3;
        }

        while(game.getDay() < lastday){
            //start the day
            game.startDay();
            //start the game
            game.startGame();

        }

        game.endgame();

        Systems.scan.close();

    }
}