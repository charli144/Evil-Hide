package me.sweetll.evilhide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.sweetll.evilhide.fragment.SettingFragment;

/**
 * Created by sweet on 3/7/16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.content) FrameLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }
}
