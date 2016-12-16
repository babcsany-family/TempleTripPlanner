package com.babcsany.templetripplanner;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by peter on 2016. 12. 13..
 */
@NoArgsConstructor
@Builder
@Data
public class Patron implements Parcelable {
    private String name;
    private PatronKind kind;
    private Drawable picture;

    protected Patron(Parcel in) {
        name = in.readString();
        kind = PatronKind.valueOf(in.readString());
        picture = null;
    }

    public Patron(String patronName, PatronKind patronKind, Drawable patronPicture) {
        name = patronName;
        kind = patronKind;
        picture = patronPicture;
    }

    public static final Creator<Patron> CREATOR = new Creator<Patron>() {
        @Override
        public Patron createFromParcel(Parcel in) {
            return new Patron(in);
        }

        @Override
        public Patron[] newArray(int size) {
            return new Patron[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(kind.name());
    }
}
