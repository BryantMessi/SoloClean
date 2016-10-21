package com.solo.soloclean.memory.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Messi on 16-10-13.
 */

public class ProcFile extends File implements Parcelable {
    public final String content;
    public static final Creator<ProcFile> CREATOR = new Creator() {
        public ProcFile createFromParcel(Parcel in) {
            return new ProcFile(in);
        }

        public ProcFile[] newArray(int size) {
            return new ProcFile[size];
        }
    };

    public static String readFile(String path) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        output.append(reader.readLine());

        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            output.append('\n').append(line);
        }

        reader.close();
        return output.toString();
    }

    protected ProcFile(String path) throws IOException {
        super(path);
        this.content = readFile(path);
    }

    protected ProcFile(Parcel in) {
        super(in.readString());
        this.content = in.readString();
    }

    public long length() {
        return (long)this.content.length();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getAbsolutePath());
        dest.writeString(this.content);
    }
}
