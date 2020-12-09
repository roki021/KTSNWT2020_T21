package com.culturaloffers.maps.helper;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.FileOutputStream;
import java.util.UUID;

public class ImageHandler {
    public static String saveImage(String path, String imgBase64)
    {
        try
        {
            byte[] imageByte= Base64.decodeBase64(imgBase64);
            final String imageName = UUID.randomUUID().toString().replace("-", "");
            String imagePath = path + imageName+".jpg";

            new FileOutputStream(imagePath).write(imageByte);
            return imagePath;
        }
        catch(Exception e)
        {
            return "error = "+e;
        }
    }
}
