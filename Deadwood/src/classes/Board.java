package classes;


public class Board {
    private Set[] sets = new Set[10];
    private Player[] players;
    private int[] location;
    private int scenesLeft;
    
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