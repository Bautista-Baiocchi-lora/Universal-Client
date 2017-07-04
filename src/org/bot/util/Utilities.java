package org.bot.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ethan on 7/3/2017.
 */
public class Utilities {
    public static Image getLocalImage(String file) {
        try {

            return new ImageIcon(Utilities.class.getClass().getResource(file)).getImage();
        } catch (NullPointerException e) {
            System.out.println("[Error] Cannot load this Image " + file);
            e.printStackTrace();
            return null;
        }
    }
}