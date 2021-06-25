package org.chuck.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class ImageCacheImpl implements ImageCache{
	private static LruCache<String, Bitmap> bitmapCache;
	private static ImageCacheImpl imageCacheImpl;
	
	public ImageCacheImpl() {
		super();
		int maxSize = 10 * 1024 * 1024;
		bitmapCache=new LruCache<String, Bitmap>(maxSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
	}
	
	public static ImageCacheImpl getInstance(){
		if(imageCacheImpl==null){
			imageCacheImpl=new ImageCacheImpl();
		}
		return imageCacheImpl;
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		return bitmapCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		bitmapCache.put(url, bitmap);			
	}
	
	public void removeBitMap(String url){
		bitmapCache.remove(url);
	}
	
	public void clearCache(){
		bitmapCache.evictAll();
	}
}
