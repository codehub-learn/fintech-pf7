package gr.codelearn.domain;

import java.io.File;

public enum Directory {
    FILE_DIRECTORY(System.getProperty("user.home") + File.separator + "data_files" + File.separator);

    private final String path;

    Directory(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
