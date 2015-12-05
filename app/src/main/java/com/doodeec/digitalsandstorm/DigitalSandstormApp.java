package com.doodeec.digitalsandstorm;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;
import com.doodeec.digitalsandstorm.db.RecipeEntity;
import com.doodeec.digitalsandstorm.db.RecipeStepEntity;
import com.doodeec.digitalsandstorm.di.components.DaggerNetComponent;
import com.doodeec.digitalsandstorm.di.components.DaggerRestApiComponent;
import com.doodeec.digitalsandstorm.di.components.NetComponent;
import com.doodeec.digitalsandstorm.di.components.RestApiComponent;
import com.doodeec.digitalsandstorm.di.modules.AppModule;
import com.doodeec.digitalsandstorm.di.modules.NetModule;
import com.doodeec.digitalsandstorm.di.modules.RestApiModule;

import io.fabric.sdk.android.Fabric;
import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;

/**
 * Application object for "Digital Sandstorm" project (it's a generated codename if you are curious :) )
 * Configures basic boilerplate (db initialization, analytics, crashlytics, ...)
 *
 * @author Dusan Bartos
 */
public class DigitalSandstormApp extends Application {

    private RestApiComponent mRestApiComponent;
    private SyncObservable mSyncObservable;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        // prepare network component
        NetComponent netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(DigitalSandstormConfig.REST_URL))
                .build();

        // initialize REST API component
        mRestApiComponent = DaggerRestApiComponent.builder()
                .netComponent(netComponent)
                .restApiModule(new RestApiModule())
                .build();

        // initialize SyncObservable observable
        mSyncObservable = new SyncObservable(this);

        // initialize database
        Sprinkles sprinkles = Sprinkles.init(getApplicationContext());
        sprinkles.addMigration(new Migration() {
            @Override
            protected void onPreMigrate() {
                // do nothing
            }

            @Override
            protected void doMigration(SQLiteDatabase db) {
                db.execSQL(RecipeEntity.CREATE_DB_TABLE);
                db.execSQL(RecipeStepEntity.CREATE_DB_TABLE);
            }

            @Override
            protected void onPostMigrate() {
                // do nothing
            }
        });
    }

    public RestApiComponent getRestApiComponent() {
        return mRestApiComponent;
    }

    public SyncObservable getSyncObservable() {
        return mSyncObservable;
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
