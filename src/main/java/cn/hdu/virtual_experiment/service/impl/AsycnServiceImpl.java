package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.context.Const;
import cn.hdu.virtual_experiment.config.SerialConfig;
import cn.hdu.virtual_experiment.dao.NodeMapper;
import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.service.AsycnService;
import cn.hdu.virtual_experiment.util.SerialComm;
import com.ssm.SerialPort.SerialPort_jni;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class AsycnServiceImpl implements AsycnService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    SerialConfig serialConfig;

    //封装node 异步执行
    @Async("asyncServiceExecutor")
    public void downloadByJniAndSetNode(String port, CountDownLatch countDownLatch){
        try {
            log.info("当前线程：{}",Thread.currentThread().getName());
            SerialPort_jni.download(Const.DOWNLOAD_TOOL_PATH,"/home/puck/ihex/SerialIDSend.ihex","/dev/"+port);
            //SerialPort_jni.download("/home/puck/Desktop/jni/download_tool.sh","/home/puck/ihex/SerialIDSend.ihex","/dev/"+port);
            SerialComm serialComm = serialConfig.getSerialComm();
            log.info("当前serialComm为{}",serialComm);
            serialComm.connect("/dev/"+port,57600);
            serialComm.getID(81,false);
            String ID = serialComm.getStringBuilder().toString().trim().substring(57,77);
            log.info("节点ID:{}",ID);
            serialComm.closePort();

            Node node = new Node();
            node.setNode_id(ID);
            node.setNode_name(port);
            nodeMapper.insertNode(node);
            countDownLatch.countDown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //异步下载文件到node
    @Async("asyncServiceExecutor")
    public void downloadByJni(String tool,String path,String ttyUSB,CountDownLatch countDownLatch){
        SerialPort_jni.download(tool,path,ttyUSB);
        countDownLatch.countDown();
    }
}
