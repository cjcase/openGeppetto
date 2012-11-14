
package openGeppetto;

//Code by cjcase

import AIBO.*;

public class controlAdapter {
    
    //Constants
    public static final byte DEFAULT = 0;  
    public static final byte AIBO = 1; //Using robot "index" to allow method overload and general robot interoperability 
    private final int aiboMainPort = 10020;
    private final int aiboWalkPort = 10050;
    private final int aiboHeadPort = 10052;
    private final int aiboStopPort = 10053;
    
    //Global Vars
    private byte currentBot;
    private String host;
    private controlAIBO aiboMain;
    private walkAIBO aiboWalk;
    private headAIBO aiboHead;
    private stopAIBO aiboStop;
    public Listener.ConnectionListener connObs;
            
    //Constructors
    public controlAdapter(){
        currentBot = this.DEFAULT;
    }
    public controlAdapter(byte bot){ 
        currentBot = bot;
        if(currentBot == this.AIBO){
            aiboMain = new controlAIBO();
        }
    }
    public controlAdapter(byte bot, String host){ 
        currentBot = bot;
        this.host = host;
        if(currentBot == this.AIBO){
            aiboMain = new controlAIBO(this.host, aiboMainPort);
            aiboMain.needConnection();
    
            aiboStop = new stopAIBO(host, aiboStopPort);
            aiboStop.needConnection();
        }
    }
    
    public void startHead(){
        if(currentBot == this.AIBO)
            aiboHead = new headAIBO(host, aiboHeadPort);
            aiboHead.needConnection();
            aiboMain.startHead();
    }
    
    public void startWalk(){
        if(currentBot == this.AIBO){
            aiboWalk = new walkAIBO(host, aiboWalkPort);
            aiboWalk.needConnection();
            aiboMain.startWalk();
        }
    }
    
    public void startRaw(){
        if(currentBot == this.AIBO)
            aiboMain.startRaw();
    }
    
    public void panHead(double param){
        if(currentBot == this.AIBO)
            aiboHead.sendCommand("p", param);
    }
    public void nodHead(double param){
        if(currentBot == this.AIBO)
            aiboHead.sendCommand("r", param);   
    }
    
    public void goFwd(double param){
        if(currentBot == this.AIBO)
            aiboWalk.sendCommand("f", param);
    }
    
    public void strafe(double param){
        if(currentBot == this.AIBO)
            aiboWalk.sendCommand("s", param);
    }
    
    public void rotate(double param){
        if(currentBot == this.AIBO){
            // Rotation is both fast and sensitive, so we'll exponentiate it to
			// drag out the low end without sacrificing the high end
			double aval = param; 
            aval *= (aval < 0? aval : -aval);
            aiboWalk.sendCommand("r", aval);
        }
    }
    
    public void toggleStop(){
        aiboStop.toggle();
    }
    
    public void stop(){
        aiboStop.stop();
    }
    
    public void start(){
        aiboStop.start();
    }
    
    public float getPan(){
        if(currentBot == this.AIBO)
            return (float)aiboHead.pan;
        return 0.0f;
    }
    
    public float getNod(){
        if(currentBot == this.AIBO)
            return (float)aiboHead.nod;
        return 0.0f;
    }
    
    public boolean getStopped(){
        if(currentBot == this.AIBO)
            return aiboStop.stopped;
        return true;
    }
    
    public float getFwd(){
        if(currentBot == this.AIBO)
            return (float)aiboWalk.fwd;
        return 0.0f;
    }
    
    public float getRotation(){
        if(currentBot == this.AIBO)
            return (float)aiboWalk.rotate;
        return 0.0f;
    }
     
    public boolean isConnected(){
        return aiboMain._isConnected;
    }
     
    public String getHost(){
        return host;
    }
}
