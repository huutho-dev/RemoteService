package training.com.remoteservice.base.mvp;

/**
 * Created by hnc on 05/04/2017.
 */

public interface BaseView {

    /**
     * subscribe presenter here   (call from onCreate)
     */
    void onSubscribe();


    /**
     * unsSubcribe presenter here  (call from onDestroy)
     */
    void onUnSubscribe();

}
