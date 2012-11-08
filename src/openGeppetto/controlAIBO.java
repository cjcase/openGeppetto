/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author cj
 */
public class controlAIBO extends TCPListener{
    
    Socket sock;
    InputStream socketIn;
    PrintStream socketOut;
    
    //Aibo Head variables
    private int centralPort = 10020;
    double pan = 0.0;
	double roll = 0.0;
    
    //Constructors
    public controlAIBO() { super(); }
    public controlAIBO(String host) { 
        super(host, 10020); //Tekkotsu default control port
    } 
    public controlAIBO(String host, int port) { super(host, port); }
    
    private void startHead(){
        socketOut.println("!root \"TekkotsuMon\" \"HeadController\"");
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
            socketOut.println("!hello");
            socketOut.println("!dump_stack");
            socketOut.println("!refresh");
            this.startHead();
			while (true) { //block until the socket is closed
				readLine(socketIn);
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
		        
		//The sleep is to get around the socket still listening after being closed
		//if(!destroy)
			//System.out.println("HeadPoint - connection closed... reconnect after 5 seconds");
		try { Thread.sleep(5000); } catch (Exception e) {}
  }
    
}
