package com.babcsany.templetripplanner;

import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by peter on 2017. 02. 15..
 */
public interface Analytics {
    // Categories
    String CATEGORY_APP = "App";
    String CATEGORY_INDIVIDUAL = "Individual";
    String CATEGORY_ABOUT = "About";
    String CATEGORY_SETTINGS = "Settings";

    // Actions
    String ACTION_APP_LAUNCH = "Launch";
    String ACTION_NEW = "New";
    String ACTION_VIEW = "View";
    String ACTION_EDIT = "Edit";
    String ACTION_DELETE = "Delete";

    // Variables
    String VARIABLE_BUILD_TYPE = "Build Type";

    void send(Map<String, String> params);

    class GoogleAnalytics implements Analytics {
        private final Tracker tracker;

        public GoogleAnalytics(Tracker tracker) {
            this.tracker = tracker;
        }

        @Override
        public void send(Map<String, String> params) {
            tracker.send(params);
        }
    }
}