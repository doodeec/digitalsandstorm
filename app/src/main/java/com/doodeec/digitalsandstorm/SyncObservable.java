package com.doodeec.digitalsandstorm;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.doodeec.digitalsandstorm.model.Recipe;
import com.doodeec.digitalsandstorm.model.RecipeStep;
import com.doodeec.digitalsandstorm.network.RestService;
import com.doodeec.digitalsandstorm.network.response.RecipeListResponse;
import com.doodeec.digitalsandstorm.network.response.RecipeStepListResponse;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.emilsjolander.sprinkles.Transaction;

/**
 * Singleton class instance used for data synchronization
 * To separate and centralize this process from other components, it is defined
 * as an observable, so other application parts can listen to data changes if necessary
 *
 * However in most of the situations, Sprinkles library already cover this
 * by using return value inside async handler
 *
 * @author Dusan Bartos
 */
public class SyncObservable extends Observable {

    public static final int STATE_IDLE = 0;
    public static final int STATE_SYNCING = 1;

    private static final String TAG = "SyncObservable";

    private int mCurrentState = STATE_IDLE;
    private Call<RecipeListResponse> mRecipeListCall;
    private Call<RecipeStepListResponse> mRecipeStepListCall;

    // queue storing sync errors
    // when no observer is attached, errors are postponed to be displayed as toasts
    private Queue<Throwable> mErrorQueue = new LinkedList<>();

    @Inject
    RestService mRestService;

    public SyncObservable(DigitalSandstormApp application) {
        application.getRestApiComponent().inject(this);
    }

    /**
     * Triggers network synchronization
     * method calls REST webservice to sync all Recipes and Recipe Steps
     */
    public void triggerSync() {
        if (mCurrentState == STATE_SYNCING) {
            cancelRecipesCall();
            cancelStepsCall();
        }

        mCurrentState = STATE_SYNCING;

        // init calls first
        mRecipeListCall = mRestService.getRecipeList();
        mRecipeStepListCall = mRestService.getRecipeStepsList();

        // kickoff
        mRecipeListCall.enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Response<RecipeListResponse> response, Retrofit retrofit) {
                Log.d(TAG, "onResponse Recipes");
                mRecipeListCall = null;
                checkStatus();

                Recipe[] recipes = response.body().getRecipes();
                if (recipes != null) {
                    // save steps with single transaction
                    Transaction transaction = new Transaction();
                    try {
                        for (Recipe recipe : recipes) {
                            boolean saved = recipe.save(transaction);
                            Log.d(TAG, "saved Recipe - " + recipe.getId() + " " + saved);

                            if (!saved) break;
                        }
                        Log.d(TAG, "all recipes saved");
                        transaction.setSuccessful(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        transaction.finish();
                    }

                    // propagate changes to observers on UI thread
                    notifyUiThread(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mRecipeListCall = null;
                checkStatus();
                handleError(t);
            }
        });
        mRecipeStepListCall.enqueue(new Callback<RecipeStepListResponse>() {
            @Override
            public void onResponse(Response<RecipeStepListResponse> response, Retrofit retrofit) {
                Log.d(TAG, "onResponse RecipeSteps");
                mRecipeStepListCall = null;
                checkStatus();

                RecipeStep[] recipeSteps = response.body().getRecipeSteps();
                if (recipeSteps != null) {
                    // save steps with single transaction
                    Transaction transaction = new Transaction();
                    try {
                        for (RecipeStep recipeStep : recipeSteps) {
                            boolean saved = recipeStep.save(transaction);
                            Log.d(TAG, "saved RecipeStep - " + recipeStep.getId() + " " + saved);

                            if (!saved) break;
                        }
                        Log.d(TAG, "all RecipeSteps saved");
                        transaction.setSuccessful(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        transaction.finish();
                    }

                    // propagate changes to observers on UI thread
                    notifyUiThread(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mRecipeStepListCall = null;
                checkStatus();
                handleError(t);
            }
        });
    }

    /**
     * Returns current state of sync process
     *
     * @return true if current state is {@link #STATE_SYNCING}
     */
    public synchronized boolean isSyncing() {
        return mCurrentState == STATE_SYNCING;
    }

    /**
     * Returns next unhandled error and dequeue that error from buffer
     *
     * @return sync error
     */
    public synchronized Throwable nextError() {
        if (mErrorQueue.size() == 0) return null;
        return mErrorQueue.remove();
    }

    // internally check completion state
    private void checkStatus() {
        if (mRecipeStepListCall == null && mRecipeListCall == null) {
            mCurrentState = STATE_IDLE;
        }
    }

    /**
     * Propagates a data change to UI thread
     *
     * @param dataObject changed data
     */
    private void notifyUiThread(final Object dataObject) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(dataObject);
            }
        });
    }

    // put error to queue
    private void handleError(Throwable t) {
        t.printStackTrace();
        Log.e(TAG, "response error");

        mErrorQueue.add(t);
        notifyUiThread(null);
    }

    private void cancelRecipesCall() {
        if (mRecipeListCall != null) {
            mRecipeListCall.cancel();
            mRecipeListCall = null;
        }
    }

    private void cancelStepsCall() {
        if (mRecipeStepListCall != null) {
            mRecipeStepListCall.cancel();
            mRecipeStepListCall = null;
        }
    }
}
