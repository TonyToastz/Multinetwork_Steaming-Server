/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nattapatn
 */
public class SpeedTest extends Thread {

    Socket socket;

    public SpeedTest(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
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
            Logger.getLogger(SpeedTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dis.close();
            } catch (IOException ex) {
                Logger.getLogger(SpeedTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
