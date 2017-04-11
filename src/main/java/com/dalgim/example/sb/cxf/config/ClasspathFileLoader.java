package com.dalgim.example.sb.cxf.config;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Mateusz Dalgiewicz on 11.04.2017.
 */
public class ClasspathFileLoader {

    public static InputStream loadFile(String relativePath) {
        Preconditions.checkNotNull(relativePath, "File relative path cannot be null!");

        URL resource = ClasspathFileLoader.class.getResource(relativePath);
        if (resource != null) {
            try {
                return resource.openStream();
            } catch (IOException e) {
                throw new RuntimeException("IO error while getting file from resources: " + relativePath);
            }
        }
        throw new RuntimeException("FileNotFound in resources: " + relativePath);
    }
}
