/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.xuggle.xuggler.IContainer;

/**
 *
 * @author NattapatN
 */
public class TestgetDuration {

    public static void main(String[] args) {
        String filename = "media/out2.mp4";
        IContainer container = IContainer.make();
        int result = container.open(filename, IContainer.Type.READ, null);
        long duration = container.getDuration();
        System.out.println((double)duration/1000.0);
    }

}
