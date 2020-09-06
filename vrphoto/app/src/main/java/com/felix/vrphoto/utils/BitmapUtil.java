package com.felix.vrphoto.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;

import java.io.File;

public class BitmapUtil {

	public static Bitmap textAsString(String text, float textSize, int textColor, int height, int width){
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.SERIF);
		paint.setColor(textColor);
		
		paint.setTextAlign(Paint.Align.LEFT);
		
		int w = (int)(paint.measureText(text)+0.5f);
		float baseline = (int)(-paint.ascent()+0.5f);
		int h = (int) (baseline + paint.descent()+0.5f);
		
		Bitmap image = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(image);
		canvas.drawText(text, (width - w) / 2, baseline + (height - h) / 2, paint);

        Matrix m = new Matrix();
        m.preScale(-1, 1);

        Bitmap dst = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, false);
        image.recycle();

        return dst;
	}
	
	public static Bitmap textAsString(String text, float textSize, int textColor, int height, int width, Bitmap image){
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.SERIF);
		paint.setColor(textColor);
		
		paint.setTextAlign(Paint.Align.LEFT);
		
		int w = (int)(paint.measureText(text)+0.5f);
		float baseline = (int)(-paint.ascent()+0.5f);
		int h = (int) (baseline + paint.descent()+0.5f);
		
		Canvas canvas = new Canvas(image);
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		canvas.drawText(text,(width-w)/2, baseline+(height-h)/2, paint);

        Matrix m = new Matrix();
        m.preScale(-1, 1);

        Bitmap dst = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, false);
        image.recycle();

        return dst;
	}
	
	public static Bitmap getResizedBitmap(int targetW, int targetH, String imagePath) {

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    //inJustDecodeBounds = true <-- will not load the bitmap into memory
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imagePath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap image = BitmapFactory.decodeFile(imagePath, bmOptions);

		Matrix m = new Matrix();
		m.preScale(-1, 1);

		Bitmap dst = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, false);
		image.recycle();

	    return dst;
	}
	
	public static boolean isImage(File file) {
		if ((file == null) || !file.exists() || file.isDirectory()) {
			return false;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), options);
		return (options.outWidth != -1) && (options.outHeight != -1);
	}
}
