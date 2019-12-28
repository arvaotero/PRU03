package aom.pokeapi.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import aom.pokeapi.R;
import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.model.Pokemon;
import aom.pokeapi.model.rest.ObjectListPokemon;
import aom.pokeapi.model.rest.ObjectResponse;
import aom.pokeapi.service.rest.RestManagerPokemon;
import aom.pokeapi.util.Logger;
import aom.pokeapi.util.asynctask.CGAsyncTask;
import aom.pokeapi.util.asynctask.CGIAsynTaskDoInBackgroundExecute;
import aom.pokeapi.util.asynctask.CGIAsynTaskPostExecute;
import aom.pokeapi.util.asynctask.CGIAsynTaskPreExecute;
import aom.pokeapi.util.asynctask.TaskHelper;
import aom.pokeapi.view.activities.base.BaseAppCompatActivity;
import aom.pokeapi.view.adapter.PokemonRecyclerAdapter;

public class MainActivity extends BaseAppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    /**********************************************
     * FIELDS VIEW
     **********************************************/

    private PokemonRecyclerAdapter mAdapter;
    private List<Pokemon> mListItems;
    private String mNextUrl = "";

    /**********************************************
     * FIELDS VIEWS
     **********************************************/
    private RecyclerView recyclerView;

    private LinearLayout lyContent;
    private LinearLayout lyEmpty;


    /**********************************************
     * MENU
     **********************************************/
    private SearchView searchView;
    private ImageView ivTopBar;

    // ToolBar and ActionBar
    private ActionBar actionBar;
    private Toolbar toolbar;

    private Menu mMenu;
    private MenuItem mCloseMenuItem;
    private MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init toolbar and navigation drawer
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
        }

        recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);

        ivTopBar = (ImageView) findViewById(R.id.img_logo_toolbar);
        searchView = (SearchView) findViewById(R.id.search_view);

        lyContent = (LinearLayout) findViewById(R.id.ly_content);
        lyEmpty = (LinearLayout) findViewById(R.id.ly_empty);

    }

    @Override
    protected void onStart() {
        super.onStart();

        initItemsUI();
        initListener();
    }


    /*******************************************************************************************/
    /****************************************** LISTENER ******************************************/
    /*******************************************************************************************/
    void initListener(){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                CallSearchAsyncTask(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                return false;
            }
        });
    }

    /*******************************************************************************************/
    /*********************************** NAVIGATION METHODS ************************************/
    /*******************************************************************************************/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mMenu = menu;

        mCloseMenuItem = menu.findItem(R.id.action_close);
        mCloseMenuItem.setVisible(false);

        // Save Share menu item
        mSearchMenuItem = menu.findItem(R.id.action_search);
        mSearchMenuItem.setVisible(true);

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            onCloseSearch(true);
            return true;
        } else if (id == R.id.action_search) {
            onOpenSerch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onOpenSerch(){
        mCloseMenuItem.setVisible(true);
        mSearchMenuItem.setVisible(false);
        ivTopBar.setVisibility(View.GONE);
        searchView.setVisibility(View.VISIBLE);
    }

    public void onCloseSearch(boolean reset){
        mCloseMenuItem.setVisible(false);
        mSearchMenuItem.setVisible(true);
        ivTopBar.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);

        if(reset) initItemsUI();
    }

    /*******************************************************************************************/
    /****************************************** LIST ******************************************/
    /*******************************************************************************************/

    private void initItemsUI() {

        mNextUrl = "";
        mListItems = new ArrayList<>();

        if (recyclerView != null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }

        // RecyclerView
        mAdapter = new PokemonRecyclerAdapter(this, mListItems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !isRequesting) {
                    CallListAsyncTask();
                }
            }
        });

        CallListAsyncTask();
    }

    private void updateListItemsUI(ObjectListPokemon items) {

        if (items != null && !items.getResults().isEmpty()) {

            for(Pokemon item : items.getResults()){
                Logger.Debug(TAG, "Pokemon: " + item.getName() + ", " + item.getId());
                if(!mListItems.contains(item)) mListItems.add(item);
            }

            mNextUrl = items.getNext();

            mAdapter.setItems(mListItems);
            mAdapter.notifyDataSetChanged();
        }

        if(mAdapter.getItemCount() > 0){
            lyContent.setVisibility(View.VISIBLE);
            lyEmpty.setVisibility(View.GONE);
        }else{
            lyContent.setVisibility(View.GONE);
            lyEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updatePokemonUI(Pokemon pokemon) {

        if(pokemon != null){

            mListItems = new ArrayList<>();
            mListItems.add(pokemon);

            mAdapter.setItems(mListItems);
            mAdapter.notifyDataSetChanged();

            lyContent.setVisibility(View.VISIBLE);
            lyEmpty.setVisibility(View.GONE);

        }else{
            lyContent.setVisibility(View.GONE);
            lyEmpty.setVisibility(View.VISIBLE);
        }
    }





    /*******************************************************************************************/
    /****************************************** TASKS ******************************************/
    /*******************************************************************************************/
    protected boolean isRequesting;
    private void CallListAsyncTask(){

        if(isRequesting){
            return;
        }

        CGIAsynTaskPreExecute preExecute = new CGIAsynTaskPreExecute() {
            @Override
            public void action() {
                lockView(true);
                isRequesting = true;
            }
        };

        CGIAsynTaskDoInBackgroundExecute doInBackgroundExecute = new CGIAsynTaskDoInBackgroundExecute() {
            @Override
            public ObjectResponse action() {
                try {
                    if(mNextUrl.length() > 0){
                        return RestManagerPokemon.getList(mNextUrl);
                    }else{
                        return RestManagerPokemon.getList(MainActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ObjectResponse(AppRestConstants.ERROR_SERVER, getString(R.string.ws_error_server));
            }
        };

        CGIAsynTaskPostExecute postExecute = new CGIAsynTaskPostExecute() {
            @Override
            public void success(ObjectResponse result) {
                updateListItemsUI((ObjectListPokemon) result.getObject());
            }

            @Override
            public void error(ObjectResponse result) {
                Logger.Debug(TAG, "Error " + result.getError());
            }

            @Override
            public void actionFinally() {
                lockView(false);
                isRequesting = false;
            }
        };

        CGAsyncTask task = new CGAsyncTask(TAG, this, preExecute, doInBackgroundExecute, postExecute);
        TaskHelper.execute(task);
    }


    private void CallSearchAsyncTask(final String name){

        if(isRequesting){
            return;
        }

        if(name == null || name.length() == 0){
            return;
        }

        onCloseSearch(false);

        CGIAsynTaskPreExecute preExecute = new CGIAsynTaskPreExecute() {
            @Override
            public void action() {
                lockView(true);
                isRequesting = true;
            }
        };

        CGIAsynTaskDoInBackgroundExecute doInBackgroundExecute = new CGIAsynTaskDoInBackgroundExecute() {
            @Override
            public ObjectResponse action() {
                try {
                    return RestManagerPokemon.getItemByName(getApplicationContext(), name.toLowerCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ObjectResponse(AppRestConstants.ERROR_SERVER, getString(R.string.ws_error_server));
            }
        };

        CGIAsynTaskPostExecute postExecute = new CGIAsynTaskPostExecute() {
            @Override
            public void success(ObjectResponse result) {
                updatePokemonUI((Pokemon) result.getObject());
            }

            @Override
            public void error(ObjectResponse result) {
                Logger.Debug(TAG, "Error " + result.getError());
                updatePokemonUI(null);
            }

            @Override
            public void actionFinally() {
                lockView(false);
                isRequesting = false;
            }
        };

        CGAsyncTask task = new CGAsyncTask(TAG, this, preExecute, doInBackgroundExecute, postExecute);
        TaskHelper.execute(task);
    }

}
