package com.babcsany.templetripplanner.parcels;

import com.babcsany.templetripplanner.enums.PatronKind;
import org.parceler.Parcel;

import java.util.List;

/**
 * Parcel class to pass patron information between activities.
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
