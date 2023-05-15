package classes;

public class Systems {
    //this class vaildates moves and checks for win conditions
    //this class also handles the game loop


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
        if(ply.getlocation().getName() != "Casting Office"){
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


    public static boolean move(Player ply, Set set){
        //move the player to the set and change the location
        //check if the set is adjacent to the player's current location
        //if the set is adjacent, move the player
        //else return false

        if(set.isAdjacent(ply.getlocation())){
            ply.setlocation(set);
            return true;
        }


        
        return false;
    }

    public static boolean work(Player ply, Set set){
        //check if the player is on a role

        
        return false;
    }

    public static boolean act(Player ply, int roll){
        //check the budget of the scene
        //check if the player is on a role
        //if the roll is higher than the budget, pay players on the scene
        //else pay nothing
        if(ply.getlocation().getScene().getBudget() <= roll){
            //pay players on the scene
            //call finish shot
            ply.getlocation().finishShot();
            return true;
        }
        else{
            //pay nothing
            return true;
        }
    }
    
    public static boolean takeRole(Player ply){

        //check if the player is on a role
        if(ply.getRole() != null){
            return false;
        }

        //print all the avaliable roles on the set and scene of the players location
        //ask the player which role they want to take
        //if the player chooses a role, set the player's role to the role
        //else return false
        Set set = ply.getlocation();
        Scene scene = set.getScene();
        System.out.println("Avaliable roles:");
        for(Roles role : set.getRoles()){
            //check if the role is taken, if it is, dont print, else print
            if(role.getIsTaken() == false){
                System.out.println(role.getName());
            }
        }
        
        for(Roles role : scene.getRoles()){
            //check if the role is taken, if it is, dont print, else print
            if(role.getIsTaken() == false){
                System.out.println(role.getName());
            }
        }

        System.out.println("Which role would you like to take?");
        String role = System.console().readLine();
       
        for(Roles r : set.getRoles()){
            if(r.getName() == role){
                ply.setRole(r);
                return true;      
        }

        for(Roles r2 : scene.getRoles()){
            if(r2.getName() == role){
                ply.setRole(r2);
                return true;
            }
        }


    }
        return false;
    }

}
