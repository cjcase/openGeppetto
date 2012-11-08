/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

/**
 *
 * @author cj
 */
public class controlAdaptor {
    
    //Constants
    public static final byte DEFAULT = 0;  
    public static final byte AIBO = 1; //Using robot "index" to allow method overload and general robot interoperability 
    
    //Global Vars
    private byte currentBot;
    private controlAIBO aiboMain;
    private headAIBO aiboHead;
    
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
            aiboMain = new controlAIBO(host);
            aiboMain.needConnection();
            
            aiboHead = new headAIBO(host);
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
    
    public boolean isConnected(){
        return aiboHead._isConnected;
    }
        
}
