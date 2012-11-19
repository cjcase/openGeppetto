/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AIBO;

// Code by cjcase

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import openGeppetto.TCPListener;

public class walkAIBO extends TCPListener{
    
    //Global Variables
    Socket sock;
    InputStream socketIn;
    OutputStream socketOut;
    
    //Walk directions
    public double fwd = 0.0;
    public double strafe = 0.0;
	public double rotate = 0.0;
    
    //Constructors
    public walkAIBO() { 
        super();
    }
    public walkAIBO(String host) { 
        super(host, 10050); //Tekkotsu default walk control port
    } 
    public walkAIBO(String host, int port) { 
        super(host, port);
    }

    public void sendCommand(String command, double param){
        if (socketOut == null) {
          return;
        }
        // Extract command byte
        byte cmdbytes[] = command.getBytes();
            if(cmdbytes[0] == 'f')
                fwd = param;
            else if(cmdbytes[0] == 'r')
                rotate = param;
            else if(cmdbytes[0] == 's')
                strafe = param;

        // Construct the command sequence
        byte sequence[] = new byte[5];
        // The commmand byte is the first byte in cmdbytes. The remaining
        // four bytes belong to the parameter. We have to convert the parameter
        // (which we send as a float, not a double) to MIPS byte order thanks to
        // (ahem) prior design decisions.
        sequence[0] = cmdbytes[0];
        int pbits = Float.floatToIntBits((float) param);
        Integer i;
        i = new Integer((pbits >> 24) & 0xff); sequence[4] = i.byteValue();
        i = new Integer((pbits >> 16) & 0xff); sequence[3] = i.byteValue();
        i = new Integer((pbits >>  8) & 0xff); sequence[2] = i.byteValue();
        i = new Integer(pbits & 0xff);	   sequence[1] = i.byteValue();
        // Now write the whole command.
        try {
          socketOut.write(sequence, 0, 5);
        } catch(Exception e) { close(); return; }
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
			socketOut = sock.getOutputStream();
			socketIn = sock.getInputStream();
			fireConnected();
			while (true) { //block until the socket is closed
				//readLine(socketIn);
                refresh();
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
		if(!destroy)
			System.out.println("AIBO Walk Control :: connection closed... reconnecting");
		try { Thread.sleep(5000); } catch (Exception e) {}
    }

    public void refresh() {
        if(_isConnected) {
			sendCommand("f",fwd);
			sendCommand("s",strafe);
			sendCommand("r",rotate);
            try { Thread.sleep(1000); } catch (Exception e) {}
		}
    }
    
}
