package com.solo.soloclean.memory.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.IOException;


/**
 * Created by Messi on 16-10-13.
 */

public class ProcessInfo implements Parcelable {

    private int mPid;
    private int mUid;
    private String mProcessName;
    private boolean mforeground;
    private Cgroup mCgroup;

    public static final Creator<ProcessInfo> CREATOR = new Creator() {
        public ProcessInfo createFromParcel(Parcel source) {
            return new ProcessInfo(source);
        }

        public ProcessInfo[] newArray(int size) {
            return new ProcessInfo[size];
        }
    };

    protected ProcessInfo(Parcel in) {
        this.mPid = in.readInt();
        this.mProcessName = in.readString();
        this.mCgroup = in.readParcelable(Cgroup.class.getClassLoader());
        this.mforeground = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mProcessName);
        dest.writeInt(this.mPid);
        dest.writeParcelable(this.mCgroup, flags);
        dest.writeByte((byte) (this.mforeground ? 1 : 0));
    }

    public ProcessInfo(int pid) throws IOException {
        this.mPid = pid;
        this.mProcessName = getProcessName(pid);
        this.mCgroup = Cgroup.get(pid);
    }

    private String getProcessName(int pid) throws IOException {
        String cmdline = null;

        try {
            cmdline = ProcFile.readFile(String.format("/proc/%d/cmdline", new Object[]{Integer.valueOf(pid)})).trim();
        } catch (IOException var3) {

        }

        return !TextUtils.isEmpty(cmdline) && !"null".equals(cmdline) ? cmdline : Stat.get(pid).getComm();
    }

    public String getProcessName() {
        return mProcessName;
    }

    public int getPid() {
        return mPid;
    }

    public int getUid() throws IOException, NotAndroidAppProcessException {
        ControlGroup cpuacct = this.mCgroup.getGroup("cpuacct");
        ControlGroup cpu = this.mCgroup.getGroup("cpu");
        if (cpu != null && cpuacct != null && cpuacct.group.contains("pid_")) {

            try {
                this.mUid = Integer.parseInt(cpuacct.group.split("/")[1].replace("uid_", ""));
            } catch (Exception var5) {
                this.mUid = this.status().getUid();
            }
            return mUid;
        } else {
            throw new NotAndroidAppProcessException(mPid);
        }
    }

    public boolean getForeground() throws NotAndroidAppProcessException {
        ControlGroup cpuacct = this.mCgroup.getGroup("cpuacct");
        ControlGroup cpu = this.mCgroup.getGroup("cpu");
        if (cpu != null && cpuacct != null && cpuacct.group.contains("pid_")) {
            this.mforeground = !cpu.group.contains("bg_non_interactive");
            return mforeground;
        } else {
            throw new NotAndroidAppProcessException(mPid);
        }
    }

    private String read(String filename) throws IOException {
        return ProcFile.readFile(String.format("/proc/%d/%s", new Object[]{Integer.valueOf(this.mPid), filename}));
    }

    public String attr_current() throws IOException {
        return this.read("attr/current");
    }

    public String cmdline() throws IOException {
        return this.read("cmdline");
    }

    public Cgroup getCgroup() throws IOException {
        return mCgroup;
    }

    public int oom_adj() throws IOException {
        return Integer.parseInt(this.read("oom_adj"));
    }

    public int oom_score_adj() throws IOException {
        return Integer.parseInt(this.read("oom_score_adj"));
    }

    public Stat stat() throws IOException {
        return Stat.get(this.mPid);
    }

    public Statm statm() throws IOException {
        return Statm.get(this.mPid);
    }

    public Status status() throws IOException {
        return Status.get(this.mPid);
    }

    public String wchan() throws IOException {
        return this.read("wchan");
    }

    private static final class NotAndroidAppProcessException extends Exception {
        NotAndroidAppProcessException(int pid) {
            super(String.format("The process %d does not belong to any application", new Object[]{Integer.valueOf(pid)}));
        }
    }
}
