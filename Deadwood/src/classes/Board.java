package classes;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private Set[] sets = new Set[10];
    private Set[] specialSets = new Set[2];
    private Scene[] scenes;
    private Player[] players;
    private int[] location;
    private int scenesLeft;
    private static Random rand = new Random();
    
    public Board() {
        buildSets();
        buildScenes();
        scenesLeft = 40;
        shuffleScenes();
    }
    
    public void shuffleScenes() {
        for (int i = 0; i < sets.length; i++) {
            int swapInd = rand.nextInt(scenesLeft);
            sets[i].setScene(scenes[swapInd]);
            Scene swap = scenes[scenesLeft - 1];
            scenes[scenesLeft - 1] = scenes[swapInd];
            scenes[swapInd] = swap;
            scenesLeft--;
        }
    }

    public void updateLocations(Set set) {
        int numOfPlayersOffCard = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getlocation() == set) {
                if (players[i].getRole() == null) {
                    if (numOfPlayersOffCard * players[i].getWidth() > set.getWidth()) {
                        players[i].setLocation(set.getX() + (numOfPlayersOffCard - 1 - set.getWidth() / players[i].getWidth()) * players[i].getWidth(),
                                set.getY() + players[i].getHeight());
                    } else
                        players[i].setLocation(set.getX() + numOfPlayersOffCard * players[i].getWidth(),
                                set.getY());
                    numOfPlayersOffCard++;
                } else {
                    //
                }
            }
        }
    }

    public boolean setLocation(Player ply, Set set) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(ply)) {
                for (int k = 0; k < sets.length; k++) {
                    if (set.equals(sets[k])) {
                        location[i] = k;
                        ply.setLocation(set.getLocation());
                        updateLocations(set);
                        return true;
                    }
                }
                for (int k = 0; k < specialSets.length; k++) {
                    if (specialSets[k] == set) {
                        location[i] = sets.length + k;
                        ply.setLocation(set.getLocation());
                        updateLocations(set);
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
        location = new int[players.length];
    }

    public Set getSetByName(String name) {
        for (int k = 0; k < sets.length; k++) {
            if (sets[k].getName().equals(name)) {
                return sets[k];
            }
        }
        
        for (int k = 0; k < specialSets.length; k++) {
            if (specialSets[k].getName().equals(name)) {
                return specialSets[k];
            }
        }
        
        return null;
    }
    
    private void buildScenes() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(new File("./cards.xml"));
            Element root = dom.getDocumentElement();
            NodeList cardList = root.getElementsByTagName("card");
            int numCards = cardList.getLength();
            scenes = new Scene[numCards];

            for (int i = 0; i < numCards; i++) {
                Element cardElement = (Element) cardList.item(i);
                String name = cardElement.getAttribute("name");
                int budget = Integer.parseInt(cardElement.getAttribute("budget"));
                NodeList sceneList = cardElement.getElementsByTagName("scene");
                Element sceneElement = (Element) sceneList.item(0);
                int sceneNum = Integer.parseInt(sceneElement.getAttribute("number"));
                String description = sceneElement.getTextContent().trim();

                NodeList partList = cardElement.getElementsByTagName("part");
                int numRoles = partList.getLength();
                Roles[] roles = new Roles[numRoles];

                for (int j = 0; j < numRoles; j++) {
                    Element partElement = (Element) partList.item(j);
                    String roleName = partElement.getAttribute("name");
                    int roleLevel = Integer.parseInt(partElement.getAttribute("level"));
                    roles[j] = new Roles(roleName, roleLevel);
                }

                scenes[i] = new Scene(name, budget, sceneNum);
                scenes[i].setDescription(description);
                scenes[i].setRoles(roles);
            }
        } catch (Exception e) {
        }
    }

    private void buildSets() {
        sets[0] = new Set("Main Street", 3);
        sets[1] = new Set("Saloon", 2);
        sets[2] = new Set("Bank", 1);
        sets[3] = new Set("Hotel", 3);
        sets[4] = new Set("Church", 2);
        sets[5] = new Set("Ranch", 2);
        sets[6] = new Set("Secret Hideout", 3);
        sets[7] = new Set("General Store", 2);
        sets[8] = new Set("Train Station", 3);
        sets[9] = new Set("Jail", 1);
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
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
    
            // Parse the XML file
            Document document = builder.parse(new File("./board.xml"));
    
            Element root = document.getDocumentElement();
            NodeList list = root.getElementsByTagName("set");
            
            for (int setInd = 0; setInd < sets.length; setInd++) {
                Node set = list.item(setInd);
                String name = set.getAttributes().getNamedItem("name").getNodeValue();
                Set s = getSetByName(name);
                if (s == null) {
                    System.err.println("Couldn't find set " + name);
                    return;
                }
                s.setLabel(name);
                Systems.getInstance().getBoardPanel().add(s);
                NodeList children = set.getChildNodes();
                
                for (int j = 0; j < children.getLength(); j++) {
                    Node sub = children.item(j);
                    String cname = sub.getNodeName();
                    if (cname.equals("area")) {
                        s.setLocation(Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue()), Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue()));
                        s.setSize(Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue()), Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue()));
                        continue;
                    }
                    if (cname.equals("parts")) {
                        NodeList parts = sub.getChildNodes();
                        Roles[] rArr = new Roles[(int) Math.floor(parts.getLength() / 2.0)];
                        int realIndex = 0;
                        for (int partInd = 0; partInd < parts.getLength(); partInd++) {
                            Node part = parts.item(partInd);
                            if (!part.getNodeName().equals("part"))
                                continue;
                            NamedNodeMap attribs = part.getAttributes();
                            rArr[realIndex] = new Roles(attribs.getNamedItem("name").getNodeValue()
                                , Integer.parseInt(attribs.getNamedItem("level").getNodeValue()));
                            realIndex++;
                        }
                        s.setRoles(rArr);
                    }
                }
            }
            specialSets[0].setBounds(991, 248, 194, 201);
            specialSets[1].setBounds(9, 459, 208, 209);
            Systems.getInstance().getBoardPanel().repaint();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}