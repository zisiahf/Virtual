package cn.hdu.virtual_experiment.Init;

import cn.hdu.virtual_experiment.config.NodeServiceConfig;
import cn.hdu.virtual_experiment.context.Variant;
import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.service.InitService;
import cn.hdu.virtual_experiment.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统初始化
 */
@Component
@Slf4j
public class NodeInit implements ApplicationRunner {

    @Autowired
    InitService initService;


    //初始化操作
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //初始化节点 返回LIST
        List<Node> list = initService.initNode();

        for(Node node : list){
            System.out.println(node);
        }
        System.out.println("以上是全部的node信息");

        //封装当前节点信息
        for (Node node:list) {
            Variant.nodeMap.put(node.getNum(),node);
            log.info("当前node为：{},是否使用：{}",node.getNode_name(),node.isUsed());
        }

    }
}
