package com.example.rpg_character;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class InventoryItem implements Parcelable, Serializable {//предмет в инвенторе
    public String name, desc;

    public static final Creator<InventoryItem> CREATOR = new Creator<InventoryItem>() {

        @Override
        public InventoryItem createFromParcel(Parcel source) {
            return new InventoryItem(source);
        }

        @Override
        public InventoryItem[] newArray(int size) {
            return new InventoryItem[size];
        }
    };

    public InventoryItem(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public InventoryItem(Parcel source) {
        name = source.readString();
        desc = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
    }
}
