/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tony
 */
public class SpeedTest_Server {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9000);

            Socket socket = server.accept();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            
            long size = dis.readLong();
            FileOutputStream fos = new FileOutputStream("out.db");
            byte[] buffer = new byte[4096];
            int filesize = (int) size;
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }   dos.writeLong(System.currentTimeMillis());
            fos.close();
            dis.close();
            
        } catch (IOException ex) {
            Logger.getLogger(SpeedTest_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
