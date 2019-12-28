package aom.pokeapi.model.singleton;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class ImageSingleton {

    /**********************************************
     * FIELDS
     **********************************************/
    /** Map of image download*/
    private Map<String, Bitmap> images;

    /**********************************************
     * CONSTRUCTOR
     **********************************************/

    /** Class instance */
    private static ImageSingleton instance = null;
    /**
     *  Creador de la clase segun el patron Singleton
     */
    public static ImageSingleton getInstance() {
        if (instance == null) {
            synchronized(ImageSingleton.class) {
                if (instance == null) {
                    instance = new ImageSingleton();
                }
            }
        }
        return instance;
    }

    public ImageSingleton(){
        images = new HashMap<>();
    }


    /**********************************************
     * GETTER AND SETTER
     **********************************************/
    public Map<String, Bitmap> getImages() {
        return images;
    }
}
