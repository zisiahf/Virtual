package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.context.Const;
import cn.hdu.virtual_experiment.Init.NodeInit;
import cn.hdu.virtual_experiment.config.SerialConfig;
import cn.hdu.virtual_experiment.context.Variant;
import cn.hdu.virtual_experiment.dao.NodeMapper;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.service.AsycnService;
import cn.hdu.virtual_experiment.service.NodeService;
import cn.hdu.virtual_experiment.util.SerialComm;
import cn.hdu.virtual_experiment.util.UUIDUtil;
import cn.hdu.virtual_experiment.vo.DownloadVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 26074 on 2020/1/6.
 */
@Service
@Slf4j
public class NodeServiceImpl implements NodeService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    SerialConfig serialConfig;

    @Autowired
    AsycnService asycnService;

    //下载ihex
    public void downloadIhex(String node_number, MultipartFile[] files) {
        List<DownloadVo> downloadVos = getDownloadVO(node_number,files);
        CountDownLatch countDownLatch = new CountDownLatch(downloadVos.size());
        for (DownloadVo downloadVo: downloadVos) {
            asycnService.downloadByJni(Const.DOWNLOAD_TOOL_PATH, downloadVo.getFile().getPath(),
                    "/dev/"+downloadVo.getNode_name(),countDownLatch);
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除所有上传文件
        for (DownloadVo downloadVo: downloadVos) {
            if (downloadVo.getFile() != null){
                downloadVo.getFile().delete();
            }
        }
    }

    //获得下载信息
    private List<DownloadVo> getDownloadVO(String node_number,MultipartFile[] files) {
        List<DownloadVo> downloadVos = new ArrayList<>();
        //解析字符串
        String[] array_node_number = node_number.split("&");
        //判断长度是否一致
        if(array_node_number.length != files.length){
            throw new GlobalException(CodeMsg.DATA_ERROR);//数据异常
        }
        //根据字符串找到对应ttyUSB 下载文件按照顺序
        for(int i = 0;i < array_node_number.length;i++){
            //解析字符串
            String[] node_numbers = array_node_number[i].split(",");
            File uploadFile = uploadFileToLocal(files[i]);
            log.info("上传文件路径：{}",uploadFile);
            if(uploadFile == null){
                //上传出错
                throw new GlobalException(CodeMsg.UPLOAD_ERROR);
            }
            for(int j = 0; j < node_numbers.length; j++){
                String ttyUSB = Variant.nodeMap.get(node_numbers[j]).getNode_name();
                if(StringUtils.isEmpty(ttyUSB)){
                    //节点不存在
                    throw new GlobalException(CodeMsg.NODE_ISNOTEXIST);
                }
                //封装downloadVo
                DownloadVo downloadVo = new DownloadVo();
                downloadVo.setNode_name(ttyUSB);
                downloadVo.setFile(uploadFile);
                downloadVos.add(downloadVo);
            }
        }
        return downloadVos;
    }

    //上传文件到本地
    private File uploadFileToLocal(MultipartFile file){
        try{
            // 原始文件名称
            String originalFilename = file.getOriginalFilename();
            // 设置新文件名称 以学生账户命名
            String newFileName = UUIDUtil.uuid() + originalFilename.substring(originalFilename.lastIndexOf("."));
            // 上传文件
            File uploadFile = new File("/home/puck/ihex/" + newFileName);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            // 向磁盘写文件
            file.transferTo(uploadFile);
            return uploadFile;

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
