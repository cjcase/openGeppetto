package AIBO;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import openGeppetto.TCPListener;
import openGeppetto.Listener;

//Code by cjcase
public class controlAIBO extends TCPListener implements Listener.ConnectionListener{
    
    //Global Variables
    Socket sock;
    InputStream socketIn;
    PrintStream socketOut;
    
    //Constructors
    public controlAIBO() { 
        super();
    }
    public controlAIBO(String host) {
        super(host, 10020); //Tekkotsu default control port
    } 
    public controlAIBO(String host, int port) { 
        super(host, port); 
    }
    
    public void startHead(){
        socketOut.println("!root \"TekkotsuMon\" \"HeadController\"");
    }
    
    public void startWalk(){
        socketOut.println("!root \"TekkotsuMon\" \"WalkController\"");
    }
    
    public void startRaw(){
        socketOut.println("!root \"TekkotsuMon\" \"RawCam\"");
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
		      
		try { Thread.sleep(5000); } catch (Exception e) {}
  }

    @Override
    public void onConnected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDisconnected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
