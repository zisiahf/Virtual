package cn.hdu.virtual_experiment.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SerialService {
    boolean openPort(String node_number);

    boolean closePort(String node_number);

    String getSerialData(String node_number, String token, HttpServletRequest request);

    Integer getTemperature(String node_number);

    Map<String, String> getAdHocNet(String node_numbers,String root);
}
