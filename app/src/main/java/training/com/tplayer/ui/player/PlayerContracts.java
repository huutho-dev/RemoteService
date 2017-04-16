package training.com.tplayer.ui.player;

import training.com.tplayer.base.mvp.BaseInteractor;
import training.com.tplayer.base.mvp.BasePresenter;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerContracts {
    public interface View extends BaseView {



    }

    public interface Presenter extends BasePresenter {

        void setPlayLists();

        void playPause();

        void forward();

        void backward();

    }

    public interface Interactor extends BaseInteractor {

    }
}
