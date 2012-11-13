/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AIBO;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import openGeppetto.TCPListener;

// Code by cjcase
public class stopAIBO  extends TCPListener{
    //Global Variables
    private Socket sock;
    private InputStream socketIn;
    private PrintStream socketOut;
    public boolean stopped;
    
    //Constructors
    public stopAIBO(){ 
        super();
        stopped = true;
    }
    public stopAIBO(String host) {
        super(host, 10053); //Tekkotsu default emergency stop port
        stopped = true;
    } 
    public stopAIBO(String host, int port) { 
        super(host, port);
        stopped = true;
    }
    
    private void getStatus() throws Exception{
        socketOut.println("refresh");
        String status = readLine(socketIn);
        if(status.equals("on")){
            stopped = true;
        } else if(status.equals("on")){
            stopped = false;
        }
    }
    
    public void start(){
        socketOut.println("start");
        stopped = false;
    }
    
    public void stop(){
        socketOut.println("stop");
        stopped = true;
    }
    
    public void toggle(){
        if(stopped){
            this.start();
        } else {
            this.stop();
        }
    }
    
    @Override
    protected void connected(Socket socket) {
        sock = socket;
        try {
			sock.setTcpNoDelay(true);
            try {
                sock.setTrafficClass(0x10);
            } catch(SocketException e) {
                System.out.println("Connection Error! >> " + e.getMessage());
            }
			socketOut = new PrintStream(sock.getOutputStream());
			socketIn = sock.getInputStream();
			fireConnected();
			while (true) { //block until the socket is closed
				this.getStatus(); //TODO: Diseñar el observador para el cambio de estado.
				if(!_isConnected) break;
			}
        } catch(SocketException e) {
            System.out.println("Connection Error! >> " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Connection Error! >> " + e.getMessage());
        } finally {
            fireDisconnected();
        }

		try { socket.close(); } catch (Exception e) { System.out.println("Connection Error! >> " + e.getMessage()); }
		_isConnected=false;
		      
		try { Thread.sleep(5000); } catch (Exception e) {}
  }
}
