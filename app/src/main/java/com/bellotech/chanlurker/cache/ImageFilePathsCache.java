package com.bellotech.chanlurker.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache of file paths to downloaded images
 */
public class ImageFilePathsCache {
    private static List<String> imageFilePaths = new ArrayList<String>();

    public static synchronized boolean hasImageFilePath(String imageFilePath){
        return imageFilePaths.contains(imageFilePath);
    }

    public static synchronized void addImageFilePath(String imageFilePath){
        imageFilePaths.add(imageFilePath);
    }

    public static synchronized void purgeCache(){
        imageFilePaths.clear();
    }
}
