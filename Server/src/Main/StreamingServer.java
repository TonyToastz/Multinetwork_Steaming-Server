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
public class StreamingServer extends Thread {

    int port;
    RecieveFile file;
    int link;

    public StreamingServer(int port, int link) {
        this.port = port;
        this.link = link;
    }

    public void run() {

        try {
            ServerSocket server = new ServerSocket(port);
            boolean firstTime = true;
            long start = System.currentTimeMillis();
            for (int i = 0; true; i++) {
                Socket socket = server.accept();
                System.out.println("Recieve : " + i);

                RecieveFile file = new RecieveFile(socket, "media/out" + i + ".mp4");
                file.start();

                if (System.currentTimeMillis() - start > 40000 && firstTime && i >= link) {
                    Player player = new Player(link);
                    player.start();
                    firstTime = false;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(StreamingServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
