package aom.pokeapi.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import aom.pokeapi.model.singleton.ImageSingleton;

public class ImageLoader {


    static String TAG = "ImageLoader";

    public static void LoadAsyncImage(Activity activity, View view, String url, int defaultImage){

        ImageSingleton imageSingleton = ImageSingleton.getInstance();
        if(imageSingleton.getImages().containsKey(url)){
            DrawImage(activity, view, imageSingleton.getImages().get(url));
        }else{
            DownloadAsyncImage(activity, view, url, defaultImage, activity);
        }
    }

    public static void LoadAsyncImage(Activity activity, MenuItem menuItem, String url, int defaultImage){

        ImageSingleton imageSingleton = ImageSingleton.getInstance();
        if(imageSingleton.getImages().containsKey(url)){
            DrawMenuImage(activity, menuItem, imageSingleton.getImages().get(url));
        }else{
            DownloadAsyncImage(activity, menuItem, url, defaultImage, activity);
        }
    }

    /**********************************************
     * DRAW PROCESSS
     **********************************************/

    @SuppressLint("NewApi")
    private static void DrawImage(Activity activity, View view, Bitmap bitmap){
        if(view == null) return;
        try{

            BitmapDrawable drawable = new BitmapDrawable(activity.getResources(), bitmap);

            if(view instanceof ImageButton){
                ((ImageButton)view).setImageDrawable(drawable);
            }else if(view instanceof ImageView){
                ((ImageView)view).setImageDrawable(drawable);
            }else{
                view.setBackground(drawable);
            }
        }catch(NoSuchMethodError e){
            Logger.Error(TAG, "DrawImage - NoSuchMethodError: " + e.getMessage());
            Toast.makeText(activity.getApplicationContext(), "No se puede mostrar correctamente las imagenes debido a la version android de su dispositivo", Toast.LENGTH_SHORT).show();

            if(view instanceof ImageButton){
                ((ImageButton)view).setImageBitmap(bitmap);
            }else if(view instanceof ImageView)
            {
                ((ImageView)view).setImageBitmap(bitmap);
            }
        }
    }

    private static void DrawMenuImage(Activity activity, MenuItem menuItem, Bitmap bitmap){
        BitmapDrawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
        menuItem.setIcon(drawable);
    }

    /**********************************************
     * DOWNLOAD PROCESSS
     **********************************************/

    public static void DownloadAsyncImage(final Activity activity, final View view, final String url,final int defaultImage, final Context context)
    {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {

            private Bitmap bitmap;
            @Override
            protected Void doInBackground(String...params) {

                bitmap = DoInBackgroundCallback(url, context);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if(bitmap!=null){
                    try{
                        ImageSingleton.getInstance().getImages().put(url, bitmap);
                        DrawImage(activity, view, bitmap);
                    }catch(Exception e){
                        Logger.Error(TAG, "DownloadAsyncImage - Exception: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if(view instanceof ImageButton){
                    ((ImageButton)view).setImageResource(defaultImage);
                }else if(view instanceof ImageView){
                    ((ImageView)view).setImageResource(defaultImage);
                }
            }
        };
        asyncTask.execute("");
    }

    public static void DownloadAsyncImage(final Activity activity, final MenuItem view, final String url, final int defaultImage, final Context context)
    {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {

            private Bitmap bitmap;
            @Override
            protected Void doInBackground(String...params) {

                bitmap = DoInBackgroundCallback(url, context);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if(bitmap!=null){
                    try{
                        ImageSingleton.getInstance().getImages().put(url, bitmap);
                        DrawMenuImage(activity, view, bitmap);
                    }catch(Exception e){
                        Logger.Error(TAG, "DownloadAsyncImage - Exception: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if(view instanceof ImageButton){
                    ((ImageButton)view).setImageResource(defaultImage);
                }else if(view instanceof ImageView){
                    ((ImageView)view).setImageResource(defaultImage);
                }
            }
        };
        asyncTask.execute("");
    }

    static int HIGH_SIZE = 200000;
    private static Bitmap DoInBackgroundCallback(final String imageUri, Context context){
        try {
            URL url = null;
            try {
                url = new URL(imageUri);
            } catch (MalformedURLException e) {
                Logger.Error(TAG, "exception:" + e.getMessage());
            }

            //Proceso sin certificado SSL
            URL aURL= new URL(imageUri);

            URLConnection conn = aURL.openConnection();
            conn.connect();

            int file_size = conn.getContentLength();
            if(file_size > HIGH_SIZE){
                InputStream is = conn.getInputStream();
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, o2);
                is.close();

                return bitmap;
            }else{
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            }

        } catch (MalformedURLException e) {
            Logger.Error(TAG, "MalformedURLException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Logger.Error(TAG, "IOException: " + e.getMessage() + ", imageUri: " + imageUri);
            e.printStackTrace();
        }catch (OutOfMemoryError e){
            Logger.Error(TAG, "OutOfMemoryError: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

}
