package cn.hdu.virtual_experiment.vo;

import java.io.File;

public class DownloadVo {

    private String node_name;
    private File file;

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
