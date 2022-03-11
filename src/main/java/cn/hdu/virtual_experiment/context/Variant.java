package cn.hdu.virtual_experiment.context;

import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.util.SerialComm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Variant {

    //key：节点编号 value：Node对象
    public static Map<String, Node> nodeMap = new ConcurrentHashMap<>();

    //key:ttyusb value:SerialComm对象
    public static Map<String, SerialComm> serialMap = new ConcurrentHashMap<>();

    //key:node_number  value:数据
    public static Map<String, String> dataMap = new ConcurrentHashMap<>();
}
