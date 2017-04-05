package training.com.remoteservice.base.mvp;

/**
 * Created by ThoNH on 05/04/2017.
 */

public abstract class BasePresenterImpl<View extends BaseView> implements BasePresenter {

    public View mView ;


    @Override
    public void onSubcribeView(BaseView view) {
        this.mView = (View) view;
    }
}
