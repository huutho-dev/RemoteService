package training.com.tplayer.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import training.com.tplayer.base.mvp.BasePresenterImpl;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by hnc on 05/04/2017.
 */

public abstract class BaseFragment<PresenterImpl extends BasePresenterImpl> extends Fragment implements BaseView {

    public View mRootView;

    public Context mContext;

    public Activity mActivity;

    public PresenterImpl mPresenterImp;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBundle(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateViewFragment(inflater, container, savedInstanceState);
    }

    public View onCreateViewFragment(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        return mRootView;
    }

    public abstract int setLayoutId();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedFragment(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public abstract void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState);


    public void getDataBundle(@Nullable Bundle savedInstanceState) {

    }
}
