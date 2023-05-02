package classes;

public class Scene {
    private int budget;
    private String name;
    private String description;
    private Roles[] roles;

    //create the getters and setters for the class
    public int getBudget(){
        return budget;
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }    
}