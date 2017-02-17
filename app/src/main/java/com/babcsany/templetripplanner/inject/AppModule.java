package com.babcsany.templetripplanner.inject;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.babcsany.templetripplanner.Analytics;
import com.babcsany.templetripplanner.BuildConfig;
import com.babcsany.templetripplanner.BusRegistry;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import dagger.Module;
import dagger.Provides;
import org.dbtools.android.domain.config.DatabaseConfig;
import pocketbus.Bus;
import timber.log.Timber;

import javax.inject.Singleton;

/**
 * Created by peter on 2017. 02. 15..
 */
@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    public NotificationManager provideNotificationManager(Application application) {
        return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    public Analytics provideAnalytics() {
        // Only send analytics to Google Analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return params -> Timber.d(String.valueOf(params));
        }

        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(application);
        Tracker tracker = googleAnalytics.newTracker(BuildConfig.ANALYTICS_KEY);
        // tracker.setSessionTimeout(300); // default is 30 seconds
        return new Analytics.GoogleAnalytics(tracker);
    }

    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig(Application application) {
        return null; //new AppDatabaseConfig(application);
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        Bus bus = new Bus.Builder()
                .build();
        bus.setRegistry(new BusRegistry());
        return bus;
    }
}
