/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.coremedia.iso.IsoFile;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class GetDuration {

    private static final String filename = "media/out0.mp4";

    public static void mainb(String[] args) {
        try {
            String filename = GetDuration.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "media/out0.mp4";
            IsoFile isoFile = new IsoFile(filename);
            double lengthInSeconds = (double)
                    isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                    isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
            System.err.println(lengthInSeconds);
        } catch (IOException ex) {
            Logger.getLogger(GetDuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
