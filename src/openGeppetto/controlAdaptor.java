/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

import java.io.*;
import java.net.Socket;
import java.util.Vector;
import java.util.Iterator;
import java.lang.Integer;
import java.util.HashMap;
import java.lang.Class;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.net.SocketException;

/**
 *
 * @author cj
 */
public class controlAdaptor extends TCPListener{
    
    InputStream socketIn;
    PrintStream socketOut;
    final int defPort = 10052;
    
    
    @Override
    protected void connected(Socket socket) {
       try {
           socketIn = socket.getInputStream();
       } catch(Exception e) {
       } 
    }
    
}
