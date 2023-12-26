package com.example.deepart.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtils {
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    public static int[] getResizedResolution(int height, int width, int lowBoundValue) {
        int resizeHeight, resizeWidth;
        float ratio;
        if (height < width) {
            ratio = (float) height / (float) lowBoundValue;
            resizeHeight = lowBoundValue;
            resizeWidth = (int) ((float)width / ratio);
        } else {
            ratio = (float) width / (float) lowBoundValue;
            resizeWidth = lowBoundValue;
            resizeHeight = (int) ((float)height / ratio);
        }
        return new int[]{resizeHeight, resizeWidth};
    }

    public static float[] bitmapToRGBArray(Bitmap bitmap, int height, int width) {
        int[] pix = new int[width * height];
        float[] pixRGB = new float[width * height * 3];
        bitmap.getPixels(pix, 0, width, 0, 0, width, height);

        int green_offset = width * height;
        int blue_offset = green_offset + width * height;
        int r, g, b, index;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                r = (pix[index] >> 16) & 0xff;
                g = (pix[index] >> 8) & 0xff;
                b = pix[index] & 0xff;
                pixRGB[index] = (float) r / 255;
                pixRGB[index + green_offset] = (float) g / 255;
                pixRGB[index + blue_offset] = (float) b / 255;
            }
        }
        return pixRGB;
    }

    public static int getBoundedValue(float value, int lowBound, int highBound) {
        int valueInt = (int) (value * highBound);
        if (valueInt > highBound) valueInt = highBound;
        else if (valueInt < lowBound) valueInt = lowBound;
        return valueInt;
    }

    public static Bitmap NCHWArraytoBitmap(float[] data, int height, int width) {
        int[] pix = new int[height * width];

        int green_offset = width * height;
        int blue_offset = green_offset + width * height;
        int r, g, b, index;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                r = getBoundedValue(data[index], 0, 255);
                g = getBoundedValue(data[index + green_offset], 0, 255);
                b = getBoundedValue(data[index + blue_offset], 0, 255);
                pix[index] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
        final Bitmap bitmap = Bitmap.createBitmap(pix, width, height, Bitmap.Config.ARGB_8888);
        final Matrix transform = new Matrix();
        transform.setRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, transform, false);
    }

    public static File createImageFile() throws IOException {
        final String root =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Deep_Art";
        final File myDir = new File(root);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "IMG_" + timeStamp + ".png";
        return new File(myDir, fname);
    }

    public static Matrix getRotateMatrix(String currentImagePath) {
        try {
            ExifInterface ei = new ExifInterface(currentImagePath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Matrix matrix = new Matrix();
            int rotate_angle;
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotate_angle = 0;
            }
            matrix.postRotate(rotate_angle);
            return matrix;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveBitmap(final Bitmap bitmap) {
        try {
            final File file = createImageFile();
            if (file.exists()) {
                file.delete();
            }
      //      Environment.getExternalStorageDirectory().getAbsolutePath() + "어플명"
            final FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 99, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
          //  return Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Pictures/Download/";
        } catch (final Exception e) {
            return null;
        }
    }

    public static Bitmap cropBitmap(final Bitmap bitmap) {
        int imageHeight = bitmap.getHeight();
        int imageWidth = bitmap.getWidth();

        int shorterSide = imageWidth < imageHeight ? imageWidth : imageHeight;
        int longerSide = imageWidth < imageHeight ? imageHeight : imageWidth;
        boolean portrait = imageWidth < imageHeight;

        int lengthToCrop = (longerSide - shorterSide) / 2;

        int[] pixels = new int[shorterSide * shorterSide];

        bitmap.getPixels(pixels, 0, shorterSide, portrait ? 0 : lengthToCrop, portrait ? lengthToCrop : 0, shorterSide, shorterSide);
        bitmap.recycle();

        Bitmap croppedBitmap = Bitmap.createBitmap(shorterSide, shorterSide, Bitmap.Config.ARGB_8888);
        croppedBitmap.setPixels(pixels, 0, shorterSide, 0, 0, shorterSide, shorterSide);
        return croppedBitmap;
    }
}
