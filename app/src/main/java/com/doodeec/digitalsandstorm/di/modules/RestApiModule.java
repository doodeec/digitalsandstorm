package com.doodeec.digitalsandstorm.di.modules;

import com.doodeec.digitalsandstorm.network.RestService;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * This module provides REST API interface (either mock or real one)
 *
 * @author Dusan Bartos
 */
@Module
public class RestApiModule {

    @Provides
    public RestService provideApiInterface(Retrofit retrofit) {
        return retrofit.create(RestService.class);

        // Create the Behavior object which manages the fake behavior and the background executor.
        /*NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(50);
        behavior.setDelay(2, TimeUnit.SECONDS);
        behavior.setVariancePercent(50);
        ExecutorService bg = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                .setNameFormat("mock-retrofit-%d")
                .setDaemon(true)
                .build());

        // Create the mock implementation and use MockRetrofit to apply the behavior to it.
        MockRetrofit mockRetrofit = new MockRetrofit(behavior, new CallBehaviorAdapter(retrofit, bg));
        MockRestService mockRestService = new MockRestService();
        return mockRetrofit.create(RestService.class, mockRestService);*/
    }
}
