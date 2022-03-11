package cn.hdu.virtual_experiment.vo;

public class Order_Date_Vo {

    private Integer timeIndex; // 实验时间索引

    private Integer people_number; // 实验人数

    private Integer count;  // 每个人占用多少个节点

    public Integer getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(Integer timeIndex) {
        this.timeIndex = timeIndex;
    }

    public Integer getPeople_number() {
        return people_number;
    }

    public void setPeople_number(Integer people_number) {
        this.people_number = people_number;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
