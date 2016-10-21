package com.solo.soloclean.memory.bean;

import android.os.Parcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Messi on 16-10-13.
 */

public class Cgroup extends ProcFile {
    public final ArrayList<ControlGroup> groups;
    public static final Creator<Cgroup> CREATOR = new Creator() {
        public Cgroup createFromParcel(Parcel source) {
            return new Cgroup(source);
        }

        public Cgroup[] newArray(int size) {
            return new Cgroup[size];
        }
    };

    public static Cgroup get(int pid) throws IOException {
        return new Cgroup(String.format("/proc/%d/cgroup", new Object[]{Integer.valueOf(pid)}));
    }

    private Cgroup(String path) throws IOException {
        super(path);
        String[] lines = this.content.split("\n");
        this.groups = new ArrayList();
        String[] var6 = lines;
        int var5 = lines.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String line = var6[var4];

            try {
                this.groups.add(new ControlGroup(line));
            } catch (Exception var8) {
                ;
            }
        }

    }

    private Cgroup(Parcel in) {
        super(in);
        this.groups = in.createTypedArrayList(ControlGroup.CREATOR);
    }

    public ControlGroup getGroup(String subsystem) {
        Iterator var3 = this.groups.iterator();

        while(var3.hasNext()) {
            ControlGroup group = (ControlGroup)var3.next();
            String[] systems = group.subsystems.split(",");
            String[] var8 = systems;
            int var7 = systems.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                String name = var8[var6];
                if(name.equals(subsystem)) {
                    return group;
                }
            }
        }

        return null;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.groups);
    }
}
