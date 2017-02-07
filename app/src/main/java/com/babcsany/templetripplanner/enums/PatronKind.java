package com.babcsany.templetripplanner.enums;

import com.babcsany.templetripplanner.R;

/**
 * The type of the patron.
 *
 * There are 3 types of patrons in the Hostel: CHILD, ADULT, TEMPLE_WORKER
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
        for (PatronKind patronKind : patronKinds) {
            if (patronKind.getResourceId() == resourceId) {
                return patronKind;
            }
        }
        return null;
    }

    public int getEmailKind() {
        return emailKind;
    }
}
