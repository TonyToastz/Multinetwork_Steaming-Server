/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.xuggle.xuggler.IContainer;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author NattapatN
 */
public class Player extends Thread {

    long start = System.currentTimeMillis();
    MediaListPlayer player;
    MediaList playlist;

    public static void main(String[] args) {
        Player player = new Player();
        player.start();
    }

    public Player() {
    }

    public void run() {
        double time = 0;
        JFrame f = new JFrame();
        f.setLocation(100, 50);
        f.setSize(1000, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        Canvas c = new Canvas();
        c.setBackground(Color.black);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        p.add(c);
        f.add(p);

        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        MediaPlayerFactory mpf = new MediaPlayerFactory();

        EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));

        emp.setVideoSurface(mpf.newVideoSurface(c));

        player = mpf.newMediaListPlayer();
        player.setMediaPlayer(emp);
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);

        playlist = mpf.newMediaList();
        for (int i = 0; i <= 2; i++) {
            if (new File("media/out" + i + ".mp4").exists()) {
                playlist.addMedia("media/out" + i + ".mp4");
                System.out.println("add : " + i + " to playlist");
                String filename = "media/out" + i + ".mp4";
                IContainer container = IContainer.make();
                int result = container.open(filename, IContainer.Type.READ, null);
                long duration = container.getDuration();
                time += (double) duration / 1000.0;
            } else {
                try {
                    sleep(500);
                    i--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        long start = System.currentTimeMillis();

        player.setMediaList(playlist);
        player.setMode(MediaListPlayerMode.DEFAULT);
        player.play();
        int numNW=2;
        int play = 3;
        int end = 0;
        int [] nw=new int[numNW]; 
        int nwlag[]=new int[numNW];
        for(int j=0;j<numNW;j++){
            nw[j]=0;
            nwlag[j]=0;
        }
        int count = 3;
        int lag = 0;
        boolean running = true;
        while (running) {
            if (new File("media/out" + count + ".mp4").exists()) {
                playlist.addMedia("media/out" + count + ".mp4");
                nw[count%numNW]++;
                System.out.println("add : " + count + " to playlist");
                if ((System.currentTimeMillis() - start) > (long) time) {
                    player.playItem(play);
                    start = System.currentTimeMillis() - (long) time;
                    nwlag[count%numNW]++;
                    lag++;
                    System.out.println("lag : " + lag);

                }
                String filename = "media/out" + count + ".mp4";
                IContainer container = IContainer.make();
                int result = container.open(filename, IContainer.Type.READ, null);
                long duration = container.getDuration();
                time += (double) duration / 1000.0;
                play++;
                count++;
                end = 0;
            } else if ((System.currentTimeMillis() - start) > (long) time + 5000) {
                count++;
                end++;

            } else {
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (end >= 5) {
                running = false;
            }
        }
        System.out.println("Streamng Ending!!");
        System.out.println("total lag : " + lag +" times.");
        for(int j=0;j<numNW;j++){
            System.out.println("NW "+(j+1)+" : "+nw[j]+" times. : lag : "+nwlag[j]);
        }
        
    }

}
