package com.babcsany.templetripplanner;

/**
 * Created by peter on 2016. 12. 13..
 */
public enum PatronKind {
    CHILD(R.string.patronKind_child, R.string.emailPatronKind_child),
    ADULT(R.string.patronKind_adult, R.string.emailPatronKind_adult),
    TEMPLE_WORKER(R.string.patronKind_templeWorker, R.string.emailPatronKind_templeWorker);

    private final int resourceId;
    private final int emailKind;

    PatronKind(int resourceId) {
        this.resourceId = this.emailKind = resourceId;
    }

    PatronKind(int resourceId, int emailKind) {
        this.resourceId = resourceId;
        this.emailKind = emailKind;
    }

    public int getResourceId() {
        return resourceId;
    }

    public static PatronKind getByResourceId(int resourceId) {
        final PatronKind[] patronKinds = PatronKind.values();
        for (int i = 0; i < patronKinds.length; i++) {
            if (patronKinds[i].getResourceId() == resourceId) {
                return patronKinds[i];
            }
        }
        return null;
    }

    public int getEmailKind() {
        return emailKind;
    }
}
