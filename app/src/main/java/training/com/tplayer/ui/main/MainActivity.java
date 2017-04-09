package training.com.tplayer.ui.main;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements MainContracts.View, CircleMenu.OnItemClickListener{

    @BindView(R.id.circleMenu)
    CircleMenu mCircleMenu;


    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        mCircleMenu.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new MainPresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new MainInteractorImpl(this));
    }

    @Override
    public void onItemClick(CircleMenuButton menuButton) {
        switch (menuButton.getId()) {
            case R.id.action_menu_online:
                break;
            case R.id.action_menu_offline:
                break;
            case R.id.action_menu_favorite:
                break;
            case R.id.action_menu_setting:
                break;
            case R.id.action_menu_share:
                break;
            case R.id.action_menu_rate:
                break;
        }
    }
}
