package com.doodeec.digitalsandstorm.di.modules;

import android.util.Log;

import com.doodeec.digitalsandstorm.DigitalSandstormConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * DI module for network communication
 * provides network specific constructors for partial components
 * - GSON
 * - OkHttp client
 * - logging and header interceptors
 * - Retrofit instance
 *
 * @author Dusan Bartos
 */
@Module
public class NetModule {

    // base URL for server communication
    private String mBaseUrl;

    public NetModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @Singleton
    Interceptor provideHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Log.w("Request", original.urlString());

                Request request = original.newBuilder()
                        .header("access_token", DigitalSandstormConfig.TOKEN)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideClient(HttpLoggingInterceptor interceptor, Interceptor headersInterceptor) {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(headersInterceptor);
        client.interceptors().add(interceptor);
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetro(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
}
