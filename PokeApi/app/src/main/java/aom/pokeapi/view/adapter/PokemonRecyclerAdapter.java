package aom.pokeapi.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aom.pokeapi.R;
import aom.pokeapi.constants.AppConstants;
import aom.pokeapi.model.Pokemon;
import aom.pokeapi.util.ImageLoader;
import aom.pokeapi.util.Utils;
import aom.pokeapi.util.fonts.FontCache;
import aom.pokeapi.view.activities.MainFragmentActivity;
import aom.pokeapi.view.fragments.PokemonInfoFragment;

public class PokemonRecyclerAdapter extends RecyclerView.Adapter<PokemonRecyclerAdapter.ItemHolder> {

    private static final String TAG = PokemonRecyclerAdapter.class.getSimpleName();

    private List<Pokemon> mItemList;
    private Context mContext;
    private Activity mAdapterListener;


    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Pokemon item;
        private TextView tvName;
        private ImageView ivImage;

        private Activity androidActivity;

        public ItemHolder(View v, Activity activity) {
            super(v);

            androidActivity = activity;

            tvName = (TextView) v.findViewById(R.id.tv_name);
            ivImage = (ImageView) v.findViewById(R.id.iv_image);

            initFonts();
            v.setOnClickListener(this);
        }

        private void initFonts() {
            // Init fonts
            tvName.setTypeface(FontCache.get(AppConstants.FONTS_DEFAULT_BOLD, androidActivity.getApplicationContext()));
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(androidActivity, MainFragmentActivity.class);
            intent.putExtra(AppConstants.INTENT_EXTRA_FRAGMENT_TYPE, AppConstants.FRAGMENT_POKEMON_INFO);
            intent.putExtra(AppConstants.INTENT_EXTRA_URL, item.getUrl());
            intent.putExtra(AppConstants.INTENT_EXTRA_NAME, item.getName());
            intent.putExtra(AppConstants.INTENT_EXTRA_ID, item.getId());
            androidActivity.startActivity(intent);
        }

        public void bindItem(Context ctx, Pokemon item) {

            this.item = item;
            tvName.setText(Utils.capitalizeFirstLetter(item.getName()));

            ImageLoader.LoadAsyncImage(androidActivity, ivImage,
                    String.format(ctx.getString(R.string.URL_REST_IMAGE), item.getId()),
                    R.drawable.default_pokeball);

        }
    }


    public PokemonRecyclerAdapter(Activity activity, List<Pokemon> activities) {
        mAdapterListener = activity;
        mItemList = activities;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);
        mContext = inflatedView.getContext();
        return new ItemHolder(inflatedView, mAdapterListener);
    }


    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Pokemon item = mItemList.get(position);
        holder.bindItem(mContext, item);
    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setItems(List<Pokemon> itemList) {
        mItemList = itemList;
    }
}
