/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Module.ClearOldData;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nattapatn
 */
public class Server {
    
    public static void main(String [] args){
        try {
            System.out.println("[Server Start]");
            
            ServerSocket sSocket = new ServerSocket(9090);
            while(true){
                Socket socket = sSocket.accept();
                System.out.print("Connected form :");
                System.out.println(socket.getRemoteSocketAddress());
                
                //Clear Data.
                ClearOldData clearData = new ClearOldData();
                clearData.clear();
                
                //Server Live Start.
                ServerLiveThread slt = new ServerLiveThread(socket);
                slt.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
