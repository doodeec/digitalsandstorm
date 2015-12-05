package com.doodeec.digitalsandstorm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.doodeec.digitalsandstorm.DigitalSandstormApp;
import com.doodeec.digitalsandstorm.R;
import com.doodeec.digitalsandstorm.SyncObservable;
import com.doodeec.digitalsandstorm.model.Recipe;
import com.doodeec.digitalsandstorm.model.RecipeListActions;

import java.util.Observable;
import java.util.Observer;

public class RecipeListActivity extends AppCompatActivity implements
        RecipeListActions,
        Observer {

    private SyncObservable mSyncObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSyncObservable = ((DigitalSandstormApp) getApplication()).getSyncObservable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSyncObservable.addObserver(this);
    }

    @Override
    protected void onPause() {
        mSyncObservable.deleteObserver(this);
        super.onPause();
    }

    @Override
    public void openRecipeDetail(Recipe recipe) {
        Intent detailIntent = new Intent(this, RecipeDetailActivity.class);
        detailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        startActivity(detailIntent);
    }

    @Override
    public void refreshRecipeList() {
        mSyncObservable.triggerSync();
    }

    @Override
    public void showRecipeDescription(Recipe recipe) {
        //TODO show tooltip with description
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    public void update(Observable observable, Object data) {
        if (observable.equals(mSyncObservable)) {
            Throwable t = mSyncObservable.nextError();
            while (t != null) {
                Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
                t = mSyncObservable.nextError();
            }
        }
    }
}
