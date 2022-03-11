package cn.hdu.virtual_experiment.controller.student;

import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.LoginService;
import cn.hdu.virtual_experiment.vo.LoginVo;
import cn.hdu.virtual_experiment.vo.SessionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 */
@Controller
@RequestMapping("/student")
public class LoginController {

    @Autowired
    LoginService loginService;

    //返回登录页面
    @RequestMapping("/toLogin")
    public String toLogin(String oid){
        //oid为空不允许登录
        if(StringUtils.isEmpty(oid)){
            return "index";
        }
        return "pages/student/studentLogin";
    }

    //登录提交参数 post方式提交数据
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result<String> doLogin(LoginVo loginVo, HttpServletRequest request){
        System.out.println("doLogin:"+loginVo);
        //生成token返回给前端
        String token = loginService.login(loginVo,request);
        System.out.println("doLogin:"+token);
        return Result.success(token);
    }

    //登录提交参数 post方式提交数据
    @RequestMapping("/getMessage")
    @ResponseBody
    public Result<SessionVo> getMessage(String token, HttpServletRequest request){
        SessionVo sessionVo = (SessionVo) request.getSession().getAttribute(token);
        return Result.success(sessionVo);
    }
}
