package com.babcsany.templetripplanner.inject;

import com.babcsany.templetripplanner.App;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by peter on 2017. 02. 15..
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // UI
    void inject(App application);
/*
    void inject(StartupActivity target);
    void inject(DirectoryActivity target);
    void inject(IndividualActivity target);
    void inject(IndividualEditActivity target);
    void inject(SettingsActivity target);
    void inject(AboutActivity target);

    // Adapters
    void inject(DirectoryAdapter target);

    // Exported for child-components.
    Application application();

    void inject(SettingsFragment target);
*/
}
