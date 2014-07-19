package com.bellotech.chanlurker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bellotech.chanlurker.enums.ImageDimensions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class DownloadThumbNailTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    String url;
    String tempFileName;
    Context context;
    List<String> tempFilePathCache;
    boolean isDownload;
    ImageDimensions dim;

    public DownloadThumbNailTask(ImageView bmImage, String tempFileName, List<String> tempFilePathCache, Context context, boolean isDownload, ImageDimensions dim) {
        this.bmImage = bmImage;
        this.tempFileName = tempFileName;
        this.tempFilePathCache = tempFilePathCache;
        this.context = context;
        this.isDownload = isDownload;
        this.dim = dim;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap mIcon11 = null;
        if(isDownload) {
            url = urls[0];
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                tempFilePathCache.add(saveImageToInternalStorage(mIcon11));
            } catch (Exception e) {
            }
        }else{
            mIcon11 = loadImageFromInternalStorage();
        }
        return mIcon11;
    }

    protected void onPreExecute(){
        bmImage.setVisibility(View.GONE);
    }

    protected void onPostExecute(Bitmap result) {
        if(result != null) {
            bmImage.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params =  bmImage.getLayoutParams();
            if(dim == ImageDimensions.ThreadThumbNail) {
                params.height = 350;
                params.width = 350;
            }else if(dim == ImageDimensions.ThreadScaled) {
                params.height = 175;
                params.width = 175;
            }else {
                params.height = result.getHeight();
                params.width = result.getWidth();
            }
            bmImage.setLayoutParams(params);
            bmImage.setImageBitmap(result);
        }
    }

    private String saveImageToInternalStorage(Bitmap image) {
        String tempFilePath = null;
        try {
            FileOutputStream fos = context.openFileOutput(tempFileName, Context.MODE_PRIVATE);
            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {

        }
        return tempFilePath;
    }

    private Bitmap loadImageFromInternalStorage() {
        Bitmap img = null;
        try {
            FileInputStream fis = context.openFileInput(tempFileName);
            img = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (Exception e) {

        }
        return img;
    }
}
