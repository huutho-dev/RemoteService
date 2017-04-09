package training.com.tplayer.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ThoNH on 30/03/2017.
 */

public class ViewUtils {

    public static void toastShowShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShowLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void snakbarShowShort(View rootView, String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);
    }

    public static void snakbarShowLong(View rootView, String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);
    }

    public static AlertDialog.Builder dialogBasicMsg(Context context, String title, String msg) {
        return new AlertDialog
                .Builder(context)
                .setTitle(title)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage(msg);
    }


}
