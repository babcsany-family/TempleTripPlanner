package com.babcsany.templetripplanner.log;

import timber.log.Timber;

/**
 * Created by peter on 2017. 02. 17..
 */
public class DebugTree extends Timber.DebugTree {

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        // add line number
        return super.createStackElementTag(element) + ":" + element.getLineNumber();
    }
}
