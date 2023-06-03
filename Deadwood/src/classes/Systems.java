package classes;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Handler;
import java.io.File;
import javax.imageio.*;
import java.awt.image.*;

public class Systems {
    // this class validates moves and checks for win conditions
    // this class also handles the game loop
    // this class is a singleton
    private Player[] players;
    private int day;
    private Banker banker;
    private int curTurn = 0;
    private int scenesLeft;
    private int lastday;

    public Die die;
    public Board board;
    private JPanel gamePanel;
    private JLabel boardPanel;
    private JFrame gameFrame;
    private JPanel playerPanel;

    // create the singleton
    private static Systems instance = null;

    // create the constructor
    private Systems() {
        day = 1;
        curTurn = 0;
        die = new Die();
        scenesLeft = 10;
    }

    // create the getInstance method
    public static Systems getInstance() {
        if (instance == null) {
            instance = new Systems();
        }
        return instance;
    }

    public JPanel getGamePanel() { return gamePanel; }
    public JLabel getBoardPanel() { return boardPanel; }
    public void setGamePanel(JPanel panel, JFrame frame) {
        gamePanel = panel;
        gameFrame = frame;
        //SwingUtilities.invokeLater(() -> {
            gamePanel.setSize(1200, 900);
            //gameFrame.setSize(1200, 900);
            try {
                BufferedImage myPicture = ImageIO.read(new File("./images/board.jpg"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                boardPanel = picLabel;
                picLabel.setBounds(0, 0, 1200, 900);
                gamePanel.add(picLabel);
                gamePanel.revalidate();
                gameFrame.pack();
            } catch (Exception e) {
                System.exit(1);
            }
        //});
    }

    public int getDay() {
        return day;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public Board getBoard() {
        return board;
    }

    public Banker getBanker() {
        return banker;
    }

    public void setLastDay(int lastday) {
        this.lastday = lastday;
    }

    //end game

    public void endgame(){
        //calculate the scores
        //print the scores
        //print the winner
        //exit the game
        //the final score is credit + dollars + rank*5
        int max = 0;
        int winner = 0;
        int[] scores = new int[players.length];
        for(int i = 0; i < players.length; i++){
            scores[i]=(players[i].getCredits() + players[i].getMoney() + players[i].getDice().getRank()*5);
            System.out.println("Player " + (i+1) + " Rank: " + players[i].getDice().getRank() + " Credits: " + players[i].getCredits() + " Dollars: " + players[i].getMoney() + " Score: " + scores[i]);
            if(scores[i] > max){
                max = scores[i];
                winner = i;
            }
        }

        //have a pop up window with the winner and their score, money, credits, and rank
        JOptionPane.showMessageDialog(null, "Player " + (winner+1) + " is the winner with a score of " + max + "!\nRank: " + players[winner].getDice().getRank() + "\nCredits: " + players[winner].getCredits() + "\nDollars: " + players[winner].getMoney());

        //pressing ok will exit the game
        System.exit(0);


    }
    public static int getCostOfRank(int rank, boolean withCredits) {
        int costincredits = 0;
        int costindollars = 0;

        switch (rank) {
            case 2:
                costincredits = 5;
                costindollars = 4;
                break;
            case 3:
                costincredits = 10;
                costindollars = 10;
                break;
            case 4:
                costincredits = 15;
                costindollars = 18;
                break;
            case 5:
                costincredits = 20;
                costindollars = 28;
                break;
            case 6:
                costincredits = 25;
                costindollars = 40;
                break;
            default:
                break;
        }
        if (withCredits)
            return costincredits;
        return costindollars;
    }
    public static boolean upgradeRank(Player ply, int rank, boolean withcredits) {
        // check if the player has enough money to upgrade
        // check if the player is at the casting office
        // rank 2 costs 5 credits or 4 dollars
        // rank 3 costs 10 credits or 10 dollars
        // rank 4 costs 15 credits or 18 dollars
        // rank 5 costs 20 credits or 28 dollars
        // rank 6 costs 25 credits or 40 dollars

        // if the player has enough money or credits, upgrade the player
        // else return false
        if (ply.getlocation().getName() != "Casting Office") {
            return false;
        }

        if (withcredits) {
            int costCredits = getCostOfRank(rank, withcredits);
            if (ply.getCredits() >= costCredits) {
                ply.getDice().setRank(rank);
                ply.updateDiceImage();
                ply.removeCredits(costCredits);
                return true;
            }
        } else {
            int costMoney = getCostOfRank(rank, withcredits);
            if (ply.getMoney() >= costMoney) {
                ply.getDice().setRank(rank);
                ply.updateDiceImage();
                ply.removeMoney(costMoney);
                return true;
            }
        }

        return false;
    }

    public boolean move(Player ply, Set set) {
        //check if the player is on a role
        if(ply.getRole() != null){
            return false;
        }

        //change the players location
        ply.setlocation(set);
        board.setLocation(ply, set);
        return true;
        
    }

    public static boolean act(Player ply, int roll) {
        // check the budget of the scene
        // check if the player is on a role
        // if the roll is higher than the budget, pay players on the scene
        // else pay nothing
        if (ply.getRole() == null)
            return false;
        
            
        if (ply.getlocation().getScene().getBudget() <= roll) {
            // pay players on the scene
            // call finish shot
            for (Roles r : ply.getlocation().getRoles()) {
                if (r.equals(ply.getRole())) {
                    ply.addCredits(1);
                    ply.addMoney(1);
                    System.out.println("Success! Gained 1 credit and $1");
                    //have a popup saying acting was success
                    JOptionPane.showMessageDialog(null, "Acting was a success! Gained 1 credit and $1");
                    ply.getlocation().finishShot();
                    return true;
                }
            }

            for (Roles r : ply.getlocation().getScene().getRoles()) {
                if (r.equals(ply.getRole())) {
                    ply.addCredits(2);
                    System.out.println("Success! Gained 2 credits");
                    JOptionPane.showMessageDialog(null, "Acting was a success! Gained 2 credits");
                    ply.getlocation().finishShot();
                    return true;
                }
            }

            System.err.println("PLAYER ATTEMPTING ACT ON WRONG LOCATION");
            return false;
        }
            //have a popup saying acting failed
        
        for (Roles r : ply.getlocation().getRoles()) {
            if (r.equals(ply.getRole())) {
                ply.addMoney(1);
                //have a popup saying acting was failure but since you're an extra you get a dollar
                JOptionPane.showMessageDialog(null, "Acting was a failure! Since you're an extra, you get $1");
                return true;
            }
        }

        //popup that says acting failed
        JOptionPane.showMessageDialog(null, "Acting was a failure!");
        return true;
        // check how many scenes are left on the board, if there are 1 or less, end the
        // day
    }

    public boolean takeRole(Player ply, Roles role) {
        if (role.getIsTaken() != null || role.getRank() > ply.getDice().getRank())
            return false;
        ply.setRole(role);
        role.setIsTaken(ply);
        board.updateLocations(ply.getlocation());
        return true;
    }

    public void startDay() {
        // change all the players back to the trailer
        // change all the scenes in the sets randomly
        // reset the shots on the sets
        // reset the players roles
        // reset the players rehearsal tokens
        // change all the players back to the trailer
        for (Player ply : players) {
            // from the board, get the trailer and set the player's location to the trailer
            ply.setlocation(board.getSetByName("Trailers"));

            // reset the players roles
            ply.setRole(null);

            // reset the players rehearsal tokens
            ply.removeChips();

            board.setLocation(ply, board.getSetByName("Trailers"));
        }

        board.shuffleScenes();
        scenesLeft = 10;
        System.out.println("Everyone at the Trailers!");
    }

    public void endDay() {
        // add 1 to the day counter
        day++;
        if(day > lastday){
            endgame();
        }
        startDay();
    }

    public void finishScene() {
        scenesLeft--;
        if(scenesLeft < 1){
            endDay();
        }

    }
    static String[] choices = {
            "Rehearse",
            "Act",
            "Upgrade",
            "Move",
            "Take Role",
            "End Turn",
            "End Game"
    };

    JPanel buttonPanel;
    public void startGame() {
        startDay();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < choices.length; i++) {
            JButton choice = new JButton(choices[i]);
            choice.addActionListener(f -> {
                // call handleChoice with the choice
                handleChoice(choice.getLabel());
                
            });
            buttonPanel.add(choice);
        }
        setVisibleOptions();
        gamePanel.add(buttonPanel);

        //set playerpanel to the players[curturn]'s info and display it

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.add(new JLabel("Player " + players[curTurn].getid()));
        playerPanel.add(new JLabel("Money: " + players[curTurn].getMoney()));
        playerPanel.add(new JLabel("Rank: " + players[curTurn].getDice().getRank()));
        playerPanel.add(new JLabel("Role: None" ));
        gamePanel.add(playerPanel);


        gameFrame.pack();
        gamePanel.repaint();
    }

    private void setVisibleOptions() {
        Player curPly = players[curTurn];
        for (int i = 0; i < buttonPanel.getComponentCount(); i++) {
            JButton butt = (JButton) buttonPanel.getComponent(i);
            butt.setVisible(true);
            if ((butt.getText() == "Take Role" || butt.getText() == "Act" || butt.getText() == "Rehearse") && curPly.getlocation().isSpecial())
                butt.setVisible(false);
            if ((butt.getText() == "Move" || butt.getText() == "Take Role") && curPly.getRole() != null)
                butt.setVisible(false);
            if (curPly.getRole() == null && (butt.getText() == "Act" || butt.getText() == "Rehearse"))
                butt.setVisible(false);
            if (butt.getText() == "Upgrade") {
                if (curPly.getlocation().getName() == "Casting Office" && curPly.getDice().getRank() < 6)
                    butt.setVisible(true);
                else
                    butt.setVisible(false);
            }
        }
    }

    private void setVisibleOptions(String[] opts) {
        setVisibleOptions();
        for (int i = 0; i < buttonPanel.getComponentCount(); i++) {
            JButton butt = (JButton) buttonPanel.getComponent(i);
            if (!Arrays.asList(opts).contains(butt.getText()))
                butt.setVisible(false);
        }
    }

    public void endturn(){
        curTurn = (curTurn + 1) % players.length;
        //set playerpanel to the players[curturn]'s info and display it
        playerPanel.removeAll();
        playerPanel.add(new JLabel("Player " + players[curTurn].getid()));
        playerPanel.add(new JLabel("Money: " + players[curTurn].getMoney()));
        playerPanel.add(new JLabel("Rank: " + players[curTurn].getDice().getRank()));
        if(players[curTurn].getRole() != null){
            //rehersal tokens
            playerPanel.add(new JLabel("Role: " + players[curTurn].getRole().getName()));
            playerPanel.add(new JLabel("Rehearsal Tokens: " + players[curTurn].getChips()));
        }else{
            playerPanel.add(new JLabel("Role: None"));
        }
        gameFrame.pack();
        setVisibleOptions();
        gamePanel.revalidate();
        gamePanel.repaint();
    }


    public void handleChoice(String choice) {
        Player curPly = players[curTurn];
        if(choice.equals("End Turn")){
            //update the curTurn to the next player
            endturn();
        }else if(choice.equals("End Game")){
            this.day = 5;
            endgame();
        }else if(choice.equals("Rehearse")){
            boolean fin = players[curTurn].rehearse();
            if(!fin){
                //have a popup that says you can't rehearse without a role
                JOptionPane.showMessageDialog(null, "You can't rehearse without a role!");
            }else{
                endturn();
            }
        }else if(choice.equals("Act")){
            boolean fin = players[curTurn].act();
            if(!fin){
                //have a popup that says you can't act without a role
                JOptionPane.showMessageDialog(null, "You can't act without a role!");
            }else{
                endturn();
            } 
        }else if(choice.equals("Upgrade")) {
            if (curPly.getlocation().getName() != "Casting Office")
                return;
            JCheckBox checkbox = new JCheckBox("Use credits to upgrade");
            String message = "Select rank to upgrade to";
            Object[] params = {message, checkbox};
            ArrayList<String> options = new ArrayList<>();
            for (int i = curPly.getDice().getRank() + 1; i <= 6; i++) {
                String opt = i + "";
                int credsReq = getCostOfRank(i, true);
                int moneyReq = getCostOfRank(i, false);

                if (credsReq > curPly.getCredits() && moneyReq > curPly.getMoney())
                    break;

                if (credsReq <= curPly.getCredits())
                    opt = opt + " | " + credsReq + "C";
                if (moneyReq <= curPly.getMoney())
                    opt = opt + " | $" + credsReq;
                options.add(opt);
            }
            if (options.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Not enough credits or money to upgrade");
                return;
            }
            String input = (String) JOptionPane.showInputDialog(null, params,
                    "Upgrade Rank", JOptionPane.QUESTION_MESSAGE, null, options.toArray(), options.get(0));
            boolean useCredits = checkbox.isSelected();
            for (int i = 2; i <= 6; i++) {
                if (input.charAt(0) == ('0' + i)) {
                    if (upgradeRank(curPly, i, useCredits)) {
                        endturn();
                    } else {
                        JOptionPane.showMessageDialog(null, "Not enough " + (useCredits ? "credits" : "money") + " to upgrade");
                        return;
                    }
                }
            }

        }else if(choice.equals("Move")){
            Set location[] = curPly.getlocation().getAdjacentSet();
            //have a popup that shows the adjacent sets and ask which set they want to move to
            String[] options = new String[location.length];
            for (int i = 0; i < location.length; i++) {
                options[i] = location[i].getName();
            }
            String input = (String) JOptionPane.showInputDialog(null, "Choose a location to move to",
                    "Move", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            //System.out.println(input);
            //move the player to the location chosen
            for (int i = 0; i < location.length; i++) {
                if(input.equals(location[i].getName())){
                    curPly.move(location[i]);
                    break;
                }
            }
            if (curPly.getlocation().isSpecial() || curPly.getlocation().getScene() == null) {
                endturn();
                return;
            }
            setVisibleOptions(new String[] {"End Turn", "Take Role"});
        } else if(choice.equals("Take Role")) {
            Roles[] offCard = curPly.getlocation().getRoles();
            if (curPly.getlocation().getScene() == null) {
                JOptionPane.showMessageDialog(null, "Scene already wrapped!");
                return;
            }
            Roles[] onCard = curPly.getlocation().getScene().getRoles();
            //have a popup that shows the adjacent sets and ask which set they want to move to
            ArrayList<String> options = new ArrayList<>();
            for (Roles r : offCard) {
                if (r.getIsTaken() == null && r.getRank() <= curPly.getDice().getRank())
                    options.add(r.getName() + " | R" + r.getRank());
            }
            for (Roles r : onCard) {
                System.out.println(r.getName() + ", " + r.getRank());
                if (r.getIsTaken() == null && r.getRank() <= curPly.getDice().getRank())
                    options.add(r.getName() + " | R" + r.getRank());
            }
            if (options.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No roles to take!");
                return;
            }
            String input = options.get(0);
            if (options.size() > 1) {
                input = (String) JOptionPane.showInputDialog(null, "Choose a role to take",
                        "Move", JOptionPane.QUESTION_MESSAGE, null, options.toArray(), options.get(0));
            }

            for (Roles r : offCard) {
                if (r.getIsTaken() == null && r.getRank() <= curPly.getDice().getRank() && input.contains(r.getName())) {
                    if (takeRole(curPly, r)) {
                        endturn();
                        return;
                    }
                }
            }
            for (Roles r : onCard) {
                if (r.getIsTaken() == null && r.getRank() <= curPly.getDice().getRank() && input.contains(r.getName())) {
                    if (takeRole(curPly, r)) {
                        endturn();
                        return;
                    }
                }
            }

        }




    }
}
