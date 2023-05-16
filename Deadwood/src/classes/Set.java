package classes;

public class Set {

    private int shotsLeft;
    public Scene scene;
    private String name;
    private Set adjacentSets[] = new Set[1];
    private Roles[] roles; // off card roles
    private Roles[] availableroles;
    
    public Set(String name) {
        this.name = name;
    }
    
    public Set(String name, int numShots) {
        this.name = name;
        shotsLeft = numShots;
    }
    
    public Set(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
    }

    //getter and setter for shotsLeft
    public void setShotsLeft(int shotsLeft){
        this.shotsLeft = shotsLeft;
    }

    public int getShotsLeft(){
        return shotsLeft;
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

    public void addAdjacentSet(Set set) {
        for (int i = 0; i < adjacentSets.length; i++) {
            if (adjacentSets[i] == null) {
                adjacentSets[i] = set;
                return;
            }
        }
        
        Set[] newSets = new Set[adjacentSets.length + 1];
        System.arraycopy(adjacentSets, 0, newSets, 0, adjacentSets.length);
        newSets[newSets.length - 1] = set;
        
        adjacentSets = newSets;
        
        //System.err.println(name + " set has too many adjacent sets");
    }


    public Set[] getAdjacentSet() {
        return adjacentSets;
    }

    public void finishScene(){
        return;
    }

    public void finishShot(){
        //subtract 1 from shotsLeft
        //if shotsLeft == 0, call finishScene
        this.shotsLeft -= 1;
        if (this.shotsLeft == 0){
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