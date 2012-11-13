
package openGeppetto;

//Code by cjcase

import AIBO.*;

public class controlAdaptor {
    
    //Constants
    public static final byte DEFAULT = 0;  
    public static final byte AIBO = 1; //Using robot "index" to allow method overload and general robot interoperability 
    private final int aiboMainPort = 10020;
    private final int aiboHeadPort = 10052;
    private final int aiboStopPort = 10053;
    
    //Global Vars
    private byte currentBot;
    private controlAIBO aiboMain;
    private headAIBO aiboHead;
    private stopAIBO aiboStop;
    public Listener.ConnectionListener connObs;
            
    //Constructors
    public controlAdaptor(){
        currentBot = this.DEFAULT;
    }
    public controlAdaptor(byte bot){ 
        currentBot = bot;
        if(currentBot == this.AIBO){
            aiboMain = new controlAIBO();
        }
    }
    public controlAdaptor(byte bot, String host){ 
        currentBot = bot;
        if(currentBot == this.AIBO){
            aiboMain = new controlAIBO(host, aiboMainPort);
            aiboMain.addConnectionListener(connObs);
            
            aiboStop = new stopAIBO(host, aiboStopPort);
            aiboStop.needConnection();
            
            aiboHead = new headAIBO(host, aiboHeadPort);
            aiboHead.needConnection();
        }
    }
    
    public void panHead(double param){
        if(currentBot == this.AIBO)
                aiboHead.sendCommand("p", param);
    }
    public void nodHead(double param){
        if(currentBot == this.AIBO)
               aiboHead.sendCommand("r", param);   
    }
    
    public void toggleStop(){
        aiboStop.toggle();
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
     
    public boolean isConnected(){
        return aiboHead._isConnected;
    }
        
}
