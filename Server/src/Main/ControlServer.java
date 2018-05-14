/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Module.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class ControlServer extends Thread {

    int port;

    public ControlServer(int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);

            for (int i = 0; true; i++) {
                Socket socket = server.accept();

                RecieveFile file = new RecieveFile(socket, "media/test" + i + ".db");
                file.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
