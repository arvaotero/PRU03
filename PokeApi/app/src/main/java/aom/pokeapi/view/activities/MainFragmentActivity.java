package aom.pokeapi.view.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import aom.pokeapi.R;
import aom.pokeapi.constants.AppConstants;
import aom.pokeapi.util.fonts.FontCache;
import aom.pokeapi.view.activities.base.BaseAppCompatActivity;
import aom.pokeapi.view.fragments.PokemonInfoFragment;
import aom.pokeapi.view.listener.ActivityTopBarListener;

public class MainFragmentActivity extends BaseAppCompatActivity implements ActivityTopBarListener {

    private static final String TAG = MainFragmentActivity.class.getSimpleName();

    // Parameter with the type of fragment to show
    private String fragmentType;

    // ToolBar and ActionBar
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView toolbarTitle;

    private LinearLayout bgMain;

    // Reference to current fragment
    private Fragment mFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        initComponents();

        // Factory pattern to show one fragment or another
        fragmentType = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_FRAGMENT_TYPE);
        if (fragmentType != null && !fragmentType.isEmpty()) {
            mFragment = null;

            //********************************
            // POKEMON
            //********************************
            if (fragmentType.equals(AppConstants.FRAGMENT_POKEMON_INFO)) {
                String url = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_URL);
                String name = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_NAME);

                mFragment = PokemonInfoFragment.newInstance(url, name);
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_activity_content_frame, mFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void initComponents() {
        // Init toolbar and navigation drawer
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbarTitle = (TextView) findViewById(R.id.fragment_title_toolbar);
        }


        bgMain = (LinearLayout) findViewById(R.id.bg_main);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!isLocked) {
            finish();
        }
    }

    @Override
    public void updateTopBarTitle(String title) {
        toolbarTitle.setText(title);
        toolbarTitle.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_BOLD, this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
