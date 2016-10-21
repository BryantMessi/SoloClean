package com.solo.soloclean.memory.bean;

import android.os.Parcel;

import java.io.IOException;

/**
 * Created by Messi on 16-10-13.
 */

public class Statm extends ProcFile {

    public final String[] fields;
    public static final Creator<Statm> CREATOR = new Creator() {
        public Statm createFromParcel(Parcel source) {
            return new Statm(source);
        }

        public Statm[] newArray(int size) {
            return new Statm[size];
        }
    };

    public static Statm get(int pid) throws IOException {
        return new Statm(String.format("/proc/%d/statm", new Object[]{Integer.valueOf(pid)}));
    }

    private Statm(String path) throws IOException {
        super(path);
        this.fields = this.content.split("\\s+");
    }

    private Statm(Parcel in) {
        super(in);
        this.fields = in.createStringArray();
    }

    public long getSize() {
        return Long.parseLong(this.fields[0]) * 1024L;
    }

    public long getResidentSetSize() {
        return Long.parseLong(this.fields[1]) * 1024L;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(this.fields);
    }
}
