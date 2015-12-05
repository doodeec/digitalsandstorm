package com.doodeec.digitalsandstorm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.doodeec.digitalsandstorm.DigitalSandstormApp;
import com.doodeec.digitalsandstorm.R;
import com.doodeec.digitalsandstorm.model.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "recipe";
    private static final String TAG = "RecipeDetailActivity";

    @Bind(R.id.toolbar_image)
    ImageView mToolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra(EXTRA_RECIPE)) {
            Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(recipe.getTitle());
            }

            Picasso.with(this)
                    .load(recipe.getImageSrc(DigitalSandstormApp.isPortrait(this)))
                    .into(mToolbarImage);
        } else {
            Log.e(TAG, "cannot open detail without recipe instance");
            Toast.makeText(this, "Recipe missing. Cannot open detail", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
