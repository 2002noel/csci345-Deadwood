package classes;

public class Roles{
    private int rank;
    private String name;
    private boolean isTaken;



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

    public void setIsTaken(boolean isTaken){
        this.isTaken = isTaken;
    }

    public boolean getIsTaken(){
        return isTaken;
    }


    
}