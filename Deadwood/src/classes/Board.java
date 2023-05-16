package classes;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Board {
    private Set[] sets = new Set[10];
    private Set[] specialSets = new Set[2];
    private Scene[] scenes = new Scene[40];
    private Player[] players;
    private int[] location;
    private int scenesLeft;
    
    public Board() {
        buildSets();
        buildScenes();
    }

    public Scene[] getScenes() {
        return scenes;
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
            NodeList list = root.getElementsByTagName("card");
            
            for (int i = 0; i < scenes.length; i++) {
                Node node = list.item(i);
                NamedNodeMap attribs = node.getAttributes();
                String name = attribs.getNamedItem("name").getNodeValue();
                int budget = Integer.parseInt(attribs.getNamedItem("budget").getNodeValue());
                int sceneNum = -1;
                NodeList cnodes = node.getChildNodes();
                int numRoles = 0;
                Roles[] rArr = new Roles[4];
                for (int k = 0; k < cnodes.getLength(); k++) {
                    Node part = cnodes.item(k);
                    NamedNodeMap cAttribs = part.getAttributes();
                    if (part.getNodeName().equals("scene")) {
                        sceneNum = Integer.parseInt(attribs.getNamedItem("number").getNodeValue());
                    } else if (part.getNodeName().equals("part")) {
                        rArr[numRoles] = new Roles(cAttribs.getNamedItem("name").getNodeValue()
                                , Integer.parseInt(cAttribs.getNamedItem("level").getNodeValue()));

                        numRoles++;
                    }
                }
                
                if (sceneNum == -1) {
                    System.err.println("Couldn't find scene number");
                    return;
                }
                
                scenes[i] = new Scene(name, budget, sceneNum);
                Roles[] reducedRoles = new Roles[numRoles];
                System.arraycopy(rArr, 0, reducedRoles, 0, numRoles);
                scenes[i].setRoles(reducedRoles);
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
                NodeList children = set.getChildNodes();
                
                for (int j = 0; j < children.getLength(); j++) {
                    Node sub = children.item(j);
                    String cname = sub.getNodeName();
                    if (cname.equals("parts")) {
                        NodeList parts = sub.getChildNodes();
                        Roles[] rArr = new Roles[(int) Math.ceil(parts.getLength() / 2.0)];
                        for (int partInd = 0; partInd < parts.getLength(); partInd++) {
                            Node part = parts.item(partInd);
                            if (!part.getNodeName().equals("part"))
                                continue;
                            
                            int realPartInd = (int) Math.ceil(partInd / 2.0);
                            NamedNodeMap attribs = part.getAttributes();
                            rArr[realPartInd] = new Roles(attribs.getNamedItem("name").getNodeValue()
                                , Integer.parseInt(attribs.getNamedItem("level").getNodeValue()));
                        }
                        s.setRoles(rArr);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}