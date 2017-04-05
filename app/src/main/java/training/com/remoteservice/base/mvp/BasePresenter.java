package training.com.remoteservice.base.mvp;

/**
 * Created by ThoNH on 05/04/2017.
 */

public interface BasePresenter<View extends BaseView> {

    void onSubcribeView(View view);

}
