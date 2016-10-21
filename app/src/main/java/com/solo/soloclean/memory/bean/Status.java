package com.solo.soloclean.memory.bean;

import java.io.IOException;

/**
 * Created by Messi on 16-10-13.
 */

public class Status extends ProcFile {
    public static Status get(int pid) throws IOException {
        return new Status(String.format("/proc/%d/status", new Object[]{Integer.valueOf(pid)}));
    }

    private Status(String path) throws IOException {
        super(path);
    }

    public String getValue(String fieldName) {
        String[] lines = this.content.split("\n");
        String[] var6 = lines;
        int var5 = lines.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            String line = var6[var4];
            if (line.startsWith(fieldName + ":")) {
                return line.split(fieldName + ":")[1].trim();
            }
        }

        return null;
    }

    public int getUid() {
        try {
            return Integer.parseInt(this.getValue("Uid").split("\\s+")[0]);
        } catch (Exception var2) {
            return -1;
        }
    }

    public int getGid() {
        try {
            return Integer.parseInt(this.getValue("Gid").split("\\s+")[0]);
        } catch (Exception var2) {
            return -1;
        }
    }
}
