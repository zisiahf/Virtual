package cn.hdu.virtual_experiment.controller.teacher;

import cn.hdu.virtual_experiment.dao.StudentMapper;
import cn.hdu.virtual_experiment.domain.Student_Info;
import cn.hdu.virtual_experiment.domain.Teacher_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.NodeService;
import cn.hdu.virtual_experiment.service.StudentService;
import cn.hdu.virtual_experiment.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    //跳转到添加用户界面
    @RequestMapping("/toAddUser")
    public String toAddUser(String token){
        return "pages/teacher/myStudent";
    }

    //获取学生列表
    @RequestMapping("/getUserByTidAndOid")
    @ResponseBody
    public Result getUserByTidAndOid(String token, String oid, int pageNum, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }

        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);

        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String tid = teacher_info.getTid();

        PageResult pageResult = studentService.getUserByTidAndOid(tid,oid,pageNum);

        return Result.success(pageResult);
    }

    //删除学生
    @RequestMapping("/deleteStudentBySidAndOidAndTid")
    @ResponseBody
    public Result deleteStudentBySidAndOidAndTid(String token, String oid, String sid, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }

        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);

        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String tid = teacher_info.getTid();
        studentService.deleteStudentBySidAndOidAndTid(sid,oid,tid);

        return Result.success("删除成功");
    }

    //添加学生
    @RequestMapping("/addStudent")
    @ResponseBody
    public Result addStudent(String oid,String token,Student_Info student_info,HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String tid = teacher_info.getTid();
        studentService.addStudent(oid,tid,student_info);
        return Result.success("添加成功");
    }

    //上传文件ihex到服务器端
    @RequestMapping("/uploadStudent")
    @ResponseBody
    public Result<String> uploadStudent(@RequestParam MultipartFile file,String token,String oid,HttpServletRequest request){
        log.info("前端传递数据files为：{}",file);
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String tid = teacher_info.getTid();
        studentService.uploadStudent(file,tid,oid);
        return Result.success("上传成功");
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        studentService.exportExcel(request,response);
    }
}
