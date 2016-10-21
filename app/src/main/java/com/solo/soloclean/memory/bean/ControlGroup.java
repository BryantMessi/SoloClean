package com.solo.soloclean.memory.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Messi on 16-10-13.
 */

public class ControlGroup implements Parcelable {
    public final int id;
    public final String subsystems;
    public final String group;
    public static final Creator<ControlGroup> CREATOR = new Creator() {
        public ControlGroup createFromParcel(Parcel source) {
            return new ControlGroup(source);
        }

        public ControlGroup[] newArray(int size) {
            return new ControlGroup[size];
        }
    };

    protected ControlGroup(String line) throws NumberFormatException, IndexOutOfBoundsException {
        String[] fields = line.split(":");
        this.id = Integer.parseInt(fields[0]);
        this.subsystems = fields[1];
        this.group = fields[2];
    }

    protected ControlGroup(Parcel in) {
        this.id = in.readInt();
        this.subsystems = in.readString();
        this.group = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.subsystems);
        dest.writeString(this.group);
    }
}
