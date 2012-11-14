/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

// Original Code by Tekkotsu Developers

import java.net.DatagramSocket;
import java.net.*;


public abstract class UDPListener extends Listener {
  protected abstract void connected(DatagramSocket socket, DatagramPacket firstPacket);

  byte[] incomingbuf = new byte[1<<16];
  DatagramPacket incoming = new DatagramPacket(incomingbuf, incomingbuf.length);

  byte[] buf = ("connection request").getBytes();

	int _lastPort=-1; // keep track of previously used port number so we can resume connections

    @Override
    protected void runServer() {
        System.out.println("ERROR: UDP server listener is not implemented");
    }

    @Override
    protected void runConnect() {


		int attempts=0;
		Thread me = Thread.currentThread();
		while (me==_listenerThread && !destroy) {
			if(attempts==0) {
				System.out.println("["+_port+"] connecting ...");
			}
			try {
				if(_lastPort==-1)
					_socket=new DatagramSocket();
				else
					_socket=new DatagramSocket(_lastPort);
				_lastPort=_socket.getLocalPort();

				_socket.connect(InetAddress.getByName(_host), _port);

				// send a dummy message so that the AIBO can see what
				// address to connect its UDP socket to
				DatagramPacket message = new DatagramPacket(buf, buf.length);
				_socket.setSoTimeout(500);
				while(!destroy) {
					try {
						_socket.send(message);
						_socket.receive(incoming);
						break;
					} catch (SocketTimeoutException ex) { }
					catch (SocketException ex) { Thread.sleep(500); }
				}
				_socket.setSoTimeout(0); //set to be blocking again
				System.out.println("["+_port+"] connected ...");

				attempts=0;
				_isConnected=true;
			} catch (Exception ex) { ex.printStackTrace(); }

			if(_isConnected) {
				connected(_socket,incoming);
				if(!destroy)
					System.out.println("["+_port+"] disconnected, attempting to reestablish ..");
			}
			attempts++;
			if(destroy) {
				System.out.println("["+_port+"] connection closed");
				_socket.close();
				break;
			}
			try {
				Thread.sleep(500);
			} catch (Exception ex) {}
		}
  }

  public void close() {
	_listenerThread=null;
	_isConnected=false;
	
	try{_socket.close();}
	catch(Exception e){}
  }

  public UDPListener() { super(); }
  public UDPListener(int port) { super(port); }
  public UDPListener(String host, int port) { super(host, port); }

  DatagramSocket _socket;
}
