package training.com.remoteservice.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import training.com.remoteservice.base.mvp.BasePresenterImpl;
import training.com.remoteservice.base.mvp.BaseView;
import training.com.remoteservice.utils.LogUtils;

/**
 * Created by hnc on 05/04/2017.
 */

public abstract class BaseActivity<PresenterImpl extends BasePresenterImpl> extends AppCompatActivity implements BaseView {

    /**
     * Use when should do work other thread not in UI Thread
     */
    public Thread mThread;

    /**
     * User when should cominicate between threads or handling some delay work
     */
    public Handler mHandler;

    /**
     * Root view of activity
     */
    public View mViewRoot;

    /**
     * Instance of sub PresenterImpl
     */
    public PresenterImpl mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewRoot = LayoutInflater.from(this).inflate(setLayoutId(), null);
        setContentView(mViewRoot);
        onBindView();
        onActivityCreated();

        mThread = new Thread();
        mHandler = new Handler();
        LogUtils.printLogDetail("onCreate");
    }

    // Set layout(xml)
    public abstract int setLayoutId();

    // findViewById
    public abstract void onBindView();

    // init List<>, Adapter, ...
    public abstract void onActivityCreated();


    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.printLogDetail("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // create presenterImpl here
        onSubscribe();
        mPresenter.onSubcribeView(this);
        LogUtils.printLogDetail("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        onUnSubscribe();
        LogUtils.printLogDetail("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.printLogDetail("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.printLogDetail("onDestroy");
    }


}
