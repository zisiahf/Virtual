package cn.hdu.virtual_experiment.service;

import java.util.concurrent.CountDownLatch;

public interface AsycnService {
    public void downloadByJniAndSetNode(String port, CountDownLatch countDownLatch);

    public void downloadByJni(String tool,String path,String ttyUSB,CountDownLatch countDownLatch);
}
