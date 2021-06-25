package org.chuck.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;

import org.chuck.server.StreamTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 15-10-23.
 */
public class ImageUtil {
    private static int MAX_WIDTH=0;
    private static int MAX_HEIGHT=0;
    public static Bitmap scaleBitmap(File bmpFile, int desiredWidth, int desiredHeight){
        return scaleBitmap(bmpFile.getPath(),desiredWidth, desiredHeight,null);
    }
    public static Bitmap scaleBitmap(String bmpFilePath){
        return scaleBitmap(bmpFilePath,MAX_WIDTH, MAX_HEIGHT,null);
    }
    public static Bitmap scaleBitmap(String bmpFilePath, int desiredWidth, int desiredHeight, String saveFilePath){
        BitmapFactory.Options options = new BitmapFactory.Options();

        //不分配内存
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(bmpFilePath, options);

        //图片原始宽高
        int origWidth=options.outWidth;
        int origHeight=options.outHeight;


        float scale=getMinScale(origWidth, origHeight, desiredWidth, desiredHeight);

//		options.inSampleSize=Math.round((float)1/scale);
        int realityWidth=(int) (origWidth*scale);
        int realityHeight=(int) (origHeight*scale);

        //设置期望得到的图片的宽高
        options.outWidth = realityWidth;
        options.outHeight = realityHeight;

        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inDither = false;
        //分配内存
        options.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeFile(bmpFilePath, options);
        bitmap=getScaleBitmap(bitmap, scale);
        if(!CharSeqUtil.isEmpty(saveFilePath)){
            png2Jpeg(bitmap,saveFilePath);
            recycle(bitmap);
            bitmap= BitmapFactory.decodeFile(saveFilePath);
        }
        return bitmap;
    }


    public static Bitmap getScaleBitmap(Bitmap bitmap, float scale){
        if(scale==1){
            return bitmap;
        }

        Bitmap newBitmap=null;
        try{
            int width=bitmap.getWidth();
            int height=bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            recycle(bitmap);
        }
        return newBitmap;
    }

    public static float getMinScale(int origWidth,int origHeight,int desiredWidth, int desiredHeight){
        float widthScale=(float)desiredWidth/(float)origWidth;
        float heightScale=(float)desiredHeight/(float)origHeight;
        return widthScale<heightScale?widthScale:heightScale;
    }



    public static void png2Jpeg(Bitmap pngBmp, String jpgSaveFilePath){
        FileOutputStream fos = null;
        try {
            File jpgSaveFile=new File(jpgSaveFilePath);
            File saveFileDir=new File(jpgSaveFile.getParent());
            if(!saveFileDir.exists()){
                saveFileDir.mkdirs();
            }

            fos = new FileOutputStream(jpgSaveFile);
            pngBmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
//					fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void setBitmap(View view, Bitmap bitmap){
        int width=view.getWidth();
        int height=view.getHeight();

    }

    public static void recycle(Bitmap bitmap){
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
            bitmap=null;
        }
        System.gc();
    }
    //^^^^^^^^^^^^^^^^^^^^^^^
    public static Bitmap getImage(String address) throws Exception
    {
        //通过代码 模拟器浏览器访问图片的流程
        URL url = new URL(address);
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        //获取服务器返回回来的流
        InputStream is = conn.getInputStream();
        byte[] imagebytes = StreamTool.getBytes(is);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
        return bitmap;
    }

    public static Bitmap getHttpBitmap(String url)
    {
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }


    //^^^^^^^^^^^^^^^^^^^^^^^
}
