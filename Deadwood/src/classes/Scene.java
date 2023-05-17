package classes;

public class Scene {
    private int budget;
    private String name;
    private String description;
    private int sceneNum;
    private Roles[] roles;
    
    public Scene(String name, int budget, int sceneNum) {
        this.name = name;
        this.budget = budget;
        this.sceneNum = sceneNum;
    }

    //create the getters and setters for the class
    public int getBudget() {
        return budget;
    }

    public void setDescription(String description){
        this.description = description;
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

    public Roles[] getRoles(){
        return roles;
    }

    public void setRoles(Roles[] roles){
        this.roles = roles;
    }
}