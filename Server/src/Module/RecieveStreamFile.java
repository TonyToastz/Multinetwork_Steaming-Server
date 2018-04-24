/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import Main.Player;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class RecieveStreamFile extends Thread {

    Socket socket;
    int filename;
    int link;
    long start;

    public RecieveStreamFile(Socket socket, int filename,int link,long start) {
        this.socket = socket;
        this.filename = filename;
        this.link=link;
        this.start=start;
    }

    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            long size = dis.readLong();
            
            FileOutputStream fos = new FileOutputStream("media/out"+filename+".mp4_O");
            byte[] buffer = new byte[4096];
            
            int filesize = (int) size;
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }
            fos.close();
            dis.close();
            sleep(1000);
            System.out.println(filename+"\t\t"+System.currentTimeMillis()+"\t"+socket.getRemoteSocketAddress());
            File oldFile = new File("media/out"+filename+".mp4_O");
            File newFile = new File("media/out"+filename+".mp4");
            oldFile.renameTo(newFile);
            
            if(filename==link-1){
                if(System.currentTimeMillis()-start<40000){
                    sleep(40000-(System.currentTimeMillis()-start));
                }
                Player player =new Player(link);
                player.start();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecieveStreamFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecieveStreamFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RecieveStreamFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
