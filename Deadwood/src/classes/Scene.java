package classes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Scene extends JLabel {
    private int budget;
    private String name;
    private String description;
    private int sceneNum;
    private Roles[] roles;
    
    public Scene(String name, int budget, int sceneNum) {
        this.name = name;
        this.budget = budget;
        this.sceneNum = sceneNum;
        try {
            BufferedImage myPicture = ImageIO.read(new File("./images/cards/" + (sceneNum < 10 ? "0" : "") + sceneNum + ".png"));
            setIcon(new ImageIcon(myPicture));
        } catch (Exception e) {
            System.exit(1);
        }
    }

    //create the getters and setters for the class
    public int getBudget() {
        return budget;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public Roles[] getRoles(){
        return roles;
    }

    public void setRoles(Roles[] roles){
        this.roles = roles;
    }
}