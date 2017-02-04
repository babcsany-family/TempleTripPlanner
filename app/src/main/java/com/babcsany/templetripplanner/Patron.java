package com.babcsany.templetripplanner;

import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;

import java.util.List;

import lombok.*;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

/**
 * Created by peter on 2016. 12. 13..
 */
@Parcel
public class Patron {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PatronKind getKind() {
        return kind;
    }

    public void setKind(PatronKind kind) {
        this.kind = kind;
    }

/*
    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }
*/

    public String getWardOrBranch() {
        return wardOrBranch;
    }

    public void setWardOrBranch(String wardOrBranch) {
        this.wardOrBranch = wardOrBranch;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

/*
    public ContactsContract.CommonDataKinds.Phone getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(ContactsContract.CommonDataKinds.Phone phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
*/

    String name;
    PatronKind kind;
//    Drawable picture;
    String wardOrBranch;
    List<String> spokenLanguages;
//    ContactsContract.CommonDataKinds.Phone phoneNumber;
}
