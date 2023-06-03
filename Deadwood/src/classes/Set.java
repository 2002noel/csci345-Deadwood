package classes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Set extends JButton {

    private int shotsLeft;
    ArrayList<JLabel> shotLabelList;
    public Scene scene;
    private String name;
    private Set adjacentSets[] = new Set[1];
    private Roles[] roles; // off card roles

    public Set() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setVisible(false);
        shotLabelList = new ArrayList<>();
    }

    public Set(String name) {
        this();
        this.name = name;
    }
    
    public Set(String name, int numShots) {
        this();
        this.name = name;
        shotsLeft = numShots;
        for (int i = 0; i < shotsLeft; i++) {
            try {
                BufferedImage myPicture = ImageIO.read(new File("./images/shot.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                picLabel.setSize(42, 42);
                picLabel.setVisible(false);
                shotLabelList.add(picLabel);
            } catch (Exception e) {
                System.exit(1);
            }
        }
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

    public ArrayList<JLabel> getShotLabelList() {
        return shotLabelList;
    }

    public void finishScene() {
        System.out.println("That's a wrap!");
        boolean shouldPay = false;
        int amountToPay = scene.getBudget();
        for (Roles r : scene.getRoles()) {
            Player ply = r.getIsTaken();
            if (ply != null) {
                shouldPay = true;
                ply.removeChips();
                ply.setRole(null);
                //r.setIsTaken(null);
            }
        }

        if (shouldPay) {
            int[] payout = new int[amountToPay];
            for (int i = 0; i < amountToPay; i++) {
                payout[i] = Systems.getInstance().die.rollDie();
            }
            Arrays.sort(payout); // must be sorted from least to greatest
            Roles[] onCard = scene.getRoles();
            int rolesIndex = onCard.length;
            while (amountToPay > 0) { // assumes that roles are ordered least rank to greatest rank
                rolesIndex--;
                Player ply = onCard[rolesIndex].getIsTaken();
                if (ply != null) {
                    amountToPay--;
                    System.out.println("Paying out $" + payout[amountToPay] + " to player " + ply.getid());
                    ply.addMoney(payout[amountToPay]);
                }
                if (rolesIndex == 0) {
                    rolesIndex = onCard.length;
                }
            }

            // payout to the extras
            for (Roles r : roles) {
                Player ply = r.getIsTaken();
                if (ply != null) {
                    ply.removeChips();
                    ply.addMoney(r.getRank());
                    ply.setRole(null);
                    //r.setIsTaken(null);
                    System.out.println("Paying out $" + r.getRank() + " to player " + ply.getid());
                }
            }
        } else {
            //popup window saying no one was on the scene so no one gets an extra payout
            JOptionPane.showMessageDialog(null, "No one was on the scene so no one gets an extra payout");
            for (Roles r : roles) {
                Player ply = r.getIsTaken();
                if (ply != null) {
                    ply.removeChips();
                    ply.setRole(null);
                    //r.setIsTaken(null);
                }
            }
        }
        Systems.getInstance().getBoardPanel().remove(scene);
        scene = null;
        Systems.getInstance().finishScene();
    }

    public void finishShot(){
        //subtract 1 from shotsLeft
        //if shotsLeft == 0, call finishScene

        this.shotsLeft -= 1;
        shotLabelList.get(shotsLeft).setVisible(true);
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