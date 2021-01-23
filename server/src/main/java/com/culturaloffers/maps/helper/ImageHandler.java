package com.culturaloffers.maps.helper;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ImageHandler {
    public static String saveImage(String path, String imgBase64)
    {
        final String imageName = UUID.randomUUID().toString().replace("-", "");
        String imagePath = path + imageName+".jpg";

        try(OutputStream fos = new FileOutputStream(imagePath))
        {
            byte[] imageByte= Base64.decodeBase64(imgBase64);

            for(byte b : imageByte)
                fos.write(b);

            fos.flush();
            fos.close();
            return "http://localhost:8080/images/" + imageName + ".jpg";
        }
        catch(Exception e)
        {
            return "error = "+e;
        }
    }
}
