package training.com.remoteservice.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import training.com.remoteservice.R;


/**
 * Created by ThoNH on 29/03/2017.
 */

public class TextViewRoboto extends AppCompatTextView {

    private String fontName = "Roboto-Regular.ttf";

    public TextViewRoboto(Context context) {
        super(context);
        initTextView(context, null);
    }

    public TextViewRoboto(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTextView(context, attrs);
    }

    public TextViewRoboto(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTextView(context, attrs);
    }

    private void initTextView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewRoboto);

        if (a.hasValue(R.styleable.TextViewRoboto_textFonts)) {
            int font = a.getInt(R.styleable.TextViewRoboto_textFonts, 3);
            switch (font) {
                case 1:
                    fontName = "Roboto-Thin.ttf";
                    break;
                case 2:
                    fontName = "Roboto-Light.ttf";
                    break;
                case 3:
                    fontName = "Roboto-Regular.ttf";
                    break;
                case 4:
                    fontName = "Roboto-Medium.ttf";
                    break;
                case 5:
                    fontName = "Roboto-Bold.ttf";
                    break;
                case 6:
                    fontName = "Roboto-Black.ttf";
                    break;
                case 7:
                    fontName = "Roboto-ThinItalic.ttf";
                    break;
                case 8:
                    fontName = "Roboto-LightItalic.ttf";
                    break;
                case 9:
                    fontName = "Roboto-Italic.ttf";
                    break;
                case 10:
                    fontName = "Roboto-MediumItalic.ttf";
                    break;
                case 11:
                    fontName = "Roboto-BoldItalic.ttf";
                    break;
                case 12:
                    fontName = "Roboto-BlackItalic.ttf";
                    break;
                case 13:
                    fontName = "RobotoCondensed-Bold.ttf";
                    break;
                case 14:
                    fontName = "RobotoCondensed-BoldItalic.ttf";
                    break;
                case 15:
                    fontName = "RobotoCondensed-Italic.ttf";
                    break;
                case 16:
                    fontName = "RobotoCondensed-Light.ttf";
                    break;
                case 17:
                    fontName = "RobotoCondensed-LightItalic.ttf";
                    break;
                case 18:
                    fontName = "RobotoCondensed-Regular.ttf";
                    break;
                default:
                    fontName = "RobotoCondensed-Light.ttf";
                    break;
            }
        }
        a.recycle();

        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
        setTypeface(face);
    }
}
