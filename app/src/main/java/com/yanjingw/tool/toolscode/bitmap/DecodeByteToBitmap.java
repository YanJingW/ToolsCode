package com.yanjingw.tool.toolscode.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;

import java.io.ByteArrayOutputStream;

public class DecodeByteToBitmap {

    /**
     * 将Camera预览数据转为bitmap
     *
     * @param data   Camera预览数据
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeByteToBitmap(byte[] data, int width, int height) {
//        ByteBuffer buffer = ByteBuffer.wrap(data);
//        buffer.rewind();
//        bitmap_tmp.copyPixelsFromBuffer(buffer);

//        final Bitmap tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        tempBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(data).rewind().position(0));

        YuvImage yuv = new YuvImage(data, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);
        byte[] bytes = out.toByteArray();
        final Bitmap tempBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Bitmap bitmap = rotateBitmapByDegree(tempBitmap, 180, false, false);
        return bitmap;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree, boolean isMirror, boolean isRecycle) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        if (isMirror) {
            matrix.postScale(-1, 1);
        }
        matrix.postRotate(degree);

        try {
            if (bm != null) {
                // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
                returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (isRecycle) {
            if (bm != returnBm) {
                bm.recycle();
            }
        }

        return returnBm;
    }
}
