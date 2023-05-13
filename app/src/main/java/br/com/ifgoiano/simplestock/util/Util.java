package br.com.ifgoiano.simplestock.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Util {

    public byte[] convertBitmapForBytes(Bitmap imageBitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}
