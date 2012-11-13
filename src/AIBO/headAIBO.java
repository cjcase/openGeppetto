package AIBO;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import openGeppetto.TCPListener;

//Code by cjcase
public class headAIBO extends TCPListener{
    
    //Global Variables
    Socket sock;
    InputStream socketIn;
    OutputStream socketOut;
    
    //Head positions
    public static double pan = 0.0;
	public static double nod = 0.0;
    
    //Constructors
    public headAIBO() { 
        super();
    }
    public headAIBO(String host) { 
        super(host, 10052); //Tekkotsu default head control port
    } 
    public headAIBO(String host, int port) { 
        super(host, port);
    }
    
    public void sendCommand(String command, double param) {
		
    if (socketOut == null) {
      return;
    }
    // Extract command byte
    byte cmdbytes[] = command.getBytes();
		if(cmdbytes[0] == 'p')
			pan = param;
		else if(cmdbytes[0] == 'r')
			nod = param;

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
