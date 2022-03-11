package cn.hdu.virtual_experiment.controller.student;

import cn.hdu.virtual_experiment.Init.NodeInit;
import cn.hdu.virtual_experiment.config.NodeServiceConfig;
import cn.hdu.virtual_experiment.config.SerialConfig;
import cn.hdu.virtual_experiment.context.Variant;
import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.NodeService;
import cn.hdu.virtual_experiment.util.SerialComm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 节点相关操作
 */
@Controller
@RequestMapping("/node")
@Slf4j
public class NodeController {

    @Autowired
    NodeServiceConfig nodeServiceConfig;

    //上传文件ihex到服务器端
    @RequestMapping("/uploadIhex")
    @ResponseBody
    public Result<String> uploadIhex(@RequestParam String node_number, @RequestParam MultipartFile[] files){
        log.info("前端传递数据node_number为：{}，files为：{}",node_number,files);
        NodeService nodeService = nodeServiceConfig.getNodeService();
        //文件下载到节点中
        nodeService.downloadIhex(node_number,files);
        return Result.success("下载成功");
    }
    //查询当前可用节点
    @RequestMapping("/findAvailableNode")
    @ResponseBody
    public Result<List<Node>> findAvailableNode(){
        Map<String, Node> nodeMap = Variant.nodeMap;
        List<Node> availableNodes = new ArrayList<>();
        for(String key : nodeMap.keySet()){
            Node node = nodeMap.get(key);
            if(!node.isUsed()){
                availableNodes.add(node);
            }
        }
        return Result.success(availableNodes);
    }

}
