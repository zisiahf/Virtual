package cn.hdu.virtual_experiment.service;

import cn.hdu.virtual_experiment.domain.Student_Info;
import cn.hdu.virtual_experiment.util.PageResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StudentService {
    PageResult getUserByTidAndOid(String tid, String oid,int pageNum);

    void addStudent(String oid, String tid, Student_Info student_info);

    void deleteStudentBySidAndOidAndTid(String sid, String oid, String tid);

    void uploadStudent(MultipartFile file, String tid, String oid);

    void exportExcel(HttpServletRequest request, HttpServletResponse response);

    void deleteStudentByOid(String oid);
}
