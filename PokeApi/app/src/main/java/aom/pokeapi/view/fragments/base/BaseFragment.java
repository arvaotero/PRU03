package aom.pokeapi.view.fragments.base;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import aom.pokeapi.view.activities.base.BaseAppCompatActivity;

public class BaseFragment extends Fragment {
    protected BaseAppCompatActivity mBaseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseAppCompatActivity) {
            mBaseActivity = (BaseAppCompatActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must be attached to BaseAppCompatActivity");
        }
    }

    public void initComponents(View view){

    }
    public void initFonts(){

    }
    public void initListeners(){

    }
    public void onReloadUI(){
    }

    public void initView(View view){

        initComponents(view);
        initFonts();
        initListeners();

        onReloadUI();
    }
}
