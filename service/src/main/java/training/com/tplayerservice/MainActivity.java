
package training.com.tplayerservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import training.com.tplayerservice.app.Constants;

/**
 * Created by HuuTho on 4/8/2017.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this,TPlayerService.class);
        intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(intent);



        finish();
    }
}
