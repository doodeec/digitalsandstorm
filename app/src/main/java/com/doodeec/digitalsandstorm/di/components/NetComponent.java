package com.doodeec.digitalsandstorm.di.components;

import com.doodeec.digitalsandstorm.di.modules.AppModule;
import com.doodeec.digitalsandstorm.di.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

/**
 * DI component for handling network communication
 *
 * @author Dusan Bartos
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
}
