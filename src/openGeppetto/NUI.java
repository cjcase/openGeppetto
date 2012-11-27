/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

// Code by cjcase

import java.util.ArrayList;
import java.util.HashMap;
import org.OpenNI.Point3D;

public class NUI {
    
    //Instance
    private static volatile NUI instance = null;
    
    //Global vars
    private int width, height;
    private ArrayList<Adapter> bot;
    private ArrayList<String> gestures;
    private HashMap<Integer, String> hands;
    private boolean multipleBots;
    //private float kinectX;
    //private float kinectY;
    private boolean neo;
    
    public static NUI getInstance(){
        if (instance == null){
            synchronized (NUI  .class){
                if (instance == null)
                    instance = new NUI();
            }
        }
        return instance;
    }    
    
    //defaults
    private NUI(){
        bot = new ArrayList<Adapter>();
        gestures = new ArrayList<String>();
        hands = new HashMap<Integer, String>();
        width = 640;
        height = 480;
        multipleBots = false;
        gestures.add("Wave");
        gestures.add("Click");
        neo = true;
    }
    
    public void addBot(Adapter b){
        if(!multipleBots && bot.size() >= 1)
            multipleBots = true;
        this.bot.add(b);
    }
    
    public void addGesture(String s){
        gestures.add(s);
    }
    
    public String[] getGestures(){
        Object aux[] = gestures.toArray();
        String ret[] = new String[aux.length];
        for(int i = 0; i < aux.length; i++){
            ret[i] = (String) aux[i];
        }
        return ret;
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void gestureRecognized(String g) throws Exception{
        //Robot validation
        if(bot == null || bot.isEmpty())
            throw new NoRobotsException();
        
        //Stop all bots!
        if(g.equals("Click")){
           this.toggleAll();
        }   
    }
    
    public void handCreated(int id) throws Exception{
        //Robot validation
        if(bot == null || bot.isEmpty())
            throw new NoRobotsException();
        
        //Register hands to control
        if(hands.isEmpty()){
            System.out.println("Hand Id " + id + " now controlling head.");
            hands.put(id, "Head");
        } else if(hands.size() == 1) {
            if(hands.containsValue("Head")){
                System.out.println("Hand Id " + id + " now controlling walking direction.");
                hands.put(id, "Walk");
            } else if (hands.containsValue("Walk")){
                System.out.println("Hand Id " + id + " now controlling head.");
                hands.put(id, "Head");
            }
        } else {
            System.out.println("Hand Id " + id + " ignored, controls already set.");
        }
    }
    
    public void handMoved(int id, Point3D point) throws Exception{
        //Robot validation
        if(bot == null || bot.isEmpty())
            throw new NoRobotsException();
        
        //Check if hand is control
        if(!hands.containsKey(id))
            return;
        
        float kinectX = point.getX();
        float kinectY = point.getY();
        
        //Point processing to buffer movement
        //using new NUI
        //int xWalk, yWalk, xHead, yHead;
        //xWalk = (width / 2)/2;
        //yWalk = (height / 2);
        //xHead = (width / 2)+(width / 4);
        //yHead = (height / 2);
        
        kinectY = (kinectY * 10000)/(height / 2);
        if(kinectY > 10000) kinectY = 10000;
        if(kinectY < -10000) kinectY = -10000;
        kinectY /= 10000;
        
        
        kinectX = (kinectX * 10000)/(width / 2);
        if(kinectX > 10000) kinectX = 10000;
        if(kinectX < -10000) kinectX = -10000;
        kinectX /= 10000;
        
                
        if(!multipleBots){
            aiboAdapter auxBot = (aiboAdapter) bot.get(0); //Avoid getting from the list every call
            if(auxBot.isConnected()){
                if(hands.get(id).equals("Head")){                     
                    if(neo){
                        
                        //if(kinectX < 0) kinectX = 0;        
                                                
                        //kinectX = (float) (2 * kinectX - (Math.floor(kinectX)-1));
                        
                        //TODO Debug
                        System.out.println("X: "+kinectX);
                    }
                    auxBot.panHead(kinectX * -1);
                    auxBot.nodHead(kinectY);
                } else if(hands.get(id).equals("Walk")){
                    auxBot.rotate(kinectX);
                    auxBot.forward(kinectY);
                }
            }
        } else { //If we have multiple bots attached to one control (head example)
            int nBots = bot.size();
            for(int i = 0; i < nBots; i++){
                if(((aiboAdapter)bot.get(i)).isConnected()){
                    if(hands.get(id).equals("Head")){
                        bot.get(i).panHead(kinectX * -1);
                        bot.get(i).nodHead(kinectY);
                    } else if(hands.get(id).equals("Walk")){
                        bot.get(i).rotate(kinectX);
                        bot.get(i).forward(kinectY);
                    }
                }
            }
        }
    }
    
    public void handLost(int id) throws Exception{
        //Robot validation
        if(bot == null || bot.isEmpty())
            throw new NoRobotsException();
        if(hands.containsKey(id)){
            System.out.println("Removed hand id "+ id + " " + hands.get(id) + " control lost.");
            hands.remove(id);
        }
    }
    
    private float processPoint(float n){
        float aux = (n * 10000)/(width / 2);
        if(aux > 10000) n = 10000;
        if(aux < -10000) n = -10000;
        n /= 10000;
        return n;
    }
    
    private void toggleAll(){
        int nBots = bot.size();
        for(int i = 0; i < nBots; i++){
            bot.get(i).toggleStop();
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean getNeo() {
        return neo;
    }

    public void setNeo(boolean neo) {
        this.neo = neo;
    }
    
}
