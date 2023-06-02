package classes;

import javax.swing.*;
import java.util.Arrays;

public class Set extends JButton {

    private int shotsLeft;
    public Scene scene;
    private String name;
    private Set adjacentSets[] = new Set[1];
    private Roles[] roles; // off card roles

    public Set() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setVisible(false);
    }

    public Set(String name) {
        this();
        this.name = name;
    }
    
    public Set(String name, int numShots) {
        this();
        this.name = name;
        shotsLeft = numShots;
    }
    
    public Set(String name, Scene scene) {
        this();
        this.name = name;
        this.scene = scene;
    }

    //getter and setter for scene

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }
    
    public boolean isSpecial() {
        return name.equals("Trailers") || name.equals("Casting Office");
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

    public void finishScene() {
        Systems.getInstance().finishScene();
        System.out.println("That's a wrap!");
        boolean shouldPay = false;
        int amountToPay = scene.getBudget();
        for (Roles r : scene.getRoles()) {
            Player ply = r.getIsTaken();
            if (ply != null) {
                shouldPay = true;
                ply.removeChips();
                ply.setRole(null);
            }
        }

        if (shouldPay) {
            int[] payout = new int[amountToPay];
            for (int i = 0; i < amountToPay; i++) {
                payout[i] = Systems.getInstance().die.rollDie();
            }
            Arrays.sort(payout); // must be sorted from least to greatest
            Roles[] roles = scene.getRoles();
            int rolesIndex = roles.length;
            while (amountToPay > 0) { // assumes that roles are ordered least rank to greatest rank
                rolesIndex--;
                Player ply = roles[rolesIndex].getIsTaken();
                if (ply != null) {
                    amountToPay--;
                    System.out.println("Paying out $" + payout[amountToPay] + " to player " + ply.getid());
                    ply.addMoney(payout[amountToPay]);
                }
                if (rolesIndex == 0) {
                    rolesIndex = roles.length;
                }
            }

            // payout to the extras
            for (Roles r : roles) {
                Player ply = r.getIsTaken();
                if (ply != null) {
                    ply.removeChips();
                    ply.addMoney(r.getRank());
                    System.out.println("Paying out $" + r.getRank() + " to player " + ply.getid());
                }
            }
        } else {
            System.out.println("No players on card so scene doesn't pay out");
            for (Roles r : roles) {
                Player ply = r.getIsTaken();
                if (ply != null) {
                    ply.removeChips();
                    ply.setRole(null);
                }
            }
        }
        scene = null;
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