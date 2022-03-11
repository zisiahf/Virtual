package cn.hdu.virtual_experiment.controller.student;

import cn.hdu.virtual_experiment.domain.Ex_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.ExService;
import cn.hdu.virtual_experiment.vo.SessionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 实验部分
 */
@Controller
@RequestMapping("/student")
@Slf4j
public class ExController {

    @Autowired
    ExService exService;

    //开始试验进入实验界面
    @RequestMapping("/startEx")
    public String startEx(String token, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        SessionVo sessionVo = (SessionVo)request.getSession().getAttribute(token);
        if(sessionVo == null){
            //直接返回到预约列表页面
            log.info("session为空");
            return "index";
        }
        String page = sessionVo.getPage();
        return "pages/student/"+page+"/"+page+"_UploadIhex";
    }

    //结束实验 返回到实验列表
    @RequestMapping("/endEx")
    public String endEx(String token, HttpServletRequest request){
        return null;
    }


    //进入数据页面
    @RequestMapping("/toGetData")
    public String toGetData(String token, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        SessionVo sessionVo = (SessionVo)request.getSession().getAttribute(token);
        if(sessionVo == null){
            //直接返回到预约列表页面
            log.info("session为空");
            return "index";
        }
        String page = sessionVo.getPage();
        return "pages/student/"+page+"/"+page+"_GetData";
    }


    @RequestMapping("/getEx")
    @ResponseBody
    public Result<List<Ex_Info>> getEx(){
        List<Ex_Info> exList = exService.getEx();
        return Result.success(exList);
    }
}
