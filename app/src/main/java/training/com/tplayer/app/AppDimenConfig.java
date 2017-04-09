package training.com.tplayer.app;

import android.os.Build;
import android.util.DisplayMetrics;

import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 24/03/2017.
 */

public class AppDimenConfig {

    private static AppDimenConfig mInstance;

    /**
     * Mật độ màn hình
     * Số lượng pixel trong một inch . Còn được gọi là dpi
     */
    private float mDensity;

    /**
     * Mật độ màn hình tính theo dpi
     */
    private float mDensityDPI;

    /**
     * Tỉ lệ scale font chữ
     */
    private float mScaleDensity;

    /**
     * Chiều rông màn hình
     */
    private int mWidthScreen;

    /**
     * Chiều cao màn hình
     */
    private int mHeightScreen;


    public static synchronized AppDimenConfig getInstance() {
        if (mInstance == null) {
            mInstance = new AppDimenConfig();
        }
        return mInstance;
    }

    private AppDimenConfig() {
        DisplayMetrics dm = AppController.getInstance().getResources().getDisplayMetrics();

        mDensity = dm.density;
        mDensityDPI = dm.densityDpi;
        mScaleDensity = dm.scaledDensity;
        mWidthScreen = dm.widthPixels;
        mHeightScreen = dm.heightPixels;
        printInfoDevice();


    }

    public int getWidthScreen() {
        return mWidthScreen;
    }

    public int getHeightScreen() {
        return mHeightScreen;
    }

    public int convertSquareIcon(int sizeDp) {
        return Math.round(mDensity * sizeDp);
    }

    public int[] convertRectangle(int width, int height) {
        return new int[]{Math.round(width * mDensity), Math.round(height * mDensity)};
    }

    private void printInfoDevice() {
        String strDPI = "";
        LogUtils.printLogElement("Divice name : " + Build.MODEL);
        LogUtils.printLogElement("Device screen : " + mWidthScreen + " x " + mHeightScreen);

        if (100 < mDensityDPI && mDensityDPI < 140) {
            strDPI = ("ldpi (low) ~120dpi | x0.75");
        }

        if (140 < mDensityDPI && mDensityDPI < 180) {
            strDPI = ("mdpi (medium) ~160dpi | x1");
        }

        if (220 < mDensityDPI && mDensityDPI < 260) {
            strDPI = ("hdpi (high) ~240dpi | x1.5");
        }

        if (300 < mDensityDPI && mDensityDPI < 340) {
            strDPI = ("xhdpi (extra-high) ~320dpi | x2");
        }

        if (460 < mDensityDPI && mDensityDPI < 500) {
            strDPI = ("xxhdpi (extra-extra-high) ~480dpi | x3");
        }

        if (620 < mDensityDPI && mDensityDPI < 660) {
            strDPI = ("xxxhdpi (extra-extra-extra-high) ~640dpi | x4");
        }

        LogUtils.printLogElement(strDPI + " Real : " + mDensityDPI);

        LogUtils.printLogElement("Density : " + mDensity);

        LogUtils.printLogElement("Scale density : " + mScaleDensity);
    }

}
