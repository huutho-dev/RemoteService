package training.com.tplayer.ui.splash;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.main.MainActivity;

public class SplashActivity extends BaseActivity<SplashPresenterImpl> implements SplashContracts.View {

    @BindView(R.id.atc_splash_txt_app_name)
    TextViewRoboto mTxtAppName;

    private Animation animSplash ;

    @Override
    public int setLayoutId() {
        // make splash screen full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return R.layout.activity_splash;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        mTxtAppName.setText("TPlayer");
        animSplash = AnimationUtils.loadAnimation(this,R.anim.anim_splash);
        animSplash.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    mPresenter.loading();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onActivityCreated() {
        mTxtAppName.startAnimation(animSplash);
    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new SplashPresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new SplashInteractorImpl(this));
    }


    @Override
    public void delayComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
