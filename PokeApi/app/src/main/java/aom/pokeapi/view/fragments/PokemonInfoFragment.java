package aom.pokeapi.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import aom.pokeapi.R;
import aom.pokeapi.constants.AppConstants;
import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.model.Ability;
import aom.pokeapi.model.Move;
import aom.pokeapi.model.Pokemon;
import aom.pokeapi.model.Type;
import aom.pokeapi.model.rest.ObjectResponse;
import aom.pokeapi.service.rest.RestManagerPokemon;
import aom.pokeapi.util.ImageLoader;
import aom.pokeapi.util.Logger;
import aom.pokeapi.util.Utils;
import aom.pokeapi.util.asynctask.CGAsyncTask;
import aom.pokeapi.util.asynctask.CGIAsynTaskDoInBackgroundExecute;
import aom.pokeapi.util.asynctask.CGIAsynTaskPostExecute;
import aom.pokeapi.util.asynctask.CGIAsynTaskPreExecute;
import aom.pokeapi.util.asynctask.TaskHelper;
import aom.pokeapi.util.fonts.FontCache;
import aom.pokeapi.view.activities.MainActivity;
import aom.pokeapi.view.fragments.base.BaseFragment;
import aom.pokeapi.view.listener.ActivityTopBarListener;

public class PokemonInfoFragment extends BaseFragment {

    private final static String TAG = MainActivity.class.getSimpleName();

    private static final String PARAMETER_URL = "urlParameter";
    private static final String PARAMETER_NAME = "nameParameter";
    private static final String PARAMETER_ID = "idParameter";

    /**********************************************
     * FIELDS VIEW
     **********************************************/
    // UI Elements
    private TextView tvName, tvHeight, tvWeight;
    private TextView tvHeightUnit, tvWeightUnit;
    private TextView lblAbilities, lblMoves, lblTypes;
    private TextView tvAbilities, tvMoves, tvTypes;
    private LinearLayout lyAbilities, lyMoves, lyTypes;
    private ImageView ivImage, ivImageBack;


    /**********************************************
     * FIELDS
     **********************************************/
    private Pokemon mPokemon;
    private String mPokemonURL;
    private String mPokemonName;

    // Listener to communicate with Activity
    protected ActivityTopBarListener mListener;

    public PokemonInfoFragment() {
        // Required empty public constructor
    }

    public static PokemonInfoFragment newInstance(String url, String name) {
        PokemonInfoFragment fragment = new PokemonInfoFragment();
        Bundle args = new Bundle();
        args.putString(PARAMETER_URL, url);
        args.putString(PARAMETER_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_info, container, false);

        mPokemonURL = getArguments().getString(PARAMETER_URL);
        mPokemonName = getArguments().getString(PARAMETER_NAME);

        initView(view);
        return view;
    }

    @Override
    public void initComponents(View view) {
        super.initComponents(view);

        ivImage = (ImageView) view.findViewById(R.id.iv_image);
        ivImageBack = (ImageView) view.findViewById(R.id.iv_image_back);

        tvName = (TextView) view.findViewById(R.id.tv_name);

        tvHeight = (TextView) view.findViewById(R.id.tv_height);
        tvWeight = (TextView) view.findViewById(R.id.tv_weight);

        tvHeightUnit = (TextView) view.findViewById(R.id.tv_height_unit);
        tvWeightUnit = (TextView) view.findViewById(R.id.tv_weight_unit);

        lblAbilities = (TextView) view.findViewById(R.id.lbl_abilities);
        lblMoves = (TextView) view.findViewById(R.id.lbl_moves);
        lblTypes = (TextView) view.findViewById(R.id.lbl_types);

        tvAbilities = (TextView) view.findViewById(R.id.tv_abilities);
        tvMoves = (TextView) view.findViewById(R.id.tv_moves);
        tvTypes = (TextView) view.findViewById(R.id.tv_types);

        lyAbilities = (LinearLayout) view.findViewById(R.id.ly_abilities);
        lyMoves = (LinearLayout) view.findViewById(R.id.ly_moves);
        lyTypes = (LinearLayout) view.findViewById(R.id.ly_types);

    }


