package com.doodeec.digitalsandstorm.di.components;

import com.doodeec.digitalsandstorm.SyncObservable;
import com.doodeec.digitalsandstorm.di.modules.RestApiModule;
import com.doodeec.digitalsandstorm.di.scopes.UserScope;

import dagger.Component;

/**
 * DI Component for injecting server API interface to Activity
 *
 * @author Dusan Bartos
 */
@UserScope
@Component(dependencies = NetComponent.class, modules = RestApiModule.class)
public interface RestApiComponent {
    void inject(SyncObservable syncObservable);
}
