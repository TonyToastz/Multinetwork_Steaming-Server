/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class ServerThread extends Thread{
    
    Socket socket;
    
    public ServerThread(Socket socket){
        this.socket =socket;
    }
    
    public void run(){
        try {
            //Start Server Thread.
            ServerSocket sSocket = new ServerSocket(0);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            int port = sSocket.getLocalPort();
            dos.writeInt(port);
            sSocket.close();
            
            //Server Test Bandwidth
            ControlServer cServer = new ControlServer(port+1);
            cServer.start();
            
            //Streaming server
            ServerSocket serv = new ServerSocket(port);
            Socket socket = serv.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int link = dis.readInt();
            dis.close();
            socket.close();
            serv.close();
            System.out.println("Link : "+link);
            StreamingServer server = new StreamingServer(port,link);
            server.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
