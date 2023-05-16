package classes;


public class Board {
    private Set[] sets = new Set[10];
    private Set[] specialSets = new Set[2];
    private Player[] players;
    private int[] location;
    private int scenesLeft;
    
    public Board() {
        sets[0] = new Set("Main Street");
        sets[1] = new Set("Saloon");
        sets[2] = new Set("Bank");
        sets[3] = new Set("Hotel");
        sets[4] = new Set("Church");
        sets[5] = new Set("Ranch");
        sets[6] = new Set("Secret Hideout");
        sets[7] = new Set("General Store");
        sets[8] = new Set("Train Station");
        sets[9] = new Set("Jail");
        specialSets[0] = new Set("Trailers");
        specialSets[1] = new Set("Casting Office");
        
        sets[0].addAdjacentSet(sets[1]);
        sets[0].addAdjacentSet(sets[9]);
        sets[0].addAdjacentSet(specialSets[0]); // may not want player to be able to move back in to trailers
        
        sets[1].addAdjacentSet(sets[0]);
        sets[1].addAdjacentSet(specialSets[0]);
        sets[1].addAdjacentSet(sets[2]);
        sets[1].addAdjacentSet(sets[7]);
        
        sets[2].addAdjacentSet(sets[1]);
        sets[2].addAdjacentSet(sets[3]);
        sets[2].addAdjacentSet(sets[5]);
        sets[2].addAdjacentSet(sets[4]);
        
        sets[3].addAdjacentSet(sets[2]);
        sets[3].addAdjacentSet(specialSets[0]);
        sets[3].addAdjacentSet(sets[4]);
        
        sets[4].addAdjacentSet(sets[2]);
        sets[4].addAdjacentSet(sets[6]);
        sets[4].addAdjacentSet(sets[3]);
        
        sets[5].addAdjacentSet(specialSets[1]);
        sets[5].addAdjacentSet(sets[6]);
        sets[5].addAdjacentSet(sets[2]);
        sets[5].addAdjacentSet(sets[7]);
        
        sets[6].addAdjacentSet(sets[5]);
        sets[6].addAdjacentSet(specialSets[1]);
        sets[6].addAdjacentSet(sets[4]);
        
        sets[7].addAdjacentSet(sets[9]);
        sets[7].addAdjacentSet(sets[8]);
        sets[7].addAdjacentSet(sets[5]);
        sets[7].addAdjacentSet(sets[1]);
        
        sets[8].addAdjacentSet(specialSets[1]);
        sets[8].addAdjacentSet(sets[9]);
        sets[8].addAdjacentSet(sets[7]);
        
        sets[9].addAdjacentSet(sets[8]);
        sets[9].addAdjacentSet(sets[0]);
        sets[9].addAdjacentSet(sets[7]);
        
        specialSets[0].addAdjacentSet(sets[0]);
        specialSets[0].addAdjacentSet(sets[1]);
        specialSets[0].addAdjacentSet(sets[3]);
        
        specialSets[1].addAdjacentSet(sets[6]);
        specialSets[1].addAdjacentSet(sets[8]);
        specialSets[1].addAdjacentSet(sets[5]);
    }
    
    public boolean setLocation(Player ply, Set set) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == ply) {
                for (int k = 0; k < sets.length; k++) {
                    if (set == sets[k]) {
                        location[i] = k;
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
    
    public Set getLocation(Player ply) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == ply) {
                return sets[location[i]];
            }
        }
        
        return null;
    }

    public void setSets(Set[] sets) {
        this.sets = sets;
    }

    public Set[] getSets() {
        return sets;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }


}