/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class RecieveFile extends Thread{
    
    Socket socket;
    String filename;

    public RecieveFile(Socket socket,String filename) {
        this.socket =socket;
        this.filename = filename;
    }

    public void run() {
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            long size = dis.readLong();
            FileOutputStream fos = new FileOutputStream(filename+"_o");
            byte[] buffer = new byte[4096];
            int filesize = (int) size;
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }   fos.close();
            dos.writeLong(0);
            dis.close();
            
            sleep(1000);
            File oldFile = new File(filename+"_o");
            File newFile = new File(filename);
            oldFile.renameTo(newFile);
        } catch (IOException ex) {
            Logger.getLogger(RecieveFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RecieveFile.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                dos.close();
            } catch (IOException ex) {
                Logger.getLogger(RecieveFile.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}
