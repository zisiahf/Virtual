package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.context.Const;
import cn.hdu.virtual_experiment.config.SerialConfig;
import cn.hdu.virtual_experiment.dao.NodeMapper;
import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.service.AsycnService;
import cn.hdu.virtual_experiment.service.InitService;
import cn.hdu.virtual_experiment.util.SerialComm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class InitServiceImpl implements InitService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    SerialConfig serialConfig;

    @Autowired
    AsycnService asycnService;

    //初始化节点
    @Transactional(propagation= Propagation.SUPPORTS)
    public List<Node> initNode() {
        //1.删除所有节点信息
        log.info("删除节点");
        //nodeMapper.deleteNode();
        //插入新Node数据
        Set<String> ports = SerialComm.listPorts();
//
//        CountDownLatch countDownLatch = new CountDownLatch(ports.size());
//        log.info("ports.size()：{}",ports.size());
//        for (String port : ports) {
//            log.info("当前端口：{}",port);
//            //2.下载读取ID的IHEX文件
//            log.info("插入节点");
//            asycnService.downloadByJniAndSetNode(port,countDownLatch);
//        }
//        try {
//            countDownLatch.await();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //3.更新节点信息
        log.info("更新节点");
        updataNode(ports);
        //4.查询所有节点信息
        List<Node> nodeList = nodeMapper.selectNode();

        log.info("查询节点{}",nodeList);
        return nodeList;
    }


    //更新节点编号
    private void updataNode(Set<String> ports){
        float j = 1;
        String num = "";
        for (String port : ports) {
            if(j % Const.NODE_COL != 0){
                num=Math.round(j / Const.NODE_COL)+"_"+Math.round(j % Const.NODE_COL);
                j++;
            }else{
                num=Math.round(j / Const.NODE_COL)+ "_" + Const.NODE_COL;
                j++;
            }
            nodeMapper.updataNode(port,num);
        }
    }
}
