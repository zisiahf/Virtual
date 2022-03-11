package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.dao.TeacherLoginMapper;
import cn.hdu.virtual_experiment.domain.Teacher_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.service.TeacherLoginService;
import cn.hdu.virtual_experiment.util.MD5Util;
import cn.hdu.virtual_experiment.util.UUIDUtil;
import cn.hdu.virtual_experiment.vo.SessionVo;
import cn.hdu.virtual_experiment.vo.TeacherLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class TeacherLoginServiceImpl implements TeacherLoginService {

    @Autowired
    TeacherLoginMapper teacherLoginMapper;

    // 教师登录
    @Override
    public String login(TeacherLoginVo teacherLoginVo, HttpServletRequest request) {
        if(teacherLoginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        //用户名密码校验
        String username = teacherLoginVo.getUsername();

        // 为什么经过了两次md5加密，然而结果却和一次MD5Util.md5加密结果一样啦
        /*System.out.println("原来的密码：" + teacherLoginVo.getPassword());*/
        String password = MD5Util.md5(teacherLoginVo.getPassword());
       /* System.out.println("加密后的密码：" + password);*/

        Teacher_Info teacher_info = teacherLoginMapper.selectTeacherByUsernameAndPassword(username,password);

        if(teacher_info == null){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        return setSessionAndReturnToken(teacher_info,request);
    }

    // 教师注册
    @Transactional
    public void register(Teacher_Info teacher_info) {
        //校验是否有相同用户
        Teacher_Info t = teacherLoginMapper.selectTeacherByUsername(teacher_info.getUsername());
        if(t != null){
            throw new GlobalException(CodeMsg.USERNAME_EXIST);
        }

        // 插入的教师数据的预处理：tid——随机uuid、 password——密码加密md5，其实这里的密码是已经在表单中经过md5加密过的
        teacher_info.setTid(UUIDUtil.uuid());
        String password = MD5Util.md5(teacher_info.getPassword());
        teacher_info.setPassword(password);

        teacherLoginMapper.insert(teacher_info);
    }

    private String setSessionAndReturnToken(Teacher_Info teacher_info, HttpServletRequest request) {
        log.info("设置session并返回token");
        String token = UUIDUtil.uuid();
        HttpSession session = request.getSession();
        session.setAttribute(token,teacher_info);
        session.setMaxInactiveInterval(1000000);//设置session有效期
        log.info("设置session返回token完成,token = {}",token);
        return token;
    }
}
