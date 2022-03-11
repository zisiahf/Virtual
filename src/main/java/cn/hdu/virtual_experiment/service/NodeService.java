package cn.hdu.virtual_experiment.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 26074 on 2020/1/6.
 */
public interface NodeService {
    void downloadIhex(String node_number, MultipartFile[] files);
}