    @Override
    public void initFonts() {
        super.initFonts();
        // Init fonts
        tvName.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_BOLD, getContext()));
        tvWeight.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT, getContext()));
        tvHeight.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT, getContext()));
        tvWeightUnit.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_LIGHT, getContext()));
        tvHeightUnit.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_LIGHT, getContext()));

        lblAbilities.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_BOLD, getContext()));
        lblMoves.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_BOLD, getContext()));
        lblTypes.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_BOLD, getContext()));

        tvAbilities.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT, getContext()));
        tvMoves.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT, getContext()));
        tvTypes.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT, getContext()));

    }


    @Override
    public void onReloadUI() {
        super.onReloadUI();

        CallInfoAsyncTask();
        mListener.updateTopBarTitle(Utils.capitalizeFirstLetter(mPokemonName));
    }


    private void updateInfoUI(final Pokemon pokemon) {

        this.mPokemon = pokemon;

        tvName.setText(Utils.capitalizeFirstLetter(mPokemonName));
        tvWeight.setText(Utils.formatPrettyInt(mPokemon.getWeight()));
        tvHeight.setText(Utils.formatPrettyInt(mPokemon.getHeight()));


        if(pokemon.getAbilities().size() > 0){
            lyAbilities.setVisibility(View.VISIBLE);
            StringBuilder str = new StringBuilder();

            for(Ability ability : pokemon.getAbilities()){
                str.append("· ");
                str.append(Utils.capitalizeFirstLetter(ability.getName()));
                str.append("<br/>");
            }

            tvAbilities.setText(Utils.html(str.toString()), TextView.BufferType.SPANNABLE);
        }

        if(pokemon.getAbilities().size() > 0){
            lyMoves.setVisibility(View.VISIBLE);
            StringBuilder str = new StringBuilder();

            for(Move move : pokemon.getMoves()){
                str.append("· ");
                str.append(Utils.capitalizeFirstLetter(move.getName()));
                str.append("<br/>");
            }

            tvMoves.setText(Utils.html(str.toString()), TextView.BufferType.SPANNABLE);
        }


        if(pokemon.getAbilities().size() > 0){
            lyTypes.setVisibility(View.VISIBLE);
            StringBuilder str = new StringBuilder();

            for(Type type : pokemon.getTypes()){
                str.append("· ");
                str.append(Utils.capitalizeFirstLetter(type.getName()));
                str.append("<br/>");
            }

            tvTypes.setText(Utils.html(str.toString()), TextView.BufferType.SPANNABLE);
        }



        ImageLoader.LoadAsyncImage(getActivity(), ivImage,
                String.format(getString(R.string.URL_REST_IMAGE), mPokemon.getId()),
                R.drawable.default_pokeball);

        ImageLoader.LoadAsyncImage(getActivity(), ivImageBack,
                String.format(getString(R.string.URL_REST_IMAGE_BACK), mPokemon.getId()),
                R.drawable.default_pokeball);
    }


    /*******************************************************************************************/
    /****************************************** TASKS ******************************************/
    /*******************************************************************************************/

    private void CallInfoAsyncTask() {

        CGIAsynTaskPreExecute preExecute = new CGIAsynTaskPreExecute() {
            @Override
            public void action() {
                mBaseActivity.lockView(true);
            }
        };

        CGIAsynTaskDoInBackgroundExecute doInBackgroundExecute = new CGIAsynTaskDoInBackgroundExecute() {
            @Override
            public ObjectResponse action() {
                try {
                    if(mPokemonURL != null && mPokemonURL.length() > 0){
                        return RestManagerPokemon.getItem(mPokemonURL);
                    }else if(mPokemonName != null && mPokemonName.length() > 0){
                        return RestManagerPokemon.getItemByName(getActivity(), mPokemonName);
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
                updateInfoUI((Pokemon) result.getObject());
            }

            @Override
            public void error(ObjectResponse result) {
                Logger.Debug(TAG, "" + result.getError());
            }

            @Override
            public void actionFinally() {
                mBaseActivity.lockView(false);
            }
        };

        CGAsyncTask task = new CGAsyncTask(TAG, mBaseActivity, preExecute, doInBackgroundExecute, postExecute);
        TaskHelper.execute(task);
    }


    /*******************************************************************************************/
    /****************************************** MENU TOP BAR ******************************************/
    /*******************************************************************************************/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityTopBarListener) {
            mListener = (ActivityTopBarListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ActivityTopBarListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
