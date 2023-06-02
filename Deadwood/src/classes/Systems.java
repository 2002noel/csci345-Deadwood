package classes;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public static Scanner scan;
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
        scan = new Scanner(System.in);
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
        System.out.println("The winner is player " + (winner+1) + " with a score of " + max);
        System.exit(0);
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

        if (withcredits) {
            if (ply.getCredits() >= costincredits) {
                ply.removeCredits(costincredits);
                return true;
            }
        } else {
            if (ply.getMoney() >= costindollars) {
                ply.removeMoney(costindollars);
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
                    ply.getlocation().finishShot();
                    ply.addCredits(1);
                    ply.addMoney(1);
                    System.out.println("Success! Gained 1 credit and 1 dollar");
                    ply.getlocation().finishShot();
                    return true;
                }
            }

            for (Roles r : ply.getlocation().getScene().getRoles()) {
                if (r.equals(ply.getRole())) {
                    ply.getlocation().finishShot();
                    ply.addCredits(2);
                    System.out.println("Success! Gained 2 credits");
                    ply.getlocation().finishShot();
                    return true;
                }
            }

            System.err.println("PLAYER ATTEMPTING ACT ON WRONG LOCATION");
            return false;
        }

        System.out.println("Acting failed");
        for (Roles r : ply.getlocation().getRoles()) {
            if (r.equals(ply.getRole())) {
                ply.addMoney(1);
                System.out.println("Gained 1 dollar");
                return true;
            }
        }
        return true;
        // check how many scenes are left on the board, if there are 1 or less, end the
        // day
    }

    public boolean takeRole(Player ply) {

        // check if the player is on a role
        if (ply.getRole() != null) {
            System.out.println("You are already on a role");
            return false;
        }


        // print all the available roles on the set and scene of the players location
        // ask the player which role they want to take
        // if the player chooses a role, set the player's role to the role
        // else return false
        Set set = ply.getlocation();
        if (set == null || set.isSpecial()) {
            System.out.println("You are not on a set");
            return false;
        }
        System.out.println(set.getName());
        Scene scene = set.getScene();
        if (scene == null) {
            System.err.println("!!!SET MISSING SCENE!!!");
            return false;
        }
        System.out.println("Player rank: " + ply.getDice().getRank());
        System.out.println("Available roles:");
        for (Roles role : scene.getRoles()) {
            // check if the role is taken, if it is, dont print, else print
            if (role.getIsTaken() == null) {
                System.out.println("\"" + role.getName() + "\"" + " Rank: " + role.getRank());
            }
        }
        System.out.println("Extras:");
        for (Roles role : set.getRoles()) {
            // check if the role is taken, if it is, dont print, else print
            if (role.getIsTaken() == null) {
                System.out.println("\"" + role.getName() + "\"" + " Rank: " + role.getRank());
            }
        }

        System.out.println("Which role would you like to take?");
        String role = scan.nextLine();
        for (Roles r : set.getRoles()) {
            if (r.getName().equals(role)) {
                if(r.getIsTaken() != null){
                    System.out.println("Role is taken");
                    return false;
                }
                if(r.getRank() > ply.getDice().getRank()){
                    System.out.println("You do not have a high enough rank");
                    return false;
                }
                ply.setRole(r);
                r.setIsTaken(ply);
                board.updateLocations(set);
                return true;
            }
        }
        for (Roles r2 : scene.getRoles()) {
            if (r2.getName().equals(role)) {
                if(r2.getIsTaken() != null){
                    System.out.println("Role is taken");
                    return false;
                }
                if(r2.getRank() > ply.getDice().getRank()) {
                    System.out.println("You do not have a high enough rank");
                    return false;
                }
                ply.setRole(r2);
                r2.setIsTaken(ply);
                return true;
            }
        }
        return false;
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
    }

    public void finishScene() {
        scenesLeft--;
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
        gamePanel.add(buttonPanel);

        //set playerpanel to the players[curturn]'s info and display it

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.add(new JLabel("Player " + players[curTurn].getid()));
        playerPanel.add(new JLabel("Money: " + players[curTurn].getMoney()));
        playerPanel.add(new JLabel("Rank: " + players[curTurn].getDice().getRank()));
        playerPanel.add(new JLabel("Role: " + players[curTurn].getRole()));
        gamePanel.add(playerPanel);


        gameFrame.pack();
        gamePanel.repaint();
        // tell every player on the list to make a turn
        /*while (true) {
            for (Player ply : players) {
                // ask a player what they want to do
                // if choice is 1, rehearse
                // if choice is 2, act
                // if choice is 3, upgrade
                // if choice is 4, move
                // if choice is 5, take role
                // if choice is 6, end turn
                // if choice is 7, quit game
                System.out.println("It is Player " + ply.getid() + "'s turn.");
                System.out.println("You are at " + ply.getlocation().getName() + ".");
                System.out.println("What would you like to do?");
                System.out.println("1. Rehearse");
                System.out.println("2. Act");
                System.out.println("3. Upgrade");
                System.out.println("4. Move");
                System.out.println("5. Take Role");
                System.out.println("6. End Turn");
                System.out.println("7. End Game");
                while(gameFrame != null) {
                    gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
                    System.out.println("loop");
                }

                int choice = Systems.getIntFromUser();
    
                if(choice == 7){
                    this.day = 5;
                    endgame();
                    return;
                }
    
    
                boolean valid = ply.useTurn(choice);
    
                while (!valid) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.println("What would you like to do?");
                    System.out.println("1. Rehearse");
                    System.out.println("2. Act");
                    System.out.println("3. Upgrade");
                    System.out.println("4. Move");
                    System.out.println("5. Take Role");
                    System.out.println("6. End Turn");
                    System.out.println("7. Quit Game");
                    choice = Systems.getIntFromUser();
    
                    if(choice == 7){
                        this.day = 5;
                        endgame();
                        return;
                    }
    
                    valid = ply.useTurn(choice);
                }
    
                //check how many scenes are left on the board, if there are 1 or less, end the day
                if(scenesLeft < 1) {
                    System.out.println("All scenes are finished!");
                    endDay();
                    return;
                }
            }

        }*/

        //scan.close();

    }

    private void setVisibleOptions() {
        for (int i = 0; i < buttonPanel.getComponentCount(); i++) {
            JButton butt = (JButton) buttonPanel.getComponent(i);
            butt.setVisible(true);
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
        playerPanel.add(new JLabel("Role: " + players[curTurn].getRole()));
        setVisibleOptions();
        gamePanel.revalidate();
        gamePanel.repaint();
    }



    public void handleChoice(String choice) {
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
                JOptionPane.showMessageDialog(gameFrame, "You can't rehearse without a role!");
            }else{
                endturn();
            }
        }else if(choice.equals("Act")){
            boolean fin = players[curTurn].act();
            if(!fin){
                //have a popup that says you can't act without a role
                JOptionPane.showMessageDialog(gameFrame, "You can't act without a role!");
            }else{
                endturn();
            } 
        }else if(choice.equals("Move")){
            
            Set location[] = players[curTurn].getlocation().getAdjacentSet();
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
                    players[curTurn].move(location[i]);
                    break;
                }
            }
            //endturn();

            setVisibleOptions(new String[] {"End Turn", "Take Role"});
            
            


        }




    }

    public static int getIntFromUser() {
        while (!scan.hasNextInt()) {
            System.out.println("Invalid option. Please enter an integer.");
            if (scan.hasNextLine())
                scan.nextLine();
        }
        int ret = scan.nextInt();
        if (scan.hasNextLine())
            scan.nextLine();
        return ret;
    }
}
