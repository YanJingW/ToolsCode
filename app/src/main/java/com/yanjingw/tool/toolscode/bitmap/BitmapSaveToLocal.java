package com.yanjingw.tool.toolscode.bitmap;


import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

public class BitmapSaveToLocal {

    /**
     * 将Bitmap写入SD卡中的一个文件中
     *
     * @param bitmap
     * @param localFile
     * @return
     */
    public static boolean saveBitmapToDisk(Bitmap bitmap, File localFile) {
        if (!isBitmapAvailable(bitmap)) {
            return false;
        }

        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdirs();
        }

        if (localFile.exists()) {
            localFile.delete();
        }
        try {
            // TODO: 2018/1/25 待查
//            Matrix matrix = new Matrix();
//            matrix.postScale(2, 2);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //打开文件输出流
            FileOutputStream out = new FileOutputStream(localFile);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //刷新输出流
            out.flush();
            //关闭输出流
            out.close();
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            //出错了就删除
            localFile.delete();
            return false;
        }
    }


    /**
     * 判断bitmap是否可用 不能为空 不能是已经被回收的 isRecycled返回false
     *
     * @param bitmap
     * @return
     */
    public static boolean isBitmapAvailable(Bitmap bitmap) {
        return !(null == bitmap || "".equals(bitmap) || bitmap.isRecycled());
    }


}
