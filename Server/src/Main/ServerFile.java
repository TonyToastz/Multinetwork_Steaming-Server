/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Module.SpeedTest;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nattapatn
 */
public class ServerFile extends Thread{
    
    int port;
    
    public ServerFile(int port){
        this.port = port;
    }
    
    public void run(){
        try {
            ServerSocket sSocket = new ServerSocket(port);
            while(true){
                Socket socket = sSocket.accept();
                
                SpeedTest speedTest = new SpeedTest(socket);
                speedTest.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
