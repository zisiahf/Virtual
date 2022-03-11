package cn.hdu.virtual_experiment.vo;

/**
 * Created by 26074 on 2020/1/2.
 */
public class SessionVo {

    private String username;

    private String ex_Name;//实验名称

    private String node_number;

    private int data_len;//数据长度

    private String page;//实验跳转页面

    public int getData_len() {
        return data_len;
    }

    public void setData_len(int data_len) {
        this.data_len = data_len;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEx_Name() {
        return ex_Name;
    }

    public void setEx_Name(String ex_Name) {
        this.ex_Name = ex_Name;
    }

    public String getNode_number() {
        return node_number;
    }

    public void setNode_number(String node_number) {
        this.node_number = node_number;
    }
}
