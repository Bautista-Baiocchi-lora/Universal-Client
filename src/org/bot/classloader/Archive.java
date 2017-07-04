package org.bot.classloader;

/**
 * Created by Ethan on 6/27/2017.
 */

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Archive<T> extends Iterable<T> {

    int TMP_LEN = 1024;


    byte[] getEntry(String name) throws ClassNotFoundException;


    Map<String, T> load() throws IOException;


    void write(File file) throws IOException;

    T get(String string);

}