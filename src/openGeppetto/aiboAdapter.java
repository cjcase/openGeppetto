
package openGeppetto;

//Code by cjcase

import AIBO.*;

public class aiboAdapter implements Adapter{
    
    private final int aiboMainPort = 10020;
    private final int aiboWalkPort = 10050;
    private final int aiboHeadPort = 10052;
    private final int aiboStopPort = 10053;
    
    //Global Vars
    private String host;
    private controlAIBO aiboMain;
    private walkAIBO aiboWalk;
    private headAIBO aiboHead;
    private stopAIBO aiboStop;
    public Listener.ConnectionListener connObs;
    public boolean tele;
            
    //Constructors
    public aiboAdapter(String host){ 
        this.host = host;
        aiboMain = new controlAIBO(this.host, aiboMainPort);
        aiboMain.needConnection();
    
        aiboStop = new stopAIBO(host, aiboStopPort);
        aiboStop.needConnection();
    }
        
    @Override
    public void startHead(){
        aiboHead = new headAIBO(host, aiboHeadPort);
        aiboHead.needConnection();
        aiboMain.startHead();
    }
    
    @Override
    public void startWalk(){
        aiboWalk = new walkAIBO(host, aiboWalkPort);
        aiboWalk.needConnection();
        aiboMain.startWalk();
    }
    
    @Override
    public void startVision(){
        aiboMain.startRaw();
    }
    
    @Override
    public void panHead(double param){
        aiboHead.sendCommand("p", param);
    }
    
    @Override
    public void nodHead(double param){
        aiboHead.sendCommand("r", param);   
    }
    
    @Override
    public void tiltHead(double param){
        aiboHead.sendCommand("t", param);
    }
    
    @Override
    public void forward(double param){
        aiboWalk.sendCommand("f", param);
    }
    
    @Override
    public void strafe(double param){
        aiboWalk.sendCommand("s", param);
    }
    
    @Override
    public void rotate(double param){
        // Rotation is both fast and sensitive, so we'll exponentiate it to
        // drag out the low end without sacrificing the high end
		double aval = param; 
        aval *= (aval < 0? aval : -aval);
        aiboWalk.sendCommand("r", aval);
    }
    
    @Override
    public void toggleStop(){
        aiboStop.toggle();
    }
    
    @Override
    public void stop(){
        aiboStop.stop();
    }
    
    @Override
    public void start(){
        aiboStop.start();
    }
    
    public float getPan(){
        return (float)aiboHead.pan;
    }
    
    public float getNod(){
        return (float)aiboHead.nod;
    }
    
    public float getTilt(){
        return (float)aiboHead.tilt;
    }
    
    public boolean getStopped(){
        return aiboStop.stopped;
    }
    
    public float getFwd(){
        return (float)aiboWalk.fwd;
    }
    
    public float getRotation(){
        return (float)aiboWalk.rotate;
    }
     
    public boolean isConnected(){
        return aiboMain._isConnected;
    }
     
    public String getHost(){
        return host;
    }
}
