package com.uyou.copenaccount.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public class BitmapUtils {

    /**
     * 加载小图
     *
     * @param path
     * @return
     */
    public static Bitmap loadBitmapFromFile(String path) {
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int inSampleSize = 1;

        int width = options.outWidth;
        int height = options.outHeight;
        int reqWidth = 600;
        int reqHeight = 600;

        if (width > reqWidth || height > reqHeight) {
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = widthRatio < heightRatio ? widthRatio : heightRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    public static Bitmap rotateBitmap(Bitmap source, int angle) {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
        source.recycle();
        return bitmap;
    }

    public static Bitmap scaleBitmap(Bitmap source, int newWidth, int newHeight) {
        int width = source.getWidth();
        int height = source.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(source, 0, 0, width, height, matrix, true);
        return newBitmap;
    }

    /**
     * 把图片文件转为base64
     *
     * @param path 文件路径
     * @return
     */
    public static String loadBase64FromFile(String path) {
        String bb = null;
        try {
            FileInputStream inputStream = new FileInputStream(path);

            byte[] buff = new byte[100];
            int rc = 0;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            bb = encode(in2b);
        } catch (Exception e) {

        }
        return bb;
    }

    /**
     * 把bytes转为base64
     *
     * @param bytes 文件路径
     * @return
     */
    public static String loadBase64FromByte(byte[] bytes) {
        String bb = null;
        try {
            bb = encode(bytes);
        } catch (Exception e) {

        }
        return bb;
    }

    private static final char[] SIXTY_FOUR_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    private static String encode(byte[] input) {
        StringBuffer result = new StringBuffer();
        int outputCharCount = 0;

        for (int i = 0; i < input.length; i += 3) {
            int remaining = Math.min(3, input.length - i);
            int oneBigNumber = (input[i] & 255) << 16 | (remaining <= 1 ? 0 : input[i + 1] & 255) << 8 | (remaining <= 2 ? 0 : input[i + 2] & 255);

            for (int j = 0; j < 4; ++j) {
                result.append(remaining + 1 > j ? SIXTY_FOUR_CHARS[63 & oneBigNumber >> 6 * (3 - j)] : '=');
            }

            outputCharCount += 4;
            if (outputCharCount % 76 == 0) {
                result.append('\n');
            }
        }

        return result.toString();
    }


    /**
     * 通过URI获取文件
     */
    public static String getFileWithUri(Context context, Uri uri) {
        String picturePath = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex >= 0) {
                picturePath = cursor.getString(columnIndex);  //获取照片路径
            } else if (TextUtils.equals(uri.getAuthority(), getFileProviderName(context))) {
                picturePath = parseOwnUri(context, uri);
            }
            cursor.close();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            picturePath = uri.getPath();
        }
        return picturePath;
    }

    public static Bitmap getBitmapFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);

        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;


        logger("bitmapWidth : " + width + " , bitmapHeight : " + height);

        float scale = 1000;
        if (width > scale || height > scale) {
            int widthRatio = Math.round((float) width / scale);
            int heightRatio = Math.round((float) height / scale);
            inSampleSize = widthRatio < heightRatio ? widthRatio : heightRatio;
        }
        logger("inSampleSize : " + inSampleSize);

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        // 检测图片方向
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationDegrees = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationDegrees = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationDegrees = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationDegrees = 270;
                    break;
            }
            logger("rotationDegrees : " + rotationDegrees);
            if (rotationDegrees != 0) {
                bitmap = BitmapUtils.rotateBitmap(bitmap, rotationDegrees);
            }

            // 图片如果过大, 再进一步压缩
            height = bitmap.getHeight();
            width = bitmap.getWidth();
            if (height > width) {
                if (width > 1100) {
                    bitmap = scaleBitmap(bitmap, 1000, height * 1000 / width);
                }
            } else {
                if (height > 1100) {
                    bitmap = scaleBitmap(bitmap, width * 1000 / height, 1000);
                }
            }

            height = bitmap.getHeight();
            width = bitmap.getWidth();
            logger("height : " + height + " , width : " + width);
        } catch (Exception e) {
            logger("获取图片方向失败: " + e.getMessage());
        }
        return bitmap;
    }

    /**
     * 将Uri 解析出文件绝对路径
     */
    public static String parseOwnUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path;
        if (TextUtils.equals(uri.getAuthority(), getFileProviderName(context))) {
            path = new File(uri.getPath().replace("camera_photos/", "")).getAbsolutePath();
        } else {
            path = uri.getPath();
        }
        return path;
    }

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }

    private static void logger(String msg) {
        if (msg != null) {
            ULogger.e("BitmapUtils", msg);
        }
    }

}
