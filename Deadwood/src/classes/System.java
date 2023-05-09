package classes;

public class System {
    //this class vaildates moves and checks for win conditions
    //this class also handles the game loop
    private Player[] players;
    private int day;
    public Die die;
    public Board board;
    private Banker banker;

    
    


    public static boolean upgradeRank(Player ply, int rank, boolean withcredits){
        //check if the player has enough money to upgrade
        //check if the player is at the casting office
        //rank 2 costs 5 credits or 4 dollars
        //rank 3 costs 10 credits or 10 dollars
        //rank 4 costs 15 credits or 18 dollars
        //rank 5 costs 20 credits or 28 dollars
        //rank 6 costs 25 credits or 40 dollars

        //if the player has enough money or credits, upgrade the player
        //else return false

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
}
