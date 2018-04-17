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
 * @author nattapatn
 */
public class ClearOldData {
    
    public ClearOldData(){}
    
    public void clear() {
        File folder = new File("media");
        System.out.println("[Clear Data]");
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                try {
                    Path a = Paths.get("media/" + fileEntry.getName());
                    Files.delete(a);
                    System.out.println(fileEntry.getName()+" was delete!!");
                } catch (IOException ex) {
                    Logger.getLogger(ClearOldData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
