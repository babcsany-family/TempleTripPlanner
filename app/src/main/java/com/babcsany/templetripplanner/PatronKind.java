package com.babcsany.templetripplanner;

/**
 * Created by peter on 2016. 12. 13..
 */
public enum PatronKind {
    CHILD(R.string.patronKind_child),
    ADULT(R.string.patronKind_adult),
    TEMPLE_WORKER(R.string.patronKind_templeWorker);

    private final int resourceId;

    PatronKind(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }
}
