package training.com.tplayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by hnc on 13/04/2017.
 */

public class ImageUtils {
    public static void loadImageBasic(Context context, int idResource, ImageView imageView) {
        Picasso.with(context).load(idResource).into(imageView);
    }

    public static void loadImageBasic(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url)
                .transform(new CircleTransform())
                .into(imageView);
    }

    public static void loadImagePlayList(Context context,String url, ImageView imageView) {
        Picasso.with(context).load(url)
                .transform(new CircleTransform())
                .into(imageView);
    }

    public static void loadRoundImage(Context context,int resId, ImageView imageView){
        Picasso.with(context).load(resId)
                .transform(new CircleTransform())
                .into(imageView);
    }

    public static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
