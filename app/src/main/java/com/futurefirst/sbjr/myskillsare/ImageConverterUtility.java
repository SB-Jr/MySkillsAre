package com.futurefirst.sbjr.myskillsare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by sbjr on 1/6/16.
 */
public class ImageConverterUtility {

    public static byte[] getByteFromImage(Bitmap image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,0,os);
        return os.toByteArray();
    }

    public static Bitmap getImageFromByteArray(byte[] array){
        Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
        //Log.d("IMAGE THING", "inside the utility class the size of data being "+bitmap.getWidth()+"x"+bitmap.getHeight());
        return bitmap;
    }

}
