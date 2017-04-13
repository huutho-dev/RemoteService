package training.com.tplayer.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by hnc on 13/04/2017.
 */

public class ImageUtils {
    public static void loadImageBasic(Context context, int idResource, ImageView imageView) {
        Picasso.with(context).load(idResource).into(imageView);
    }
}
