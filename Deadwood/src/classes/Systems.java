package classes;
import java.util.Scanner;

public class Systems {
    // this class vaildates moves and checks for win conditions
    // this class also handles the game loop
    // this class is a singleton
    private Player[] players;
    private int day;
    private Banker banker;
    private int curTurn;

    public static Scanner scan;
    public Die die;
    public Board board;

    // create the singleton
    private static Systems instance = null;

    // create the constructor
    private Systems() {
        day = 1;
        curTurn = 0;
        die = new Die();
        scan = new Scanner(System.in);
    }

    // create the getInstance method
    public static Systems getInstance() {
        if (instance == null) {
            instance = new Systems();
        }
        return instance;
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

    public static boolean move(Player ply, Set set) {
        //check if the player is on a role
        if(ply.getRole() != null){
            return false;
        }

        System.out.println(set.getName() + " is the new location");
        //change the players location
        ply.setlocation(set);
        return true;
        
    }

    public static boolean work(Player ply, Set set) {
        // check if the player is on a role
        if (ply.getRole() != null) {
            
        }
        return false;
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
            System.out.println("Acting succeeded");
            System.out.println("Roll: " + roll + " Budget: " + ply.getlocation().getScene().getBudget());
            ply.getlocation().finishShot();
            return true;
        }
        System.out.println("Acting failed");
        System.out.println("Roll: " + roll + " Budget: " + ply.getlocation().getScene().getBudget());
        return true;
        // check how many scenes are left on the board, if there are 1 or less, end the
        // day
    }

    public static boolean takeRole(Player ply) {

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
        System.out.println("Available roles:");
        for (Roles role : scene.getRoles()) {
            // check if the role is taken, if it is, dont print, else print
            if (role.getIsTaken() == false) {
                System.out.println(role.getName());

            }
        }
        System.out.println("Extras:");
        for (Roles role : set.getRoles()) {
            // check if the role is taken, if it is, dont print, else print
            if (role.getIsTaken() == false) {
                System.out.println(role.getName());
            }
        }

        System.out.println("Which role would you like to take?");
        String role = scan.nextLine();
        for (Roles r : set.getRoles()) {
            if (r.getName().equals(role)) {
                ply.setRole(r);
                r.setIsTaken(true);
                return true;
            }
        }
        System.out.println("Main roles:");
        for (Roles r2 : scene.getRoles()) {
            if (r2.getName().equals(role)) {
                ply.setRole(r2);
                r2.setIsTaken(true);
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
        System.out.println("Everyone at the Trailers!");
    }

    public void endDay() {
        // add 1 to the day counter
        day++;
    }

    public void startGame() {
        // tell every player on the list to make a turn
        while (true) {
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
    
                int choice = Systems.getIntFromUser();
    
                if(choice == 7){
                    this.day = 5;
                    endDay();
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
                        endDay();
                        return;
                    }
    
                    valid = ply.useTurn(choice);
                }
    
                //check how many scenes are left on the board, if there are 1 or less, end the day
                if(board.getScenes().length <= 1){
                    endDay();
                    return;
                }
            }

        }

        //scan.close();

    }

    public static int getIntFromUser() {
        while (!scan.hasNextInt()) {
            System.out.println("Invalid option. Please enter an integer.");
            //scan.reset();
            if (scan.hasNextLine())
                scan.nextLine();
        }
        int ret = scan.nextInt();
        if (scan.hasNextLine())
            scan.nextLine();
        return ret;
    }
}
