package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.config.SerialConfig;
import cn.hdu.virtual_experiment.context.Variant;
import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.service.SerialService;
import cn.hdu.virtual_experiment.util.SerialComm;
import cn.hdu.virtual_experiment.vo.SessionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SerialServiceImpl implements SerialService {

    @Autowired
    SerialConfig serialConfig;

    //打开端口
    public boolean openPort(String node_number) {
        try {
            //key是否存在 说明端口还未关闭
            if(!checkNode(node_number)){
                Variant.serialMap.put(node_number,serialConfig.getSerialComm());
                Node node = Variant.nodeMap.get(node_number);
                if(node == null){
                    throw new GlobalException( CodeMsg.NODE_ISNOTEXIST);
                }
                Variant.serialMap.get(node_number).connect("/dev/"+node.getNode_name(), 115200);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //关闭端口
    public boolean closePort(String node_number) {
        try {
            if(checkNode(node_number)){
                log.info("{}关闭端口前:{}",node_number,Variant.serialMap.get(node_number));
                log.info("执行前时间:{}",new Date().getTime());
                Variant.serialMap.get(node_number).closePort();
                log.info("执行后时间:{}",new Date().getTime());
                //设置serialMap为null 避免重复打开
                Variant.serialMap.remove(node_number);

                log.info("{}关闭端口后:{}",node_number,Variant.serialMap.get(node_number));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getSerialData(String node_number, String token, HttpServletRequest request) {
        try {
            if(checkNode(node_number)){
                SerialComm serialComm = Variant.serialMap.get(node_number);
                serialComm.getSerialData(60);
                String resData = processData(serialComm.getData().toString());
                //log.info(node_number +"的数据为：{}",resData);
                return resData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取图表数据
    public Integer getTemperature(String node_number) {
        try {
            if(checkNode(node_number)){
                SerialComm serialComm = Variant.serialMap.get(node_number);
                Integer resData = processDataAndReturnTem(serialComm.getData().toString());
                return resData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取自组网数据
    public Map<String, String> getAdHocNet(String node_numbers,String root) {
        Map<String, String> num = new HashMap<>();//节点编号对应的当前节点序号
        Map<String, String> parent = new HashMap<>();//节点编号对应的父节号
        String count = "";
        try {
            String[] node_number = node_numbers.split(",");
            //保证端口全部开启 获取数据
            for(String s : node_number){
                if(checkNode(s)){
                    if(s.equals(root)){
                        continue;
                    }
                    SerialComm serialComm = Variant.serialMap.get(s);
                    String d = processData(serialComm.getData().toString());
                    if(StringUtils.isEmpty(d)){
                        return null;
                    }
                    int len =d.length();
                    num.put(d.substring(len-13,len-10),s);
                    log.info("当前节点号：{}",d.substring(len-13,len-10));
                    parent.put(s,d.substring(len-22,len-19));
                    log.info("父节点节点号：{}",d.substring(len-22,len-19));
                    String curCount = d.substring(len-19,len-13);
                    log.info("计数器：{}",curCount);
                    if(StringUtils.isEmpty(count)){
                        count = curCount;
                    }else if(!count.equals(curCount)){
                        return null;
                    }
                }
            }
            return getAdHocMap(root, num, parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getAdHocMap(String root, Map<String, String> num, Map<String, String> parent) {
        Map<String, String> res = new HashMap<>();
        for(String key : num.keySet()){
            String cur = num.get(key);
            String par = parent.get(cur);
            if(num.containsKey(par)){
                res.put(cur,num.get(par));
            }else{
                res.put(cur,root);
            }
        }
        return res;
    }

    private boolean checkNode(String node_number) {
        if(StringUtils.isEmpty(node_number)){
            throw new GlobalException( CodeMsg.DATA_ERROR);
        }
        if (Variant.serialMap.containsKey(node_number)) {
            return true;
        }
        return false;
    }

    //对字符串替换和增加换行符号
    private String processData(String data){
        data = data.replaceAll("xx", "7e");
        data = data.replaceAll("yy", "7d");
        return data;
    }
    //对字符串替换和增加换行符号
    private Integer processDataAndReturnTem(String data){
        String d = processData(data);
        String a = d.substring(d.length()-16,d.length()-14);
        String b = d.substring(d.length()-13,d.length()-11);
        int value =  Integer.valueOf(b,16) | (Integer.valueOf(a,16) << 8);
        Double v = value * 0.01 - 40.1;
        return v.intValue();
    }
}
