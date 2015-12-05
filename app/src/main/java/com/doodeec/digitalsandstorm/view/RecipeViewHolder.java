package com.doodeec.digitalsandstorm.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doodeec.digitalsandstorm.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ViewHolder for single recipe in list of recipes
 *
 * @author Dusan Bartos
 */
public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.view_recipe_title)
    TextView mTitle;
    @Bind(R.id.view_recipe_image)
    ImageView mImage;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * Loads image asynchronously with Picasso library to specified image view
     *
     * @param imageSrc url of an image
     */
    public void setImage(String imageSrc) {
        Picasso.with(itemView.getContext())
                .load(imageSrc)
                .into(mImage);
    }
}
