/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Module.SetEnvironment;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class Run_Me {
    
    public static void main(String [] args){
        try {
            System.out.println("[Server Start]");
            
            ServerSocket serverSocket = new ServerSocket(9090);
            
            while(true){
                Socket socket = serverSocket.accept();
                
                System.out.print("Connected form :");
                System.out.println(socket.getRemoteSocketAddress());
                
                SetEnvironment environment = new SetEnvironment();
                environment.setup();
                
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Run_Me.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
