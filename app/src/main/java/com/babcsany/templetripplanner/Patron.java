package com.babcsany.templetripplanner;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import lombok.Builder;
import lombok.Data;

/**
 * Created by peter on 2016. 12. 13..
 */
@Builder
@Data
public class Patron {
    private String name;
    private PatronKind kind;
    private Drawable picture;
}
