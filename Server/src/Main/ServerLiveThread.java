/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Module.RecieveStreamFile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nattapatn
 */
public class ServerLiveThread extends Thread {

    Socket socket;

    public ServerLiveThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //Start Server Thread.
            ServerSocket sSocket = new ServerSocket(0);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            int port = sSocket.getLocalPort();
            out.writeInt(port);

            //Server SpeedTest
            ServerFile serverFile = new ServerFile(port + 1);
            serverFile.start();
            
            System.out.println("File No.\tTime Stamp\tNetwork");

            //Reciev File.
            int i =0;
            while (true) {
                Socket socket = sSocket.accept();
                RecieveStreamFile rFile = new RecieveStreamFile(socket,i);
                rFile.start();
                i++;
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerLiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
