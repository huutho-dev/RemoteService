package training.com.tplayer.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.remote.communication.IMyAidlInterface;

import training.com.tplayer.app.Config;
import training.com.tplayer.base.mvp.BasePresenterImpl;
import training.com.tplayer.base.mvp.BaseView;
import training.com.tplayer.ui.player.PlayerPresenterImpl;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 05/04/2017.
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

    /**
     * Service
     */
    private IMyAidlInterface mTPlayerService;

    /**
     * check service binded
     */
    private boolean isBinded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewRoot = LayoutInflater.from(this).inflate(setLayoutId(), null);
        setContentView(mViewRoot);
        onBindView();
        getDataBundle(savedInstanceState);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        // create presenterImpl here
        createPresenterImpl();

        LogUtils.printLogDetail("onResume");
    }

    protected abstract void createPresenterImpl();

    @Override
    protected void onPause() {
        super.onPause();
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
        if (isBinded && mTPlayerService != null) {
            unbindTplayerService();
        }
    }


    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTPlayerService = IMyAidlInterface.Stub.asInterface(service);
            isBinded = true;
            LogUtils.printLog("onServiceConnected");

            if (mPresenter instanceof PlayerPresenterImpl){
                ((PlayerPresenterImpl) mPresenter).setService(mTPlayerService);
                serviceConnected();
            }
        }


        @Override
        public  void onServiceDisconnected(ComponentName name) {
            mTPlayerService = null;
            isBinded = false;
            LogUtils.printLog("onServiceDisconnected");
        }
    };

    public void bindTPlayerService() {
        Intent intent = new Intent();
        intent.setClassName(Config.REMOTE_PACKAGE, Config.REMOTE_CLASS_SERVICE);
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public void startTPlayerService() {
        Intent intent = new Intent();
        intent.setClassName(Config.REMOTE_PACKAGE, Config.REMOTE_CLASS_ACTIVITY);
        startService(intent);
    }

    public IMyAidlInterface getPlayerService() {
        return mTPlayerService;
    }

    public void unbindTplayerService() {
        unbindService(mServiceConn);
    }

    public void getDataBundle(Bundle savedInstanceState) {

    }

    public void serviceConnected(){

    }

}
