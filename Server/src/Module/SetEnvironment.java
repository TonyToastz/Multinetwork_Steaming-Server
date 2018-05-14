/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class SetEnvironment {

    public SetEnvironment() {
    }

    public void setup() {
        File folder = new File("media");
        if (!folder.exists()) {
            folder.mkdir();
        } else {
            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    try {
                        Path a = Paths.get("media/" + fileEntry.getName());
                        Files.delete(a);
                    } catch (IOException ex) {
                        Logger.getLogger(SetEnvironment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
