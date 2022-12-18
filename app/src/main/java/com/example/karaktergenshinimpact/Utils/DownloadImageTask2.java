package com.example.karaktergenshinimpact.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask2 extends AsyncTask<String, Integer, Drawable> {
    ImageView bmImage;

    public DownloadImageTask2(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected Drawable doInBackground(String... arg0) {
        // This is done in a background thread
        return downloadImage(arg0[0]);
    }

    /**
     * Called after the image has been downloaded
     * -> this calls a function on the main thread again
     */
    protected void onPostExecute(Drawable image)
    {
        bmImage.setImageDrawable(image);
    }


    /**
     * Actually download the Image from the _url
     * @param _url
     * @return
     */
    private Drawable downloadImage(String _url)
    {
        //Prepare to download image
        URL url;
        BufferedOutputStream out;
        InputStream in;
        BufferedInputStream buf;

        //BufferedInputStream buf;
        try {
            url = new URL(_url);
            in = url.openStream();

            /*
             * THIS IS NOT NEEDED
             *
             * YOU TRY TO CREATE AN ACTUAL IMAGE HERE, BY WRITING
             * TO A NEW FILE
             * YOU ONLY NEED TO READ THE INPUTSTREAM
             * AND CONVERT THAT TO A BITMAP
            out = new BufferedOutputStream(new FileOutputStream("testImage.jpg"));
            int i;

             while ((i = in.read()) != -1) {
                 out.write(i);
             }
             out.close();
             in.close();
             */

            // Read the inputstream
            buf = new BufferedInputStream(in);

            // Convert the BufferedInputStream to a Bitmap
            Bitmap bMap = BitmapFactory.decodeStream(buf);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }

            return new BitmapDrawable(bMap);

        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }

        return null;
    }

}