package training.com.tplayer.ui.online;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.adapter.online.OnlinePagerAdapter;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class OnlineActivity extends BaseActivity<OnlinePresenterImpl> {

    private final int REQUEST_PERMISSTION_CODE = 1011;


    @BindView(R.id.act_onl_tablayout)
    SmartTabLayout mTabLayout;

    @BindView(R.id.act_onl_viewpager)
    ViewPager mViewPager;

    private OnlinePagerAdapter mAdapter;


    @Override
    public int setLayoutId() {
        return R.layout.activity_online;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityCreated() {
        mAdapter = new OnlinePagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setViewPager(mViewPager);

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new OnlinePresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new OnlineInteractorImpl(this));

        requestPermisstion();
    }

    public void requestPermisstion() {

        // Kiểm tra xem đã có permission chưa
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Chưa có
            // Show một lời giải thích
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // tạo dialog hay gì đấy show 1 lời giải thích
                // cho người dùng cần cái permisstion này để làm gì
                // giải thích xong thì requestPermission

            } else {

                // k giải thích thì thôi
                // requestPermission luôn

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSTION_CODE);

            }


        } else {
            // Có rồi này
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSTION_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // đã cấp quyền
                    // todo somthing

                }else {

                    // người dùng ấn cancel rồi, ngu người

                }
                break;
        }

    }
}
