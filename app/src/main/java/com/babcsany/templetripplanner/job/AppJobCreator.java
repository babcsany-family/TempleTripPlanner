package com.babcsany.templetripplanner.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by peter on 2017. 02. 17..
 */
public class AppJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            default:
                return null;
        }
    }
}
