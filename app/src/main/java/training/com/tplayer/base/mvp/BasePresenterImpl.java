package training.com.tplayer.base.mvp;

/**
 * Created by ThoNH on 05/04/2017.
 */

public abstract class BasePresenterImpl<View extends BaseView, InteractorImpl extends BaseInteractorImpl>
        implements BasePresenter {

    public View mView;

    public InteractorImpl mInteractor;


    public abstract void onSubcireView(View view);

    public abstract void onSubcireInteractor(InteractorImpl interactor);
}
