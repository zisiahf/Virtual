package cn.hdu.virtual_experiment.controller.teacher;

import cn.hdu.virtual_experiment.domain.Teacher_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.TeacherLoginService;
import cn.hdu.virtual_experiment.vo.TeacherLoginVo;
import org.apache.catalina.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/teacher")
public class TeacherLoginController {

    @Autowired
    TeacherLoginService teacherLoginService;

    //返回i登陆页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "pages/teacher/teacherLogin";
    }

    //注册
    @RequestMapping("/teacherRegister")
    @ResponseBody
    public Result<String> register(Teacher_Info teacher_info){
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }

        teacherLoginService.register(teacher_info);

        return Result.success("注册成功");
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Result<String> doLogin(TeacherLoginVo teacherLoginVo, HttpServletRequest request){
        // 生成token返回给前端
        System.out.println("teacherLoginVo: "+teacherLoginVo);
        String token = teacherLoginService.login(teacherLoginVo,request);

        return Result.success(token);
    }

    @RequestMapping("/getUsername")
    @ResponseBody
    public Result<String> doLogin(String token, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String name = teacher_info.getName();
        return Result.success(name);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Result<String> logout(String token, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        //设置为空
        request.getSession().setAttribute(token, null);
        return Result.success("");
    }

}
