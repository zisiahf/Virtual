package cn.hdu.virtual_experiment.controller.student;

import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/serial")
@Controller
public class SerialController {

    @Autowired
    SerialService serialService;

    //打开端口
    @RequestMapping("/openPort")
    @ResponseBody
    public Result<Boolean> openPort(String node_number,String token){
        boolean data = serialService.openPort(node_number);
        return Result.success(data);
    }

    //关闭端口
    @RequestMapping("/closePort")
    @ResponseBody
    public Result<Boolean> closePort(String node_number,String token){
        boolean data = serialService.closePort(node_number);
        return Result.success(data);
    }

    //获取串口数据
    @RequestMapping("/getSerialData")
    @ResponseBody
    public Result getSerialData(String node_number, String token, HttpServletRequest request){
        String data = serialService.getSerialData(node_number,token,request);
        return Result.success(data);
    }

    //获取图表数据
    @RequestMapping("/getTemperature")
    @ResponseBody
    public Result<Integer> getTemperature(String node_number, String token){
        Integer data = serialService.getTemperature(node_number);
        return Result.success(data);
    }

    @RequestMapping("/getAdHocNet")
    @ResponseBody
    public Result<Map<String,String>> getAdHocNet(String node_numbers, String token,String root){
        Map<String,String> data = serialService.getAdHocNet(node_numbers,root);
        return Result.success(data);
    }

}
