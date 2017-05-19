package training.com.tplayer.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import training.com.tplayer.base.mvp.BasePresenterImpl;
import training.com.tplayer.utils.FileUtils;

/**
 * Created by ThoNH on 5/14/2017.
 */

public class SharePresenterImpl extends BasePresenterImpl<ShareActivity,ShareInteratorImpl> implements ShareContract.Presenter {
    @Override
    public void onSubcireView(ShareActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(ShareInteratorImpl interactor) {
        this.mInteractor = interactor;
    }

    @Override
    public void shareApp(AppCompatActivity compatActivity) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Mời bạn tải ứng dụng Phượt tốt nhất hiện nay");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://playstore/?id=training.com.tplayer");

        compatActivity.startActivity(Intent.createChooser(shareIntent, "Mời chọn phương tiện chia sẻ =]]"));
    }

    @Override
    public void shareSocial(AppCompatActivity mActivity) {
//        ShareDialog shareDialog = new ShareDialog(mActivity);
//        Bitmap image = ...
//        SharePhoto photo = new SharePhoto.Builder()
//                .setBitmap(image)
//                .build();
//        SharePhotoContent content = new SharePhotoContent.Builder()
//                .addPhoto(photo)
//                .build();
//        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

        Intent intent = new Intent();
        Uri uri = Uri.parse(FileUtils.PATH_SCREEN_SHOOT);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(uri, "image/*");

        mActivity.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), 1);

    }
}
