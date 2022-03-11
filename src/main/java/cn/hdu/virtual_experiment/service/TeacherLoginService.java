package cn.hdu.virtual_experiment.service;

import cn.hdu.virtual_experiment.domain.Teacher_Info;
import cn.hdu.virtual_experiment.vo.TeacherLoginVo;

import javax.servlet.http.HttpServletRequest;

public interface TeacherLoginService {
    String login(TeacherLoginVo teacherLoginVo, HttpServletRequest request);

    void register(Teacher_Info teacher_info);
}
