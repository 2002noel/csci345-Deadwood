package classes;

public class Roles{
    private int rank;
    private String name;
    private Player takenBy;

    public Roles(String name, int rank) {
        this.name = name;
        this.rank = rank;
        takenBy = null;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public int getRank(){
        return rank;
    }

    public void setIsTaken(Player take){
        takenBy = take;
    }

    public Player getIsTaken(){
        return takenBy;
    }


    
}