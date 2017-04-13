package training.com.tplayer.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.favorite.FavoriteActivity;
import training.com.tplayer.ui.offline.OfflineActivity;
import training.com.tplayer.ui.online.OnlineActivity;
import training.com.tplayer.ui.rate.RateActivity;
import training.com.tplayer.ui.setting.SettingActivity;
import training.com.tplayer.ui.share.ShareActivity;

import static training.com.tplayer.R.id.action_menu_offline;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements MainContracts.View, CircleMenu.OnItemClickListener {

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
                startActivity(new Intent(this,OnlineActivity.class));
                break;
            case action_menu_offline:
                startActivity(new Intent(this,OfflineActivity.class));
                break;
            case R.id.action_menu_favorite:
                startActivity(new Intent(this,FavoriteActivity.class));
                break;
            case R.id.action_menu_setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.action_menu_share:
                startActivity(new Intent(this,ShareActivity.class));
                break;
            case R.id.action_menu_rate:
                startActivity(new Intent(this,RateActivity.class));
                break;
        }
    }

}
