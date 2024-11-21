package com.systemcalls.systemcalls.service;

import com.systemcalls.systemcalls.service.iface.iFileSystem;

import java.io.File;

public class FileSystem implements iFileSystem {

    private final File file;

    public FileSystem(File file) {
        this.file = file;
    }

    @Override
    public long getTotalSpace() {
        return file.getTotalSpace();
    }

    @Override
    public long getFreeSpace() {
        return file.getFreeSpace();
    }
}