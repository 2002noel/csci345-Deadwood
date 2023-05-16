package classes;

public class Set {

    private int shotsleft;
    public Scene scene;
    private String name;
    private Set adjacentSets[] = new Set[4];
    private Roles[] roles;
    private Roles[] availableroles;
    
    public Set(String name) {
        this.name = name;
    }
    
    public Set(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
    }

    //getter and setter for shotsleft
    public void setShotsLeft(int shotsleft){
        this.shotsleft = shotsleft;
    }

    public int getShotsLeft(){
        return shotsleft;
    }

    //getter and setter for scene

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }

    //getter and setter for name

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    //getter and setter for adjacentSet

    public void addAdjacentSet(Set set){
        for (int i = 0; i < adjacentSets.length; i++) {
            if (adjacentSets[i] == null) {
                adjacentSets[i] = set;
                return;
            }
        }
        System.err.println(name + " set has too many adjacent sets");
    }


    public Set[] getAdjacentSet(){
        return adjacentSets;
    }

    public void finishScene(){
        return;
    }

    public void finishShot(){
        //subtract 1 from shotsleft
        //if shotsleft == 0, call finishScene
        this.shotsleft -= 1;
        if (this.shotsleft == 0){
            this.finishScene();
        }
        return;
    }

    public boolean isAdjacent(Set set){
        //check if set is in adjacentSets
        for (int i = 0; i < this.adjacentSets.length; i++){
            if (this.adjacentSets[i] == set){
                return true;
            }
        }
        return false;
    }


    public void setRoles(Roles[] roles){
        this.roles = roles;
    }

    public Roles[] getRoles(){
        return roles;
    }


}